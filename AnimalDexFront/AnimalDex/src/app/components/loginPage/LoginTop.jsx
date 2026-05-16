import { View, Text, StyleSheet, Image } from "react-native";
import { colors } from "@/style/Global";

const styles = StyleSheet.create({
    LoginTopContainer: {
        marginBottom: 30,
        alignContent: 'center',
        alignItems: 'center',
    },
    LoginTop: {
        marginBottom: 5,
        flexDirection: 'row',
        alignItems: 'center',
    },
    LoginTopText: {
        fontSize: 40,
        fontWeight: 'bold',
        color: colors.text,
    },
    LoginTopTextBottom: {
        fontSize: 16,
        fontWeight: 'bold',
        color: colors.text,
    },
});

export default function LoginTop() {
    return (
        <View style={styles.LoginTopContainer}>
            <View style={styles.LoginTop}>
                <Image source={require('@/assets/images/logo-small.png')} style={{ width: 100, height: 100, marginRight: 5 }} />
                <Text style={styles.LoginTopText}>AnimalDex</Text>
            </View>
            <Text style={styles.LoginTopTextBottom}>Descubra e colecione animais incríveis!</Text>
        </View>
    );
}