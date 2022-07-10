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
import ramos.InCalifornia.domain.member.exception.MemberNotFoundException;
import ramos.InCalifornia.domain.member.repository.MemberRepository;
import ramos.InCalifornia.global.config.jwt.JwtTokenProvider;
import ramos.InCalifornia.global.config.jwt.TokenDto;
import ramos.InCalifornia.global.config.redis.RedisService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RedisService redisService;

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
        Member findMember = memberRepository.findByEmail(dto.getEmail()).orElseThrow(MemberNotFoundException::new);
        validatePassword(findMember, dto.getPassword());

        String accessToken = tokenProvider.createAccessToken(dto.getEmail(), findMember.getRole().toString());
        String refreshToken = tokenProvider.createRefreshToken(dto.getEmail(), findMember.getRole().toString());

        redisService.setValues(dto.getEmail(), refreshToken);
        return new TokenDto(accessToken, refreshToken);
    }

    public void logout(String refreshToken) {
        String email = tokenProvider.extractEmail(refreshToken);
        redisService.deleteValues(email);
    }

    public TokenDto reissue(String role, ReissueRequest dto) {
        String email = tokenProvider.extractEmail(dto.getAccessToken());
        if (!tokenProvider.isValidToken(dto.getRefreshToken())) { // redis 연동 시 수정해야 함
            throw new TokenExpiredException();
        }
        String accessToken = tokenProvider.createAccessToken(email, role);
        String refreshToken = tokenProvider.createRefreshToken(email, role);

        redisService.deleteValues(email);
        redisService.setValues(email, refreshToken);
        return new TokenDto(accessToken, refreshToken);
    }

    private void validatePassword(Member findMember, String password) {
        if (!bCryptPasswordEncoder.matches(password, findMember.getPassword())) {
            throw new IdPasswordMismatchException();
        }
    }
}
