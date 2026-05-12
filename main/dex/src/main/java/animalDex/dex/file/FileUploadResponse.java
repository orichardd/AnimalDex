package animalDex.dex.file;

public record FileUploadResponse(
        String id,
        String name,
        String url,
        Long size
) {
}
