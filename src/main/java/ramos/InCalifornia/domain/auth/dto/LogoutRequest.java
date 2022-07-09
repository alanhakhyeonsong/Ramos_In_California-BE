package ramos.InCalifornia.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LogoutRequest {

    @NotBlank
    private String refreshToken;
}
