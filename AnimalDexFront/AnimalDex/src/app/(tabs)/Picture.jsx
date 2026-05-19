import { View, TouchableOpacity, StyleSheet, StatusBar } from "react-native";
import { useState, useRef, useEffect } from "react";
import { CameraView, useCameraPermissions } from "expo-camera";
import { Ionicons } from "@expo/vector-icons";
import * as ImagePicker from "expo-image-picker";
import api from "../../services/api";
import { useIsFocused } from "@react-navigation/native"; //detectar se a tela está ativa para evitar erros de câmera quando não estiver




export default function Picture({ navigation }) {
  const isFocused = useIsFocused(); 
  const [facing, setFacing] = useState("back");
  const [flash, setFlash] = useState("off");
  const [permission, requestPermission] = useCameraPermissions();
  const cameraRef = useRef(null);

  useEffect(() => {
    if (permission && !permission.granted) {
      requestPermission();
    }
  }, [permission]);

  const takePicture = async () => {
    if (cameraRef.current) {
        const photo = await cameraRef.current.takePictureAsync({
            quality: 0.8,
            exif: false,
        });

        const formData = new FormData();
        formData.append("file", {
            uri: photo.uri,
            type: "image/jpeg",
            name: "photo.jpeg",
        });

        try {
            const response = await api.post("/file", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            });
            console.log("Response:", response.data);
        } catch (e) {
    console.log("Error message:", e.message);
    console.log("Error status:", e.response?.status);
    console.log("Error data:", e.response?.data);
    console.log("Error URL:", e.config?.url);
}
    }
};

  const pickFromGallery = async () => {
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      quality: 1,
    });
    if (!result.canceled) {
      console.log("Gallery image:", result.assets[0].uri);
    }
  };

  const toggleFlash = () => {
    setFlash((prev) => (prev === "off" ? "on" : "off"));
  };

  if (!permission) return <View style={styles.container} />;

  return (
    
    <View style={styles.container}>
      <StatusBar barStyle="light-content" />

      {isFocused && permission.granted ? (
        <CameraView
          ref={cameraRef}
          style={StyleSheet.absoluteFill}
          facing={facing}
          flash={flash}
        />
      ) : (
        <View style={[StyleSheet.absoluteFill, styles.noPermission]} />
      )}

      {/* Top controls */}
      <View style={styles.topBar}>
        <TouchableOpacity
          style={styles.iconBtn}
          onPress={() => navigation?.goBack()}
        >
          <Ionicons name="arrow-back" size={22} color="#fff" />
        </TouchableOpacity>

        <TouchableOpacity style={styles.flashBtn} onPress={toggleFlash}>
          <Ionicons
            name={flash === "on" ? "flash" : "flash-off"}
            size={20}
            color={flash === "on" ? "#fff" : "#aaa"}
          />
        </TouchableOpacity>
      </View>

      {/* Scan frame overlay */}
      <View style={styles.frameWrapper} pointerEvents="none">
        <View style={styles.frame}>
          {/* Corner brackets */}
          <View style={[styles.corner, styles.topLeft]} />
          <View style={[styles.corner, styles.topRight]} />
          <View style={[styles.corner, styles.bottomLeft]} />
          <View style={[styles.corner, styles.bottomRight]} />
        </View>
      </View>

      {/* Bottom controls */}
      <View style={styles.bottomBar}>
        {/* Gallery picker */}
        <TouchableOpacity style={styles.galleryBtn} onPress={pickFromGallery}>
          <Ionicons name="image-outline" size={28} color="#fff" />
        </TouchableOpacity>

        {/* Shutter button */}
        <TouchableOpacity style={styles.shutter} onPress={takePicture}>
          <View style={styles.shutterInner} />
        </TouchableOpacity>

        {/* Spacer to balance layout */}
        <View style={{ width: 52 }} />
      </View>
    </View>
  );
}

const CORNER_SIZE = 24;
const CORNER_THICKNESS = 3;
const CORNER_COLOR = "#fff";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#000",
  },
  noPermission: {
    backgroundColor: "#1a1a1a",
  },

  // Top bar
  topBar: {
    position: "absolute",
    top: 48,
    left: 0,
    right: 0,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    paddingHorizontal: 20,
    zIndex: 10,
  },
  iconBtn: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: "rgba(0,0,0,0.4)",
    justifyContent: "center",
    alignItems: "center",
  },
  flashBtn: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: "rgba(60,120,60,0.85)",
    justifyContent: "center",
    alignItems: "center",
  },

  // Scan frame
  frameWrapper: {
    ...StyleSheet.absoluteFillObject,
    justifyContent: "center",
    alignItems: "center",
    zIndex: 5,
  },
  frame: {
    width: 240,
    height: 280,
    position: "relative",
  },
  corner: {
    position: "absolute",
    width: CORNER_SIZE,
    height: CORNER_SIZE,
    borderColor: CORNER_COLOR,
  },
  topLeft: {
    top: 0,
    left: 0,
    borderTopWidth: CORNER_THICKNESS,
    borderLeftWidth: CORNER_THICKNESS,
    borderTopLeftRadius: 4,
  },
  topRight: {
    top: 0,
    right: 0,
    borderTopWidth: CORNER_THICKNESS,
    borderRightWidth: CORNER_THICKNESS,
    borderTopRightRadius: 4,
  },
  bottomLeft: {
    bottom: 0,
    left: 0,
    borderBottomWidth: CORNER_THICKNESS,
    borderLeftWidth: CORNER_THICKNESS,
    borderBottomLeftRadius: 4,
  },
  bottomRight: {
    bottom: 0,
    right: 0,
    borderBottomWidth: CORNER_THICKNESS,
    borderRightWidth: CORNER_THICKNESS,
    borderBottomRightRadius: 4,
  },

  // Bottom controls
  bottomBar: {
    position: "absolute",
    bottom: 130,
    left: 0,
    right: 0,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    paddingHorizontal: 40,
    gap: 40,
    zIndex: 10,
  },
  galleryBtn: {
    width: 52,
    height: 52,
    borderRadius: 12,
    backgroundColor: "rgba(0,0,0,0.4)",
    justifyContent: "center",
    alignItems: "center",
  },
  shutter: {
    width: 72,
    height: 72,
    borderRadius: 36,
    borderWidth: 4,
    borderColor: "#4caf50",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0,0,0,0.2)",
  },
  shutterInner: {
    width: 54,
    height: 54,
    borderRadius: 27,
    backgroundColor: "#fff",
  },
});