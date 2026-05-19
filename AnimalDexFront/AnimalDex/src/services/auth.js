import api from "@/services/api";
import { saveToken, getToken } from "../app/utils/auth";


export async function login(username, password) {
    console.log("Sending:", { username, password });
    const response = await api.post("/auth/login", { username, password });
    await saveToken(response.data.token);
    return response.data;
}

const picNum = 0;

export async function register(username, password) {
    const response = await api.post("/auth/signup", { username, password, picNum });
    await saveToken(response.data.token);
    return response.data;
}

export async function refreshToken() {
    const token = await getToken();
    const response = await api.post("/auth/refreshToken", null, {
        headers: { Authorization: `Bearer ${token}` }
    });
    await saveToken(response.data.token);
    return response.data;
}