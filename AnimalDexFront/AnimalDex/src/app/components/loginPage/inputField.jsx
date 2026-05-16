import { View, TextInput, Image, StyleSheet } from "react-native";
import { colors } from "@/style/Global";

export default function IField({
  placeholder,
  onChangeText,
  style,
  imageSource,
}) {
  return (
    <View style={styles.container}>
      <Image source={imageSource} style={styles.icon} />

      <TextInput
        placeholder={placeholder}
        onChangeText={onChangeText}
        style={style}
        placeholderTextColor={colors.textSecondary}
      />
    </View>
  );
}

export function InputPasswordField({
  placeholder,
  onChangeText,
  style,
  imageSource,
}) {
  return (
    <View style={styles.container}>
      <Image source={imageSource} style={styles.icon} />

      <TextInput
        placeholder={placeholder}
        onChangeText={onChangeText}
        style={style}
        secureTextEntry
        placeholderTextColor={colors.textSecondary}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
  },

  icon: {
    width: 25,
    height: 25,
    marginRight: 10,
    marginBottom: 15,
    position: 'absolute',
    left: 10,
    zIndex: 1,
  },
});