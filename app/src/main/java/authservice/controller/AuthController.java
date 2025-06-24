package authservice.controller;

import lombok.AllArgsConstructor;
import authservice.entities.RefreshToken;
import authservice.model.UserInfoDto;
import authservice.response.JwtResponseDto;
import authservice.service.JwtService;
import authservice.service.RefreshTokenService;
import authservice.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @PostMapping("auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto){
        try{
            Boolean isSignedUped = userDetailServiceImpl.signUpUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUped))
                return new ResponseEntity<>("User Already Exist", HttpStatus.BAD_REQUEST);
            RefreshToken refreshToken= refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken= jwtService.generateToken(userInfoDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder().accessToken(jwtToken).token(refreshToken.getToken()).build(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Exception in UserService", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
