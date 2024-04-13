import apiBaseUrl from "./apiBaseUrl";

const BASE_URL = "/api/categories";

export const getAllCategories = async () => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching categories: " + error.message);
    }
};

export const getCategoryById = async (categoryId) => {
    try {
        const response = await apiBaseUrl.get(`${BASE_URL}/${categoryId}`);
        return response.data;
    } catch (error) {
        throw new Error("Error fetching category by ID: " + error.message);
    }
};

export const createCategory = async (newCategoryData) => {
    try {
        const response = await apiBaseUrl.post(`${BASE_URL}`, newCategoryData);
        return response.data;
    } catch (error) {
        throw new Error("Error while creating category: " + error.message);
    }
};

export const updateCategory = async (CategoryId, updatedCategoryData) => {
    try {
        const response = await apiBaseUrl.put(`${BASE_URL}/${CategoryId}`, updatedCategoryData);
        return response.data;
    } catch (error) {
        throw new Error("Error updating category: " + error.message);
    }
};

export const deleteCategory = async (categoryId) => {
    try {
        await apiBaseUrl.delete(`${BASE_URL}/${categoryId}`);
    } catch (error) {
        throw new Error("Error deleting product: " + error.message);
    }
};
