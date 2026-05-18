import axios from "axios";
import { getToken } from "../app/utils/auth";

const api = axios.create({
    baseURL: "http://192.168.1.111:8080",
});

// Automatically attach JWT to every request
api.interceptors.request.use(async (config) => {
    const token = await getToken();
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;