package dev.zaeem.productservice.security;

import dev.zaeem.productservice.dtos.ValidateTokenDto;
import dev.zaeem.productservice.security.dtos.ValidateRequestDto;
import dev.zaeem.productservice.security.models.JwtObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import dev.zaeem.productservice.dtos.SessionStatus;

import java.util.Optional;

@Service
public class TokenValidator {
    private RestTemplateBuilder restTemplateBuilder;

    public TokenValidator(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }
//    public Optional<JwtObject> validateToken(String token, String id){
    public JwtObject validateToken(String token){
        ValidateRequestDto requestDto = new ValidateRequestDto();
        requestDto.setToken(token);
        String requestUrl = "http://localhost:9000/auth/validate";
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<JwtObject> response = restTemplate.postForEntity(requestUrl, requestDto, JwtObject.class);
        JwtObject jwtObject = response.getBody();
        return jwtObject;
    }
}