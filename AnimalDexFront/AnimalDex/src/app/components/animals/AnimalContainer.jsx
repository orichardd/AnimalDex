import { useState } from "react";
import { View, StyleSheet, Image, Text, TouchableOpacity } from "react-native";
import AnimalPanel from "./AnimalPanel";
import { colors } from "@/style/Global";

export default function AnimalContainer({ photoURL, name, scientificName, rarity, foundNumber, habitat, distribution, weight, size, diet, curiosity, points }) {
    const [panelVisible, setPanelVisible] = useState(false);

    return (
        <>
            <TouchableOpacity style={styles.main} onPress={() => setPanelVisible(true)}>
                <View style={styles.photoContainer}>
                    <Image source={photoURL} style={styles.photo} />
                </View>
                <View style={styles.infoContainer}>
                    <Text style={styles.name}>{name}</Text>
                    <Text style={styles.scientificName}>{scientificName}</Text>
                    <View style={styles.rarityRow}>
                        <Image source={require("@/assets/images/icons/paw.png")} style={styles.pawIcon} />
                        <View style={styles.rarityBadge}>
                            <Text style={styles.rarityText}>{rarity}</Text>
                        </View>
                    </View>
                </View>
            </TouchableOpacity>

            {panelVisible && (
                <AnimalPanel
                    visible={true}
                    onClose={() => setPanelVisible(false)}
                    photoURL={photoURL}
                    name={name}
                    scientificName={scientificName}
                    rarity={rarity}
                    foundNumber={foundNumber}
                    habitat={habitat}
                    distribution={distribution}
                    weight={weight}
                    size={size}
                    diet={diet}
                    curiosity={curiosity}
                    points={points}
                />
            )}
        </>
    );
}

const styles = StyleSheet.create({
    main: {
        width: 120,
        height: 200,
        backgroundColor: "#FFFFFF",
        borderRadius: 12,
        borderWidth: 1,
        borderColor: "#D6C4A8",
        overflow: "hidden",
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.08,
        shadowRadius: 6,
        elevation: 3,
    },
    photoContainer: {
        width: "100%",
        height: 100,
    },
    photo: {
        width: "100%",
        height: "100%",
        resizeMode: "cover",
    },
    infoContainer: {
        padding: 8,
        gap: 4,
        flex: 1,
        justifyContent: "space-between",
    },
    name: {
        fontSize: 16,
        fontWeight: "700",
        color: "#2D5A27",
    },
    scientificName: {
        fontSize: 12,
        color: "#888888",
        fontStyle: "italic",
        marginBottom: 4,
    },
    rarityRow: {
        flexDirection: "row",
        alignItems: "center",
        gap: 10,
        marginTop: 4,
    },
    pawIcon: {
        width: 20,
        height: 20,
        resizeMode: "contain",
    },
    rarityBadge: {
        backgroundColor: "#E8F5E9",
        borderRadius: 20,
        paddingHorizontal: 14,
        paddingVertical: 4,
    },
    rarityText: {
        fontSize: 12,
        fontWeight: "600",
        color: "#4CAF50",
        letterSpacing: 0.5,
    },
});