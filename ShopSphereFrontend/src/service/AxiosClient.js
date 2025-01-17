import axios from "axios";
import Swal from "sweetalert2";

const client = axios.create({
  baseURL: 'http://192.168.2.46:8081', 
  timeout: 10000, 
});



client.interceptors.request.use(
  config => {
      const token=localStorage.getItem("token")
      console.log("response token is ", token)
      
      if (!config.url.startsWith('/login') && !config.url.startsWith('/sign-up')) {
          config.headers['Authorization'] = 'Bearer ' + token;
      } else {
          delete config.headers['Authorization']; 
      }
      return config;
  },
  error => {
      return Promise.reject(error);
  }
);

// Add a response interceptor
client.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    console.log("error",error.response)
    if (error.response && (error.response.status == 401 || error.response.status == 403)) {
      
      Swal.fire({
            title: 'Error',
            text: error.response?.data?.message,
            icon: 'error',
          }).then(() => {
            localStorage.clear(); 
            window.location.href = "/"   
          });
    } else {
      const message = error.response?.data?.message || "Something went wrong!"
      Swal.fire({ icon: 'error', text: message, button: "OK" });
    }
    return error; 

  }
);

export default client;