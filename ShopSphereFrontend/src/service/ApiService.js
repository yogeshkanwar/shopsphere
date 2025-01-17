import client from './AxiosClient'

export const apiService = {
    register,
    Login,
    getProducts,
    addToCart,
    removeFromCart,
    myCart,
    checkout,
    connect,
    handleGoogleLogin,
    handleGithubLogin,
    ChatApi
}

async function register(data) {
    return await client.post(`/sign-up`,data);
}

async function Login(data) {
    return await client.post(`/login`,data);
}

async function getProducts(page,size,search) {
    return await client.post(`/products/filter`,page,size,search);
}

async function addToCart(productId,quantity) {
    return await client.post(`/cart`, null,{
        params : {productId,quantity}
    });
}

async function removeFromCart(productId) {
    return await client.delete(`/cart/`.concat(productId));
}

async function myCart() {
    return await client.get(`/cart`);
}


async function checkout() {
    return await client.post(`/api/create-checkout-session`);
}

async function connect() {
    return await client.post(`/api/connect`);
}

async function handleGoogleLogin() {
    return await client.get(`/login/google`);
}

async function handleGithubLogin() {
    return await client.get(`/login/github`);
}

async function ChatApi(inputText) {
    return await client.get(`/chat?prompt=${inputText}`);
}


