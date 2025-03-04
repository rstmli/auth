package az.security.auth.service;

import az.security.auth.dao.entity.UserEntity;
import az.security.auth.dao.repository.UserRepository;
import az.security.auth.dto.LoginResponse;
import az.security.auth.dto.UserRequestDto;
import az.security.auth.exception.InvalidUsernameException;
import az.security.auth.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username).orElseThrow(() ->
                new InvalidUsernameException(username));
        return new User(username, user.getPassword(), List.of());
    }

    public String register(UserRequestDto dto){
        if(repository.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException();
        }
        var hashedPassword = passwordEncoder.encode(dto.getPassword());

        return repository.save(
                UserEntity.builder()
                        .username(dto.getUsername())
                        .password(hashedPassword)
                        .build()).getUsername();

    }

    public LoginResponse login(UserRequestDto dto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(),
                dto.getPassword()));
        String token = jwtHelper.tokenGenerate(dto.getUsername());
        return new LoginResponse(token, null);
    }

}
