import { View, Pressable, Text } from "react-native";

export default function LoginButton({ buttonStyle, onPress, title, buttonTextStyle }) {
    return (
        <Pressable style={buttonStyle} onPress={onPress} >
            <Text style={buttonTextStyle}>{title}</Text>
        </Pressable>
    );
}