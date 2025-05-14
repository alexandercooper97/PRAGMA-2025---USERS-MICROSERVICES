package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserRequestDTO;
import com.pragma.powerup.application.dto.response.UserResponseDTO;
import com.pragma.powerup.application.dto.response.UserSimpleResponseDto;
import com.pragma.powerup.application.handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    /*
    ############################################################################################################
    ##
    ##                          OBTENER TODOS LOS USUARIOS
    ##
    ############################################################################################################
     */
    private final UserHandler userHandler;
    @Operation(summary = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> userResponseDTOList = userHandler.getAllOwners();
        return ResponseEntity.ok(userResponseDTOList);
    }

    /*
    #######################################################################################################
    ##
    ##                       OBTENER USUARIO BY ID
    ##
    #######################################################################################################
     */
    @Operation(summary = "Get User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserSimpleResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/{idUser}/")
    public ResponseEntity<UserSimpleResponseDto> getUser(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(userHandler.getUser(idUser));
    }


    /*
    ##########################################################################################################
    ##
    ##                               CREAR PROPIETARIO - HU 01
    ##
    ##########################################################################################################
     */
    @Operation(summary = "Create Owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserSimpleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content)
    })

    public ResponseEntity<Void> saveOwner(@RequestHeader("Authorization") String authorization,
                                          @RequestBody @Valid UserRequestDTO userRequestDTO) {

        userHandler.saveOwner(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
    @PostMapping("/owner/")
    public ResponseEntity<Void> saveOwner(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userHandler.saveOwner(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/



    /*
    #########################################################################################################
    ##
    ##                               CREAR  EMPLEADO  --  HU 06
    ##
    #########################################################################################################
     */

    @Operation(summary = "Create Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserSimpleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content)
    })
    @PostMapping("/employee/")
    public ResponseEntity<UserSimpleResponseDto> saveEmployee(@RequestHeader("Authorization") String authorization,
                                          @RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserSimpleResponseDto userSimpleResponseDto = userHandler.saveEmployee(userRequestDTO);
        return new ResponseEntity<>(userSimpleResponseDto, HttpStatus.CREATED);
    }

    /*
    ############################################################################################################
    ##
    ##                        CREAR CLIENTE -- HU 08
    ##
    #############################################################################################################
     */
    @Operation(summary = "Create Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create Client",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "User already exists", content = @Content)
    })
    @PostMapping("/client/")
    public ResponseEntity<Void> saveClient(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userHandler.saveClient(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
