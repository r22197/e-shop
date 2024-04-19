import apiBaseUrl from "./apiBaseUrl";

const BASE_URL = "/api/products";

export const getAllProducts = async (pageNumber, pageSize) => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}?pageNumber=${pageNumber}&pageSize=${pageSize}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching products: " + error.message);
    }
};

export const getProductById = async (productId) => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}/${productId}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching product by ID: " + error.message);
    }
};

export const createProduct = async (newProductData) => {
    try {
        const response = await apiBaseUrl.post(`${BASE_URL}`, newProductData);
        return response.data;
    } catch (error) {
        throw new Error("Error while adding product: " + error.message);
    }
};

export const updateProduct = async (productId, updatedProductData) => {
    try {
        const response = await apiBaseUrl.put(`${BASE_URL}/${productId}`, updatedProductData);
        return response.data;
    } catch (error) {
        throw new Error("Error updating product: " + error.message);
    }
};

export const deleteProduct = async (productId) => {
    try {
        await apiBaseUrl.delete(`${BASE_URL}/${productId}`);
    } catch (error) {
        throw new Error("Error deleting product: " + error.message);
    }
};