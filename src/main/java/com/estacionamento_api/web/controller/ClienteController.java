package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Cliente;
import com.estacionamento_api.jwt.JwtUserDetails;
import com.estacionamento_api.service.ClienteService;
import com.estacionamento_api.service.UsuarioService;
import com.estacionamento_api.web.dto.ClienteCreateDto;
import com.estacionamento_api.web.dto.ClienteResponseDto;
import com.estacionamento_api.web.dto.mapper.ClienteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clientes" ,description = "Contém todas as operações relativas  ao recurso de um Cliente")
@RequiredArgsConstructor //aplicar Injeção pelo construtor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    private final UsuarioService usuarioService;


    @Operation(summary = "Criar um novo cliente",description = "Recurso Para Criar um novo Cliente vinculando a um usuario cadastrado" +
            "Requisição exige o uso de um token , Acesso restrito a Role='CLIENTE'",
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClienteResponseDto.class ))),
                    @ApiResponse(responseCode = "409",description = "Cliente CPF ja cadastrado no Sistema",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class ))),
                    @ApiResponse(responseCode = "422",description = "Recurso não Processados por falta de dados ou dados Invalidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),
                    @ApiResponse(responseCode = "403",description = "Recurso não Permitido ao perfil de ADMIN",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create (@Valid @RequestBody ClienteCreateDto dto,
                                                      @AuthenticationPrincipal JwtUserDetails userDetails){

        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }
}
