import { View, Text, Image, StyleSheet, TouchableOpacity, Modal, ScrollView } from "react-native";
import { colors } from "@/style/Global";

export default function AnimalPanel({ photoURL, name, scientificName, rarity, foundNumber, habitat, distribution, weight, size, diet, curiosity, points, visible, onClose, isCapture }) {
    return (
        <Modal
            visible={visible}
            transparent={true}
            animationType="fade"
            onRequestClose={onClose}
        >
            {/* Dark overlay — blocks tab switching */}
            <TouchableOpacity style={styles.overlay} activeOpacity={1} onPress={onClose}>
                {/* Panel — stops touch from bubbling to overlay */}
                <TouchableOpacity style={styles.panel} activeOpacity={1} onPress={() => { }}>
                    <ScrollView showsVerticalScrollIndicator={false}>

                        {/* Header: name + photo */}
                        <View style={styles.header}>
                            <View style={styles.headerLeft}>
                                <Text style={styles.name}>{name}</Text>
                                <Text style={styles.scientificName}>{scientificName}</Text>

                                {/* Rarity box */}
                                <View style={styles.rarityBox}>
                                    <Text style={styles.rarityIcon}>♠</Text>
                                    <Text style={styles.rarityLabel}>{rarity}</Text>
                                </View>

                                <Text style={styles.foundText}>Encontrado {foundNumber} vezes</Text>
                            </View>

                            {/* Photo */}
                            <View style={styles.photoWrapper}>
                                <Image source={photoURL} style={styles.photo} />
                                <View style={styles.pawBadge}>
                                    <Image source={require("@/assets/images/icons/paw.png")} style={styles.pawIcon} style={styles.icon} />
                                </View>
                            </View>
                        </View>

                        {/* Info grid */}
                        <View style={styles.grid}>
                            <View style={styles.infoCard}>
                                <View style={styles.cardHeader}>
                                    <Text style={styles.cardTitle}>Habitat</Text>
                                    <Image source={require("@/assets/images/icons/earth.png")} style={styles.cardIcon} style={styles.icon} />
                                </View>
                                <Text style={styles.cardBody}>{habitat}</Text>
                            </View>

                            <View style={styles.infoCard}>
                                <View style={styles.cardHeader}>
                                    <Text style={styles.cardTitle}>Distribuição</Text>
                                    <Image source={require("@/assets/images/icons/compass.png")} style={styles.cardIcon} style={styles.icon} />
                                </View>
                                <Text style={styles.cardBody}>{distribution}</Text>
                            </View>

                            <View style={styles.infoCard}>
                                <View style={styles.cardHeader}>
                                    <Text style={styles.cardTitle}>Peso</Text>
                                    <Image source={require("@/assets/images/icons/balance.png")} style={styles.cardIcon} style={styles.icon} />
                                </View>
                                <Text style={styles.cardValue}>{weight}</Text>
                            </View>

                            <View style={styles.infoCard}>
                                <View style={styles.cardHeader}>
                                    <Text style={styles.cardTitle}>Tamanho</Text>
                                    <Image source={require("@/assets/images/icons/size.png")} style={styles.cardIcon} style={styles.icon} />
                                </View>
                                <Text style={styles.cardValue}>{size}</Text>
                            </View>
                        </View>

                        {/* Diet — full width */}
                        <View style={[styles.infoCard, styles.fullWidth]}>
                            <Text style={styles.cardTitle}>Dieta</Text>
                            <Text style={styles.cardBody}>{diet}</Text>
                        </View>

                        {/* Curiosity + Points row */}
                        <View style={styles.bottomRow}>
                            <View style={[styles.infoCard, styles.curiosityCard]}>
                                <View style={styles.cardHeader}>
                                    <Text style={styles.cardTitle}>Curiosidade</Text>
                                    <Image source={require("@/assets/images/icons/brain.png")} style={styles.cardIcon} style={styles.icon} />
                                </View>
                                <Text style={styles.cardBody}>{curiosity}</Text>
                            </View>

                            <View style={styles.pointsCard}>
                                <Text style={styles.pointsValue}>+{points}</Text>
                                <View style={styles.pointsBadge}>
                                    <Text style={styles.pointsBadgeText}>z</Text>
                                </View>
                            </View>
                        </View>

                        {/* Register button */}

                        <TouchableOpacity style={styles.registerButton} onPress={() => {
                            if(isCapture) {
                                // Handle capture logic
                            } else {
                                onClose(); // Just close the panel if already captured
                            }
                        }}>
                            {isCapture && (
                                <Text style={styles.registerText}>REGISTRAR</Text>
                            ) || (
                                <Text style={styles.registerText}>CONTINUAR</Text>
                            )}
                        </TouchableOpacity>

                    </ScrollView>
                </TouchableOpacity>
            </TouchableOpacity>
        </Modal>
    );
}

