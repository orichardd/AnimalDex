import AsyncStorage from "@react-native-async-storage/async-storage";

export const saveToken = (token) => AsyncStorage.setItem("jwt", token);
export const getToken = () => AsyncStorage.getItem("jwt");
export const removeToken = () => AsyncStorage.removeItem("jwt");

export default { saveToken, getToken, removeToken };