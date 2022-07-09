package ramos.InCalifornia.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReissueRequest {

    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;
}
