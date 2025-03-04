package az.security.auth.controller;

import az.security.auth.util.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
@RequiredArgsConstructor
public class TestController {
    private final JwtHelper jwtHelper;




    @GetMapping
    public String isItWork(){
        return "it is working";
    }

    @GetMapping("/encode")
    public String encodeToken(@RequestParam("token") String token){
        return jwtHelper.tokenGenerate(token);
    }

    @GetMapping("/decode")
    public String decodeToken(@RequestParam("token") String token){
        return jwtHelper.tokenByDeCoder(token);
    }
}
