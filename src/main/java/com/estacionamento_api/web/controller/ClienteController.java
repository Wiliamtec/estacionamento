package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Cliente;
import com.estacionamento_api.jwt.JwtUserDetails;
import com.estacionamento_api.repository.projection.ClienteProjection;
import com.estacionamento_api.service.ClienteService;
import com.estacionamento_api.service.UsuarioService;
import com.estacionamento_api.web.dto.ClienteCreateDto;
import com.estacionamento_api.web.dto.ClienteResponseDto;
import com.estacionamento_api.web.dto.PageableDto;
import com.estacionamento_api.web.dto.mapper.ClienteMapper;
import com.estacionamento_api.web.dto.mapper.PageableMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Clientes" ,description = "Contém todas as operações relativas  ao recurso de um Cliente")
@RequiredArgsConstructor //aplicar Injeção pelo construtor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    private final UsuarioService usuarioService;


    @Operation(summary = "Criar um novo cliente",description = "Recurso Para Criar um novo Cliente vinculando a um usuario cadastrado" +
            "Requisição exige o uso de um token , Acesso restrito a Role='CLIENTE'",
            security = @SecurityRequirement(name = "security"),
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


    @Operation(summary = "Localizar um cliente",description = "Recurso Para Localizar um novo Cliente pelo ID ." +
            "Requisição exige o uso de um token , Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ClienteResponseDto.class ))),
                    @ApiResponse(responseCode = "404",description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class ))),
                    @ApiResponse(responseCode = "403",description = "Recurso não Permitido ao perfil de CLIENTE",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id){
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));

    }

    @Operation(summary = "Recuperar lista de clientes",

            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN' ",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)@PageableDefault(size = 5,sort = {"nome"}) Pageable pegeable){
        Page<ClienteProjection> clientes = clienteService.buscarTodos(pegeable);
        return ResponseEntity.ok(PageableMapper.toDto(clientes));

    }

    @Operation(summary = "Recuperar dados do cliente autenticado",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClienteResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de ADMIN",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })

    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails){
       Cliente cliente = clienteService.buscarPorUsuarioId(userDetails.getId());
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));

    }
}

