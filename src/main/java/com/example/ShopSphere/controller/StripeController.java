package com.example.ShopSphere.controller;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountCreateParams.Capabilities.CardPayments;
import com.stripe.param.AccountCreateParams.Capabilities.Transfers;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ShopSphere.entity.Cart;
import com.example.ShopSphere.entity.User;
import com.example.ShopSphere.service.CartService;
import com.example.ShopSphere.service.UserService;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StripeController {
	
	@Value("${stripe.key}")
	public String API_KEY;
	
	@Value("${stripe.webhook.endpointSecret}")
	private String endpointSecret;
	
	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
    
    @PostConstruct
    private void initialize() {
    	System.out.println("api_key  " + API_KEY);
        Stripe.apiKey = API_KEY; 
    }

//    List<Product> products = new ArrayList<>();
//
//    public StripeController() {
//        products.add(new Product("Product 1", 1000, 1));
//        products.add(new Product("Product 2", 2000, 2));
//    }

    @PostMapping("/api/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession() {
        User user = userService.whoAmI();
        List<Cart> cartProducts = cartService.myCart();
        Map<String, String> response = new HashMap<>();

        try {
            // Create line items for each product
            List<SessionCreateParams.LineItem> lineItems = cartProducts.stream()
                .map(this::createLineItem)
                .collect(Collectors.toList());

            // Create a Checkout Session
            SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addAllLineItem(lineItems)
                .setSuccessUrl("http://localhost:3000/success") // Replace with your success URL
                .setCancelUrl("http://localhost:3000/cancel") // Replace with your cancel URL
                .build();

            Session session = Session.create(params);
            response.put("sessionID", session.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error properly (consider using a logging framework)
            e.printStackTrace(); // Replace with logger.error(e.getMessage(), e);
            response.put("error", "Failed to create checkout session: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private SessionCreateParams.LineItem createLineItem(Cart product) {
        return SessionCreateParams.LineItem.builder()
            .setPriceData(
                SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("inr") // Specify the currency
                    .setUnitAmount(product.getProduct().getPrice().longValue() * 100) 
                    .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(product.getProduct().getName())
                            .addImage(product.getProduct().getImagePath())
                            .build()
                    )
                    .build()
            )
            .setQuantity((long) product.getQuantity())
            .build();
    }

    
    @PostMapping("/api/connect")
    public Map<String,String> getConnectUrl() throws StripeException {
    	Map<String, String> map = new HashMap<>();
    	String accountId = getAccountId();
    	AccountLinkCreateParams params =
    			  AccountLinkCreateParams.builder()
    			    .setAccount(accountId)
    			    .setRefreshUrl("https://example.com/reauth")
    			    .setReturnUrl("https://example.com/return")
    			    .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
    			    .build();
    	AccountLink accountLink = AccountLink.create(params);
    	map.put("url", accountLink.getUrl());
    	
    	return map;
    	
    }
    
    public String getAccountId() throws StripeException {
    	
    	 AccountCreateParams.Individual individual = AccountCreateParams.Individual.builder()
    	            .setFirstName("Arpit")
    	            .build();
    	 
    	AccountCreateParams params =
    			  AccountCreateParams.builder()
    			    .setCountry("US")
    			    .setEmail("arpitbala@yopmail.com")
    			    .setIndividual(individual)
    			    .setType(AccountCreateParams.Type.CUSTOM)
    			    .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
    			    .setCapabilities(AccountCreateParams.Capabilities.builder()
    			    		.setTransfers(Transfers.builder().setRequested(true).build())
    			    		.setCardPayments(CardPayments.builder().setRequested(true).build())
    			    		.build())
    			    .build();
    			Account account = Account.create(params);
    			
    			return account.getId();
    }
    
    @PostMapping("/stripe/webhook")
	public String handle(@RequestBody String payload, HttpServletRequest request,HttpServletResponse response) {
		System.out.println("payload::"+payload);

          String sigHeader = request.getHeader("Stripe-Signature");
    	  Event event = null;

    	  try {
    	    event = Webhook.constructEvent(
    	      payload, sigHeader, endpointSecret
    	    );
    	    
    	  } catch (JsonSyntaxException e) {
    	    System.out.println("Error parsing payload: " + e.getMessage());
    	    response.setStatus(400);
    	    return " ";
    	    
    	  } catch (SignatureVerificationException e) {
    	    System.out.println("Error verifying webhook signature: " + e.getMessage());
    	    response.setStatus(400);
    	    return " ";

    	  }

    	  // Deserialize the nested object inside the event
    	  EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
    	  StripeObject stripeObject = null;
    	  if (dataObjectDeserializer.getObject().isPresent()) {
    	    stripeObject = dataObjectDeserializer.getObject().get();
    	  } else {
    	    
    	  }

    	  // Handle the event
    	  switch (event.getType()) {
    	    case "payment_intent.succeeded":
    	      PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
    	      System.out.println("PaymentIntent was successful!");
    	      break;
    	    case "payment_method.attached":
    	      PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
    	      System.out.println("PaymentMethod was attached to a Customer!");
    	      break;
    	    // ... handle other event types
    	    default:
    	      System.out.println("Unhandled event type: " + event.getType());
    	  }

    	  response.setStatus(200);
    	  return "";
    	}
    	
}
