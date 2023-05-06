package com.woo.forjob.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woo.forjob.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity login(HttpServletRequest request, @RequestParam String code) throws JsonProcessingException {
        authService.login(request, code);

        return ResponseEntity.ok("ok");
    }
}
