import apiBaseUrl from "./apiBaseUrl";

const BASE_URL = "/api/shopping-cart";

export const getCart = async () => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching categories: " + error.message);
    }
};

export const addToShoppingCart = async (productId) => {
    try {
        const response = await apiBaseUrl.post(`${BASE_URL}/add/${productId}`, productId);
        return response.data;
    } catch (error) {
        throw new Error("Error while creating category: " + error.message);
    }
};

export const updatedProductCart = async (productId, newQuantity) => {
    try {
        const response = await apiBaseUrl.put(`${BASE_URL}/${productId}`, newQuantity);
        return response.data;
    } catch (error) {
        throw new Error("Error updating category: " + error.message);
    }
};

export const removeFromShoppingCart = async (productId) => {
    try {
        await apiBaseUrl.delete(`${BASE_URL}/${productId}`);
    } catch (error) {
        throw new Error("Error deleting product: " + error.message);
    }
};