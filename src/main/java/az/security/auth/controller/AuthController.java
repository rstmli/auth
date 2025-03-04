package az.security.auth.controller;

import az.security.auth.dto.LoginResponse;
import az.security.auth.dto.UserRequestDto;
import az.security.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    @PostMapping("/register")
    public String getRegister(@RequestBody UserRequestDto dto){
        return userDetailsService.register(dto);
    }
    @PostMapping("/login")
    public LoginResponse getLogin(@RequestBody UserRequestDto dto){
        return userDetailsService.login(dto);
    }

}
