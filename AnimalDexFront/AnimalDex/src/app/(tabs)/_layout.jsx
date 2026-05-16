import { Tabs } from "expo-router";
import { globalScale, colors } from "@/style/Global";
import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
    tabBar: {
        backgroundColor: colors.background,
        borderBlockColor: colors.background,
    },
});

export default function Layout() {
    return (
        <Tabs
            screenOptions={{
                headerShown: false,

                tabBarStyle: {

                    position: 'absolute',
                    bottom: 10,

                    marginHorizontal: '6%',

                    height: 70,
                    borderRadius: 30,

                    backgroundColor: colors.brown_light,
                    borderTopWidth: 0,
                }
            }}
        >
            <Tabs.Screen
                name="index"
                options={{
                    title: "Home",
                }}
            />


            <Tabs.Screen
                name="DexPage"
                options={{
                    title: "Dex",
                }}
            />

            <Tabs.Screen
                name="LoginPage"
                options={{
                    title: "Login",
                }}
            />

            <Tabs.Screen
                name="ProfilePage"
                options={{
                    title: "Profile",
                }}
            />  

        </Tabs>
    );
}