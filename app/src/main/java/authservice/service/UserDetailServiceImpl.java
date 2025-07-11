package authservice.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import authservice.model.UserInfoDto;
import authservice.entities.UserInfo;
import authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
public class UserDetailServiceImpl  implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserDetail(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto){
        // TODO: function to check valid email and password
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull( checkIfUserAlreadyExist(userInfoDto)))
            return false;
        String userId= UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
        return true;
    }

}


















