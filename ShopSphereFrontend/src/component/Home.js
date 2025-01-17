import React, { useState, useEffect,useCallback } from 'react';
import Navbar from '../common/Navbar';
import InfiniteScroll from 'react-infinite-scroll-component';
import { apiService } from '../service/ApiService';
import { Form } from "react-bootstrap";
import { debounce } from 'lodash';
import Swal from 'sweetalert2';

const Home = () => {
  const PAGE_SIZE = 6;

  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(0); 
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState('');
  const [cartProducts, setCartProducts] = useState(new Map());
  
  useEffect(() => {
    fetchProducts();
    fetchCartProduct();
  }, []);

  const fetchProducts = async (search) => {
    if (loading) return; 
    setLoading(true);
    
    try {
      const fetchedProducts = await apiService.getProducts({ page, size: PAGE_SIZE ,search});
      const newProducts = fetchedProducts.data.data.list;

      setProducts(prevProducts => [...prevProducts, ...newProducts]); 

      if (newProducts.length < PAGE_SIZE || fetchedProducts.data.data.total <= (page + 1) * PAGE_SIZE) {
        setHasMore(false);
      } else {
        setPage(prevPage => prevPage + 1);
      }
    } catch (error) {
      console.error("Error fetching products:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchCartProduct = async () => { 
      const fetchedProducts = await apiService.myCart();
      console.log("Cart products " , fetchedProducts)
      setCartProducts(prevProducts => {
        const updatedProducts = new Map(prevProducts);

        if (fetchedProducts.data && Array.isArray(fetchedProducts.data.data.list)) {
          fetchedProducts.data.data.list.forEach(product => {
              updatedProducts.set(product.product.id, product);
          });
        }
        return updatedProducts;
      });
  };

  const handleAddToCart = async(id) => {
    const response = await apiService.addToCart(id,1)
    console.log("response ",response);
    if(response.status === 200){
      fetchCartProduct()
      Swal.fire('Success', response.data.message, 'success');
      }
  };

  const removeFromCart = async(id) => {
    const response = await apiService.removeFromCart(id)
    console.log("response ",response);
    if(response.status === 200){
      cartProducts.delete(id);
      fetchCartProduct()
      }
  };

  const handleSearchChange = (e) => {
    const newSearch = e.target.value;
    setSearch(newSearch);
    throttledSearch(newSearch);
  };

  const throttledSearch = useCallback(debounce((newSearch) => {
    setProducts([]);
    setPage(0);
    setHasMore(true);
    fetchProducts(newSearch.trim() === "" ? '' : newSearch);
  }, 1000), []);


  return (
    <div className="home-container">
      <Navbar />
      <h1 className='header'>Product List</h1>
      <div className="submission-search-area">
        <Form.Control
          type="text"
          placeholder="Search Property Name..."
          value={search}
          onChange={handleSearchChange}
          className="mr-sm-2"
        />
        <img src={require("../assets/search-icon.svg").default} alt="Search Icon" />                             
      </div>

      <InfiniteScroll
        dataLength={products.length}
        next={fetchProducts}
        hasMore={hasMore}
        scrollableTarget="scrollableDiv"
        loader={<h4>Loading...</h4>}
      >
        <div className="product-list">
          {products.map((product) => (    
            <div key={product.id} className="product-card">
              <img
                src={`http://localhost:8081/${product.imagePath}`} 
                className="product-image"
                alt={product.name}
              /> 
              <h2 className='product-label'>Product Name</h2>
              <h2 className="product-name">{product.name}</h2>

              <div className="price-brand-container">
                <p className="product-label">Price:</p>
                <p className="product-price">₹{product.price.toFixed(2)}</p>
                <p className="product-category">{product.category.brand}</p>
              </div>

              {cartProducts.has(product.id) ? (
                <button 
                  className="remove-cart-button" 
                  onClick={() => removeFromCart(product.id)}
                  >
                  Remove From Cart
                </button>) :(
                <button 
                  className="add-to-cart-button" 
                  onClick={() => handleAddToCart(product.id)}
                  >
                  Add to Cart
                </button>
              )}
            </div>
          ))}
        </div>
      </InfiniteScroll>
    </div>
  );
};

export default Home;
