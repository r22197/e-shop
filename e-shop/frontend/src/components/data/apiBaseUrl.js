import axios from "axios";

const apiBaseUrl = axios.create({
    baseURL: "http://localhost:8080"
});

export const getHeader = () => {
    const token = localStorage.getItem("token")
    return {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
    }
}

export default apiBaseUrl;
