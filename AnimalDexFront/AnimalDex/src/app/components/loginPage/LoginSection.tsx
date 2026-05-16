import { Text, View, StyleSheet } from "react-native";
import { colors, globalScale, globalStyles } from "@/style/Global";
import  IField, { InputPasswordField } from "./inputField";
import LoginButton from "./LoginButton";
import LoginTop from "./LoginTop";

export default function LoginSection() {
    return (
        <View style={styles.main}>
            <LoginTop />
            <IField placeholder="username" onChangeText={undefined} style={styles.IField} imageSource={require('../../../../assets/images/icons/user_pic.png')} />
            <InputPasswordField placeholder="password" onChangeText={undefined} style={styles.IField} imageSource={require('../../../../assets/images/icons/password_lock.png')} />
            <Text style={styles.esqueci}>esqueceu sua senha?</Text>
            <LoginButton buttonStyle={styles.LoginButton} onPress={undefined} title="Entrar" buttonTextStyle={styles.LoginButtonText} />
            <LoginButton buttonStyle={styles.SigInButton} onPress={undefined} title="Cadastrar" buttonTextStyle={styles.SigInButtonText} />
        </View>
    );
}

const styles = StyleSheet.create({
    main: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        width: '100%',
    },
    esqueci: {
        color: colors.green_strong,
        width: globalScale.screenWidth,
        textAlign: 'right',
        marginBottom: 15
    },
    IField: {
        backgroundColor: colors.brown_light,
        borderColor: colors.brown_outline,
        borderWidth: 1,
        borderRadius: globalScale.outerRadius,
        padding: 10,
        width: globalScale.screenWidth,
        height: globalScale.smallContainerHeight,
        marginBottom: 15,
        paddingLeft: 40,
    },
    LoginButton: {
        backgroundColor: colors.green_light,
        width: globalScale.screenWidth,
        height: globalScale.smallContainerHeight,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: globalScale.outerRadius,
        borderColor: colors.green_medium,
        borderWidth: 1,
        marginBottom: 10,
    },
    LoginButtonText: {
        color: colors.green_strong,
        fontWeight: 'bold',
    },
    SigInButton: {
        backgroundColor: colors.brown_light,
        width: globalScale.screenWidth,
        height: globalScale.smallContainerHeight,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: globalScale.outerRadius,
        borderColor: colors.brown_outline,
        borderWidth: 1,
    },
    SigInButtonText: {
        color: colors.text,
        fontWeight: 'bold',
    },
});