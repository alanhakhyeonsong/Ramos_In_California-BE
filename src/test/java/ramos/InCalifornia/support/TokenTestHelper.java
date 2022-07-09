package ramos.InCalifornia.support;

import ramos.InCalifornia.global.config.jwt.TokenDto;

public class TokenTestHelper {

    public static TokenDto givenToken() {
        return TokenDto.builder()
                .accessToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2NTczNjQ2NzQsImV4cCI6MTY1NzM2NjQ3NH0.9PAdXq6eN7yVSduQpFORnO4F58gBll773wj9iRLR2L2waM4G9eeGY23NA4f7Ida24MDPRwmyglmKHXtjHnY_pA")
                .refreshToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2NTczNjQ2NzQsImV4cCI6MTY1Nzk2OTQ3NH0.23swQOtD2WZXqoWTk_DtB0xlh-qYYzafhkqsaqwtR5pcBG1xUv5eUOP7uf0Tsh5Go3YIEAatD185cao1l2q6hQ")
                .build();
    }
}
