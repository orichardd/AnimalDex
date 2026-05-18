import { useEffect, useState } from "react";
import { Tabs, router } from "expo-router";
import { getToken } from "../utils/auth";
import { colors } from "@/style/Global";
import { StyleSheet } from "react-native";

export default function Layout() {
  const [checked, setChecked] = useState(false);

  useEffect(() => {
    getToken().then((token) => {
      console.log("Token found:", token);
      if (!token) {
        router.replace("/LoginPage");
      }
      setChecked(true);
    });
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
      <Tabs.Screen name="index" options={{ title: "Home" }} />
      <Tabs.Screen name="DexPage" options={{ title: "Dex" }} />
      <Tabs.Screen name="Picture" options={{ title: "Picture" }} />
      <Tabs.Screen name="ProfilePage" options={{ title: "Profile" }} />

    </Tabs>
  );
}