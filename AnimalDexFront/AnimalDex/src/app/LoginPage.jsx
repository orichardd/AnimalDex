import { Text, View, StyleSheet } from "react-native";
import { globalStyles } from "../style/Global";
import LoginSection from "./components/loginPage/LoginSection";

export default function LoginPage() {
    return (
        <View style={globalStyles.view}>

            <LoginSection />
        </View>
    );
}