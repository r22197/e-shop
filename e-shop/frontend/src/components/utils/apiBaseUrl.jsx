import axios from "axios";

const apiBaseUrl = axios.create({
    baseURL: "http://localhost:8080"
});

export default apiBaseUrl;
