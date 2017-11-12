package git.info.dto;

import lombok.Data;

@Data
public class AccessTokenDto {
    String access_token;
    String scope;
    String token_type;
}
