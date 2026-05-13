package animalDex.dex.service;

import animalDex.dex.exceptions.AIResponseException;
import animalDex.dex.exceptions.AnimalNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

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
    public String GetSpeciesName(byte[] file) throws IOException {
        String prompt = """
                Identify the animal in the image. I need the full species name
                Return ONLY valid JSON.
                Example:
                {
                    "scientificName": "Vulpes vulpes"
                }
                if you couldn't identify it or there is no animal, return:
                "none"
                """;

        //byte[] imageBytes = file.getBytes();

        String mimeType = "image/jpeg";

        GenerateContentResponse rawResponse =
                client.models.generateContent(
                        aiModel,
                        Content.fromParts(
                                Part.fromText(prompt),
                                Part.fromBytes(file, mimeType)
                        ),
                        config);
        if(rawResponse == null || rawResponse.text() == null || rawResponse.text().isBlank() || Objects.equals(rawResponse.text(), "none")){
            throw new AnimalNotFoundException("Animal não encontrado.", HttpStatus.EXPECTATION_FAILED);
        }
        String json = ExtractJSON(rawResponse.text());

        JsonNode node;
        try {
            node = mapper.readTree(json);
        } catch (Exception e) {
            throw new AIResponseException("JSON retornado pela IA é inválido: " + json, HttpStatus.NOT_ACCEPTABLE);
        }

        String scientificName = node.path("scientificName").asText();
        if (scientificName.isBlank()) {
            throw new AIResponseException("IA não retornou nome científico.", HttpStatus.NOT_ACCEPTABLE);
        }

        return scientificName;
    }

    public String GetSpeciesJSON(String scientificName) {

        if (scientificName == null || scientificName.isBlank()) {
            throw new IllegalArgumentException("Nome científico não pode ser vazio.");
        }

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

        GenerateContentResponse rawResponse;

        try {
            rawResponse = client.models.generateContent(
                    aiModel,
                    Content.fromParts(
                            Part.fromText(prompt),
                            Part.fromText("Scientific name: " + scientificName)
                    ),
                    config
            );
        } catch (Exception e) {
            throw new AIResponseException("Falha ao comunicar com a IA: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (rawResponse == null || rawResponse.text() == null || rawResponse.text().isBlank()) {
            throw new AIResponseException("Resposta nula da IA.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String cleanJson = ExtractJSON(rawResponse.text());

        try {
            mapper.readTree(cleanJson);
        } catch (Exception e) {
            throw new AIResponseException("JSON retornado pela IA é inválido: " + cleanJson, HttpStatus.BAD_GATEWAY);
        }

        return cleanJson;
    }

    private String ExtractJSON(String rawText) {
        if(rawText == null || rawText.isBlank()){
            throw new AIResponseException("Erro ao analisar resposta.", HttpStatus.NOT_ACCEPTABLE);
        }

        String raw = rawText.trim()
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("(?s)```\\s*", "")
                .trim();

        int start = raw.indexOf("{");
        int end = raw.lastIndexOf("}");

        if(start == -1 || end == -1 || end <= start){
            throw new AIResponseException("Resposta IA naão contem um JSON válido", HttpStatus.NOT_ACCEPTABLE);
        }

        return raw.substring(start, end + 1);
    }
}
