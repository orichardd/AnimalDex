import { Text, View, FlatList, ActivityIndicator, Image, StyleSheet, TouchableOpacity, ScrollView } from "react-native";
import { globalStyles } from "../../style/Global";
import AnimalContainer from "../components/animals/AnimalContainer";
import { useEffect, useState } from "react";
import api from "../../services/api";
import { colors, fontSizes, rarityColors } from "../../style/Global";

const RARITIES = [
    { label: "Todos",    value: null,       icon: require("@/assets/images/icons/rarities/all.png"),  color: colors.green_medium, bg: "#E8F5E9" },
    { label: "Comum",    value: "COMMON",   icon: require("@/assets/images/icons/rarities/common.png"),  color: rarityColors.common,    bg: "#F0F0F0" },
    { label: "Raro",     value: "RARE",     icon: require("@/assets/images/icons/rarities/rare.png"),  color: rarityColors.rare,     bg: "#EFEFFF" },
    { label: "Épico",    value: "EPIC",     icon: require("@/assets/images/icons/rarities/epic.png"),  color: rarityColors.epic,    bg: "#F9EEFF" },
    { label: "Lendário", value: "LEGENDARY",icon: require("@/assets/images/icons/rarities/legendary.png"),  color: rarityColors.legendary, bg: "#FFF5E0" },
    { label: "Cromático",value: "CHROMATIC",icon: require("@/assets/images/icons/rarities/chromatic.png"),  color: rarityColors.chromatic, bg: "#F3EEFF" },
    { label: "Extinto",  value: "EXTINCT",  icon: require("@/assets/images/icons/rarities/extinct.png"), color: rarityColors.extinct,    bg: "#EFEFEF" },
];

export default function DexPage() {
    const [animals, setAnimals] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedRarity, setSelectedRarity] = useState(null);

    useEffect(() => {
        api.get("/users/dex/get")
            .then(res => setAnimals(res.data))
            .catch(err => console.error("Error fetching dex data:", err))
            .finally(() => setLoading(false));
    }, []);

    const filtered = selectedRarity
        ? animals.filter(a => a.animal.rarity === selectedRarity)
        : animals;

    return (
        <View style={globalStyles.view}>
            {/* Header */}
            <View style={styles.topMain}>
                <Image source={require('@/assets/images/logo-small.png')} style={styles.logo} />
                <View style={{ flex: 1 }}>
                    <Text style={styles.topMainText}>Sua DEX</Text>
                    <Text style={styles.topSecondaryText}>Descubra e colecione animais incríveis</Text>
                </View>
                <View style={styles.countBadge}>
                    <Text style={styles.countText}>{animals.length}</Text>
                </View>
            </View>

            {/* Rarity filter */}
            <ScrollView horizontal showsHorizontalScrollIndicator={false} style={styles.filterScroll} contentContainerStyle={styles.filterRow}>
                {RARITIES.map(r => {
                    const active = selectedRarity === r.value;
                    return (
                        <TouchableOpacity
                            key={r.label}
                            style={[styles.filterItem, active && { borderColor: r.color, borderWidth: 2, backgroundColor: r.bg }]}
                            onPress={() => setSelectedRarity(r.value)}
                        >
                            <Image source={r.icon} style={[styles.filterIcon, { tintColor: active ? r.color : "#888" }]} />
                            <Text style={[styles.filterLabel, { color: active ? r.color : "#888" }]}>{r.label}</Text>
                        </TouchableOpacity>
                    );
                })}
            </ScrollView>

            {/* Grid */}
            {loading ? (
                <ActivityIndicator style={{ marginTop: 40 }} />
            ) : (
                <FlatList
                    data={filtered}
                    keyExtractor={(item) => item.id.toString()}
                    numColumns={3}
                    columnWrapperStyle={styles.row}
                    contentContainerStyle={styles.grid}
                    renderItem={({ item }) => (
                        <AnimalContainer
                            photoURL={require(`@/assets/images/tarantula.jpeg`)}
                            name={item.animal.commonName}
                            scientificName={item.animal.scientificName}
                            rarity={item.animal.rarity}
                            foundNumber={67}
                            habitat={"Florestas tropicais"}
                            distribution={"América do Sul"}
                            curiosity={"Essa tarântula é conhecida por sua coloração vibrante e comportamento defensivo."}
                            diet={item.animal.diet}
                            size={item.animal.size}
                            weight={item.animal.weight}
                            points={6742}
                        />
                    )}
                />
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    topMain: {
        marginTop: 30,
        marginBottom: 16,
        flexDirection: "row",
        alignItems: "center",
        gap: 10,
        paddingHorizontal: 12,
    },
    logo: {
        width: 70,
        height: 70,
        marginRight: 5,
    },
    topMainText: {
        fontSize: fontSizes.XL,
        fontWeight: "bold",
        color: colors.text,
    },
    topSecondaryText: {
        fontSize: fontSizes.SM,
        color: colors.text,
    },
    countBadge: {
        backgroundColor: "#F5F0E8",
        borderRadius: 20,
        paddingHorizontal: 14,
        paddingVertical: 6,
        borderWidth: 1,
        borderColor: "#E0D5C0",
    },
    countText: {
        fontWeight: "700",
        color: colors.text,
        fontSize: fontSizes.SM,
    },
    filterScroll: {
        marginBottom: 0,
        flexGrow: 0,
        marginBottom: 20
    },
    filterRow: {
        paddingHorizontal: 12,
        gap: 8,
        flexDirection: "row",
    },
    filterItem: {
        alignItems: "center",
        justifyContent: "center",
        paddingVertical: 8,
        paddingHorizontal: 10,
        borderRadius: 12,
        borderWidth: 1,
        borderColor: "#E0D5C0",
        backgroundColor: "#FAFAF5",
        minWidth: 45,
        maxHeight: 70,
    },
    filterIcon: {
        height: 20,
        width: 20,
        marginBottom: 4,
        resizeMode: "contain",
    },
    filterLabel: {
        fontSize: 10,
        fontWeight: "600",
    },
    grid: {
        paddingHorizontal: 12,
        paddingBottom: 20,
    },
    row: {
        gap: 8,
        marginBottom: 8,
    },
});