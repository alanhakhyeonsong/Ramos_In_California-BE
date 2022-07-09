package ramos.InCalifornia.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReissueRequest {

    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;
}
