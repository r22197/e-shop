import apiBaseUrl from "./apiBaseUrl";


export const register = async (userData) => {
    try {
        const response = await apiBaseUrl.post('/register/user', userData);
        return response.data;
    } catch (error) {
        throw new Error('Registration failed: ' + error.message);
    }
};

export const login = async (credentials) => {
    try {
        const response = await apiBaseUrl.post('/login', credentials);
        return response.data;
    } catch (error) {
        throw new Error('Login failed: ' + error.message);
    }
};