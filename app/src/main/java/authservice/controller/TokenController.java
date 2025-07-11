package authservice.controller;

import authservice.entities.RefreshToken;
import authservice.request.AuthRequestDto;
import authservice.request.RefreshTokenRequestDto;
import authservice.response.JwtResponseDto;
import authservice.service.JwtService;
import authservice.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken= refreshTokenService.createRefreshToken(authRequestDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder()
                    .accessToken(jwtService.generateToken(authRequestDto.getUsername()))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Exception in UserService", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return refreshTokenService.findByToken(refreshTokenRequestDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken= jwtService.generateToken(userInfo.getUsername());
                    return JwtResponseDto.builder().accessToken(accessToken).token(refreshTokenRequestDto.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token not in DB") );
    }
}

























