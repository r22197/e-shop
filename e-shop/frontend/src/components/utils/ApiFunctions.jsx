import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080"
});

export const createProduct = async (name, description, price, category) => {
    try {
        const response = await api.post("/api/products/create", { name, description, price, category });
        return response.data; // Změna z `response.status === 201` na `response.data`
    } catch (error) {
        console.error("Error while adding product:", error);
        throw error; // Změna na throw error, aby se chyba propagovala dál
    }
};

export const getAllProducts = async (pageNumber, pageSize) => {
    try {
        const response = await api.get(`/api/products?pageNumber=${pageNumber}&pageSize=${pageSize}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching products: " + error.message);
    }
};

export const createCategory = async (name, parentCategory) => {
    try {
        const response = await api.post("/api/category/create", { name, parentCategory });
        return response.data;
    } catch (error) {
        console.error("Error while creating category:", error);
        throw error;
    }
};


export const updateCategory = async (id, categoryData) => {
    try {
        const response = await api.put(`/api/category/${id}`, categoryData);
        return response.data;
    } catch (error) {
        throw new Error("Error updating category: " + error.message);
    }
};


export const getAllCategories = async () => {
    try {
        const response = await api.get("/api/category");
        return response.data;
    } catch (error) {
        throw new Error("Error fetching categories: " + error.message);
    }
};

export const getCategoryById = async (categoryId) => {
    try {
        const response = await axios.get(`/api/category/${categoryId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getProductsByCategory = async (categoryId) => {
    try {
        const response = await api.get(`/api/product/category/${categoryId}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching products by category: " + error.message);
    }
};

export const deleteProduct = async (productId) => {
    try {
        await api.delete(`/api/products/${productId}`);
    } catch (error) {
        throw new Error("Error deleting product: " + error.message);
    }
};