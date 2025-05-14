package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserAuthRequestDto;
import com.pragma.powerup.application.dto.response.TokenResponseDto;
import com.pragma.powerup.application.handler.AuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {
    private final AuthHandler authHandler;

    @Operation(summary = "Auth User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authentication",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error during authentication", content = @Content)
    })
    @PostMapping("/login/")
    public ResponseEntity<TokenResponseDto> authUser(@Valid @RequestBody UserAuthRequestDto userAuthRequestDto) {
        return ResponseEntity.ok(authHandler.authUser(userAuthRequestDto));
    }

    @Operation(summary = "Validate Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token Valid",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Token", content = @Content)
    })
    @PostMapping("/validate")
    public ResponseEntity<TokenResponseDto> validateToken(@Valid @RequestParam String token) {
        return ResponseEntity.ok(authHandler.validateToken(token));
    }
}
