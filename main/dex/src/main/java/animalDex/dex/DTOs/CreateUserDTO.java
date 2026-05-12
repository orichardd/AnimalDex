package animalDex.dex.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank
        @Size(min = 3, max = 30)
        String username,
        @NotBlank
        @Size(min = 8, max = 30)
        String password,
        @NotNull
        @Min(0)
        Integer picNum
) {
}
