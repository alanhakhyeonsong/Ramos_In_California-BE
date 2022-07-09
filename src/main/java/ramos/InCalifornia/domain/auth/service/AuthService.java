package ramos.InCalifornia.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ramos.InCalifornia.domain.auth.dto.LoginRequest;
import ramos.InCalifornia.domain.auth.dto.MemberResponse;
import ramos.InCalifornia.domain.auth.dto.ReissueRequest;
import ramos.InCalifornia.domain.auth.dto.SignUpRequest;
import ramos.InCalifornia.domain.auth.exception.IdPasswordMismatchException;
import ramos.InCalifornia.domain.auth.exception.MemberAlreadyExistException;
import ramos.InCalifornia.domain.auth.exception.TokenExpiredException;
import ramos.InCalifornia.domain.member.entity.Member;
import ramos.InCalifornia.domain.member.entity.Role;
import ramos.InCalifornia.domain.member.repository.MemberRepository;
import ramos.InCalifornia.global.config.jwt.JwtTokenProvider;
import ramos.InCalifornia.global.config.jwt.TokenDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public MemberResponse signUp(SignUpRequest dto) {
        checkAlreadyExists(dto.getEmail());

        Member member = dto.toEntity();
        String encryptedPassword = bCryptPasswordEncoder.encode(member.getPassword());
        member.setEncryptedPassword(encryptedPassword);
        Member save = memberRepository.save(member);

        return MemberResponse.of(save);
    }

    private void checkAlreadyExists(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberAlreadyExistException();
        }
    }

    public TokenDto login(LoginRequest dto) {
        Member findMember = memberRepository.findByEmail(dto.getEmail()).orElseThrow();
        validatePassword(findMember, dto.getPassword());

        String accessToken = tokenProvider.createAccessToken(dto.getEmail(), Role.ROLE_USER.toString());
        String refreshToken = tokenProvider.createRefreshToken(dto.getEmail(), Role.ROLE_USER.toString());
        return new TokenDto(accessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        System.out.println("refreshToken = " + refreshToken);
    }

    public TokenDto reissue(ReissueRequest dto) {
        String email = tokenProvider.extractEmail(dto.getAccessToken());
        if (!tokenProvider.isValidToken(dto.getRefreshToken())) { // redis 연동 시 수정해야 함
            throw new TokenExpiredException();
        }
        String accessToken = tokenProvider.createAccessToken(email, Role.ROLE_USER.toString());
        return new TokenDto(accessToken, dto.getRefreshToken());
    }

    private void validatePassword(Member findMember, String password) {
        if (!bCryptPasswordEncoder.matches(password, findMember.getPassword())) {
            throw new IdPasswordMismatchException();
        }
    }
}