const styles = StyleSheet.create({
    overlay: {
        flex: 1,
        backgroundColor: "rgba(0, 0, 0, 0.5)",
        justifyContent: "center",
        alignItems: "center",
        padding: 16,
    },
    panel: {
        width: "100%",
        maxWidth: 400,
        maxHeight: "90%",
        backgroundColor: "#F5F0E8",
        borderRadius: 20,
        padding: 16,
        shadowColor: "#000",
        shadowOffset: { width: 0, height: 8 },
        shadowOpacity: 0.3,
        shadowRadius: 16,
        elevation: 10,
    },

    // Header
    header: {
        flexDirection: "row",
        justifyContent: "space-between",
        marginBottom: 16,
        gap: 12,
    },
    headerLeft: {
        flex: 1,
        gap: 6,
    },
    name: {
        fontSize: 22,
        fontWeight: "800",
        color: "#2D2D2D",
    },
    scientificName: {
        fontSize: 13,
        color: "#777",
        fontStyle: "italic",
        marginBottom: 4,
    },
    rarityBox: {
        borderWidth: 1,
        borderColor: "#D6C9B0",
        borderRadius: 10,
        padding: 10,
        alignItems: "center",
        backgroundColor: "#FAFAF5",
        width: 110,
    },
    rarityIcon: {
        fontSize: 28,
        color: "#888",
        marginBottom: 4,
    },
    rarityLabel: {
        fontSize: 13,
        fontWeight: "600",
        color: "#555",
    },
    foundText: {
        fontSize: 11,
        color: "#888",
        marginTop: 4,
    },
    photoWrapper: {
        width: 140,
        height: 160,
        borderRadius: 14,
        overflow: "hidden",
        position: "relative",
    },
    photo: {
        width: "100%",
        height: "100%",
        resizeMode: "cover",
    },
    pawBadge: {
        position: "absolute",
        bottom: 8,
        left: 8,
        width: 32,
        height: 32,
        borderRadius: 16,
        backgroundColor: "rgba(255,255,255,0.85)",
        alignItems: "center",
        justifyContent: "center",
    },
    pawIcon: {
        fontSize: 16,
    },

    // Info grid (2 columns)
    grid: {
        flexDirection: "row",
        flexWrap: "wrap",
        gap: 8,
        marginBottom: 8,
    },
    infoCard: {
        flex: 1,
        minWidth: "47%",
        backgroundColor: "#FAFAF5",
        borderWidth: 1,
        borderColor: "#E0D5C0",
        borderRadius: 12,
        padding: 10,
        gap: 4,
    },
    fullWidth: {
        minWidth: "100%",
        marginBottom: 8,
    },
    cardHeader: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
    },
    cardTitle: {
        fontSize: 13,
        fontWeight: "700",
        color: "#2D2D2D",
    },
    cardIcon: {
        fontSize: 16,
    },
    cardBody: {
        fontSize: 11,
        color: "#666",
        lineHeight: 16,
        marginTop: 2,
    },
    cardValue: {
        fontSize: 18,
        fontWeight: "700",
        color: "#3A3A3A",
        textAlign: "center",
        marginTop: 6,
    },

    // Bottom row: curiosity + points
    bottomRow: {
        flexDirection: "row",
        gap: 8,
        marginBottom: 16,
    },
    curiosityCard: {
        flex: 2,
    },
    pointsCard: {
        flex: 1,
        backgroundColor: "#FAFAF5",
        borderWidth: 1,
        borderColor: "#E0D5C0",
        borderRadius: 12,
        padding: 10,
        alignItems: "center",
        justifyContent: "center",
        gap: 8,
    },
    pointsValue: {
        fontSize: 22,
        fontWeight: "800",
        color: "#2D2D2D",
    },
    pointsBadge: {
        width: 36,
        height: 36,
        borderRadius: 18,
        backgroundColor: "#F5A623",
        alignItems: "center",
        justifyContent: "center",
    },
    pointsBadgeText: {
        fontSize: 16,
        fontWeight: "800",
        color: "#FFF",
    },

    // Register button
    registerButton: {
        backgroundColor: "#A8D878",
        borderRadius: 14,
        paddingVertical: 16,
        alignItems: "center",
    },
    registerText: {
        fontSize: 16,
        fontWeight: "800",
        color: "#3A5C1A",
        letterSpacing: 2,
    },
    icon:{
        width: 20,
        height: 20,
        resizeMode: "contain",
    }
});