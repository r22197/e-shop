import apiBaseUrl, {getHeader} from "./apiBaseUrl";

const BASE_URL = "/api/shopping-cart";

export const getCart = async () => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}`,
            { headers: getHeader() })
        return response.data;
    } catch (error) {
        throw new Error("Error fetching cart has product: " + error.message);
    }
};

export const addToShoppingCart = async (productId) => {
    try {
        const response = await apiBaseUrl.post(`${BASE_URL}/add/${productId}`,
            {},
            { headers: getHeader() });
        return response.data;
    } catch (error) {
        throw new Error("Error while creating cart has product: " + error.message);
    }
};

export const updateProductCartQuantity = async (id, quantity) => {
    try {
        const response = await apiBaseUrl.put(`${BASE_URL}/${id}`, { quantity });
        return response.data;
    } catch (error) {
        throw new Error("Error updating cart has product: " + error.message);
    }
};

export const removeFromShoppingCart = async (productId) => {
    try {
        await apiBaseUrl.delete(`${BASE_URL}/${productId}`);
    } catch (error) {
        throw new Error("Error deleting product: " + error.message);
    }
};

export const getTotalPrice = async () => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}/total-price`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching total price: " + error.message);
    }
};