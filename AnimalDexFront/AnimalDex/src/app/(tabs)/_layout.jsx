import { useEffect, useState } from "react";
import { Tabs, router } from "expo-router";
import { getToken, removeToken } from "../utils/auth";
import { colors } from "@/style/Global";
import { TouchableOpacity, View, Image, StyleSheet } from "react-native";
import { refreshToken } from "@/services/auth";

export default function Layout() {
  const [checked, setChecked] = useState(false);

  useEffect(() => {
    async function checkAuth() {
      const token = await getToken();

      if (!token) {
        router.replace("/LoginPage");
        return;
      }

      try {
        await refreshToken();
        setChecked(true);
      } catch (err) {
        console.error("Token inválido:", err);
        await removeToken();
        router.replace("/LoginPage");
      }
    }

    checkAuth();
  }, []);
  if (!checked) return null;

  return (
    <Tabs
      screenOptions={{
        headerShown: false,
        tabBarStyle: {
          position: "absolute",
          bottom: 10,
          marginHorizontal: "6%",
          height: 70,
          borderRadius: 30,
          backgroundColor: colors.brown_light,
          borderTopWidth: 0,
        },
      }}
    >
      <Tabs.Screen name="index"
        options={{
          title: "Home",
          tabBarShowLabel: false,
          tabBarIconStyle: {
            marginTop: 10,
          },

          tabBarIcon: ({ focused }) => (
            <Image
              source={require("@/assets/images/icons/ui/home.png")}
              style={{
                width: 24,
                height: 24,
                tintColor: focused ? colors.green_medium : colors.text,
              }}
            />
          ),
        }}
      />
      <Tabs.Screen name="DexPage"
        options={{
          title: "Dex",
          tabBarShowLabel: false,
          tabBarIconStyle: {
            marginTop: 10,
          },
          tabBarIcon: ({ focused }) => (
            <Image
              source={require("@/assets/images/icons/ui/dex.png")}
              style={{
                width: 24,
                height: 24,
                tintColor: focused ? colors.green_medium : colors.text,
              }}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="Picture"
        options={{
          title: "",
          tabBarIcon: () => (
            <View style={camStyles.wrapper}>
              <Image
                source={require("@/assets/images/icons/ui/camera.png")}
                style={camStyles.icon}
              />
            </View>
          ),
          tabBarButton: (props) => (
            <TouchableOpacity
              {...props}
              style={camStyles.button}
              activeOpacity={0.8}
            />
          ),
        }}
      />

        <Tabs.Screen name="Missions"
        options={{
          title: "Dex",
          tabBarShowLabel: false,
          tabBarIconStyle: {
            marginTop: 10,
          },
          tabBarIcon: ({ focused }) => (
            <Image
              source={require("@/assets/images/icons/ui/missions.png")}
              style={{
                width: 24,
                height: 24,
                tintColor: focused ? colors.green_medium : colors.text,
              }}
            />
          ),
        }}
      />

      <Tabs.Screen name="ProfilePage"
        options={{
          title: "Dex",
          tabBarShowLabel: false,
          tabBarIconStyle: {
            marginTop: 10,
          },
          tabBarIcon: ({ focused }) => (
            <Image
              source={require("@/assets/images/icons/user_pic.png")}
              style={{
                width: 24,
                height: 24,
                tintColor: focused ? colors.green_medium : colors.text,
              }}
            />
          ),
        }}
      />

    </Tabs>
  );
}
const camStyles = StyleSheet.create({
  button: {
    top: -10,           // sobe o botão acima da tab bar
    justifyContent: "center",
    alignItems: "center",
  },
  wrapper: {
    width: 70,
    height: 70,
    borderRadius: 35,
    backgroundColor: colors.green_medium, // verde do seu app
    justifyContent: "center",
    alignItems: "center",
    // sombra
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.2,
    shadowRadius: 6,
    elevation: 6,
  },
  icon: {
    width: 28,
    height: 28,
    tintColor: "#fff",
  },
});