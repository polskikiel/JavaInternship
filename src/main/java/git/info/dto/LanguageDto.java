package git.info.dto;

import lombok.Data;

@Data
public class LanguageDto {
    String name;
    Integer quantity;

    public LanguageDto(String name) {
        this.name = name;
    }
}
