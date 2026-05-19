import { StyleSheet } from 'react-native';

export const colors = {
    text: '#503A25',
    textSecondary: '#A09278',
    background: '#FEF8ED',
    green_strong: '#385A30',
    green_medium: '#769D57',
    green_light: '#BDD898',
    brown_background: '#EBCF9D',
    brown_light: '#FEF0D3',
    brown_outline: '#F3DAAD',
};

export const rarityColors = {
    common: "#ABA8A6",
    rare: "#95BAE9",
    epic: "#C6A9E7",
    legendary: "#FFC76E",
    chromatic: "#EC8DFF",
    extinct: "#454545",
}

export const fontSizes = {
    XXL:40,
    XL:35,
    X:30,
    L:25,
    G:20,
    M:15,
    P:10
}

export const globalScale = {
    screenWidth: '85%',
    screenWidth2: 85,
    smallContainerHeight: 45,
    outerRadius: 10,
}

export const globalStyles = StyleSheet.create({
    view: {
        flex: 1,
        backgroundColor: colors.background,
        display: 'flex',
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: "center"
        
    },
    container: {
        flex: 1,
        backgroundColor: colors.background,
        paddingTop: 60,
        paddingHorizontal: 20,
    },
});