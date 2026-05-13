package animalDex.dex.service;

import org.springframework.beans.factory.annotation.Value;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;
    private final String aiModel = "gemini-2.5-flash-lite";
    private final Client client;
    private final ObjectMapper mapper;

    GenerateContentConfig config = GenerateContentConfig.builder()
            .temperature(0.0f)
            .topP(0.95f)
            .maxOutputTokens(200)
            .build();

    public GeminiService(ObjectMapper mapper, @Value("${gemini.api.key}") String apiKey) {
        this.mapper = mapper;
        this.client = Client.builder().apiKey(apiKey).build();
    }
    public String GetSpeciesName(MultipartFile file) throws IOException {
        String prompt = """
                Identify the animal in the image. I need the full species name
                Return ONLY valid JSON.
                Example:
                {
                    "scientificName": "Vulpes vulpes"
                }
                if you couldn't identify it or there is no animal, return:
                {
                    "scientificName": "none"
                }
                """;

        byte[] imageBytes = file.getBytes();

        String mimeType = file.getContentType();

        GenerateContentResponse rawResponse =
                client.models.generateContent(
                        aiModel,
                        Content.fromParts(
                                Part.fromText(prompt),
                                Part.fromBytes(imageBytes, mimeType)
                        ),
                        config);
        if(rawResponse == null || rawResponse.text() == null || rawResponse.text().isBlank()){
            return null;
        }
        String json = ExtractJSON(rawResponse.text());
        String scientificName = mapper.readTree(json).path("scientificName").asText();
        return scientificName;
    }

    public String GetSpeciesJSON(String scientificName) {

        String prompt = """
        You are an API that ONLY returns valid raw JSON.

        Create a JSON object about the animal using the provided scientific name.

        Rules:
        - Return ONLY JSON
        - No markdown
        - No explanations
        - No ```json
        - No extra text
        - Use concise information
        - If information is uncertain, make a reasonable estimate

        Allowed rarities:
        COMMON
        RARE
        EPIC
        LEGENDARY
        CHROMATIC
        EXTINCT

        Rarity examples:
        COMMON -> cat, dog, pigeon
        RARE -> raccoon, fox
        EPIC -> deer, mantis
        LEGENDARY -> lion, bear, tiger
        CHROMATIC -> melanistic jaguar, albino tiger
        EXTINCT -> dodo, tyrannosaurus rex

        JSON format:
        {
          "scientificName": "Vulpes vulpes",
          "commonName": "Red Fox",
          "weight": "5-10 kg",
          "size": "70-90 cm",
          "diet": "Small mammals, birds, insects, fruits",
          "rarity": "RARE"
        }
        """;

        GenerateContentResponse rawResponse =
                client.models.generateContent(
                        aiModel,
                        Content.fromParts(
                                Part.fromText(prompt),
                                Part.fromText("Scientific name: " + scientificName)
                        ),
                        config
                );

        String cleanJson = ExtractJSON(rawResponse.text());
        return cleanJson;
    }

    private String ExtractJSON(String rawText) {
        String raw = rawText.trim();

        raw = raw
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```\\s*", "")
                .trim();

        int start = raw.indexOf("{");
        int end = raw.lastIndexOf("}");

        if(start != -1 && end != -1 && end > start){
            return raw.substring(start, end + 1);
        }

        return raw;
    }
}
