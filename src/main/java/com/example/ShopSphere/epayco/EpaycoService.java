package com.example.ShopSphere.epayco;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.Credentials;

@Service
public class EpaycoService {
	
	  static String username = "491d6a0b6e992cf924edd8d3d088aff1";
      static String password = "268c8e0162990cf2ce97fa7ade2eff5a";
	  static String url_apify = "https://apify.epayco.co/";
	
	
	 public static String getBearerToken() throws Exception {
	        OkHttpClient client = new OkHttpClient().newBuilder()
	                .build();
	        // Basic Auth credentials
	      
	        String credentials = Credentials.basic(username, password);

	        MediaType mediaType = MediaType.parse("application/json");
	        RequestBody body = RequestBody.create("{}", mediaType); 

	        Request request = new Request.Builder()
	                .url(url_apify+"login")
	                .addHeader("Content-Type", "application/json")
	                .addHeader("Authorization", credentials) 
	                .post(body)
	                .build();

	        try (Response response = client.newCall(request).execute()) {
	            if (response.isSuccessful()) {
	            	JSONObject responseBody = new JSONObject(response.body().string());

	                return responseBody.getString("token"); 
	            } else {
	                throw new RuntimeException("Request failed with status code: " + response.code());
	            }
	        }
	 }
	 
	 public static String createCustomer() throws Exception {
		 OkHttpClient client = new OkHttpClient();
	        // Define MediaType for JSON
	        MediaType JSON = MediaType.get("application/json; charset=utf-8");
	        String token = "Bearer " + getBearerToken();
	        System.out.println("token : " + token);

	        // Create JSON payload
	        String jsonPayload = "{\n" +
	            "    \"docType\":\"CC\",\n" +
	            "    \"docNumber\":\"123456789\",\n" +
	            "    \"name\":\"jon\",\n" +
	            "    \"lastName\":\"doe\",\n" +
	            "    \"email\":\"jondoe@hotmail.com\",\n" +
	            "    \"cellPhone\":\"0000000000\",\n" +
	            "    \"phone\":\"0000000\",\n" +
	            "    \"requireCardToken\":false\n" +
	            "}";

	        // Create RequestBody
	        RequestBody body = RequestBody.create(jsonPayload, JSON);

	        // Build the Request
	        Request request = new Request.Builder()
	            .url(url_apify + "token/customer")
	            .post(body)
	            .addHeader("Authorization", token)
	            .addHeader("Content-Type", "application/json")
	            .build();

	        // Execute the Request and handle response
	        try(Response response = client.newCall(request).execute()){
	        	if (response.isSuccessful()) {
	                String responseBody = response.body().string();

	                JSONObject jsonResponse = new JSONObject(responseBody);
	                JSONObject dataObject = jsonResponse.getJSONObject("data");
	                JSONObject customerData = dataObject.getJSONObject("data");

	                // Extract the customerId
	                String customerId = customerData.getString("customerId");
	                System.out.println("Customer ID: " + customerId);

	                // Return or use the customerId as needed
	                return customerId;
	        	} else {
	                throw new RuntimeException("Request failed with status code: " + response.code());
	            }	
	        }	     
	 }
	 
//	 public static String addCard
	 
	 	   
	 public static void main(String[] args) throws Exception {
//		 System.out.println(getBearerToken());
		 System.out.println(createCustomer());

		 
	 }
	

}
