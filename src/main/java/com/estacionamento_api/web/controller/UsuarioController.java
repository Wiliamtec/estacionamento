package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Usuario;
import com.estacionamento_api.service.UsuarioService;
import com.estacionamento_api.web.dto.UsuarioCreateDto;
import com.estacionamento_api.web.dto.UsuarioResponseDto;
import com.estacionamento_api.web.dto.UsuarioSenhaDto;
import com.estacionamento_api.web.dto.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios" ,description = "Contém todas as operações relativas  aos recursos para cadastro , edição  e leitura de um usuário")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuService;

    @Operation(summary = "Criar um novo usuario",description = "Recurso Para Criar um novo Usuário",
            responses = {
                     @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                             content = @Content(mediaType = "application/json",schema = @Schema(implementation =UsuarioResponseDto.class ))),
                     @ApiResponse(responseCode = "409",description = "Usuário e-mail ja cadastrado no Sistema",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class ))),
                     @ApiResponse(responseCode = "422",description = "Recurso não Processados por dados de entrada Invalidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create (@Valid @RequestBody UsuarioCreateDto createDto){
     Usuario user = usuService.salvar(UsuarioMapper.toUsuario(createDto));
     return  ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));

    }


    @Operation(summary = "Recuperar um usuario pelo Id",description = "Requisição exige um Token Valido , Acesso Restrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =UsuarioResponseDto.class ))),
                    @ApiResponse(responseCode = "403",description = "Usuario Sem Permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),
                    @ApiResponse(responseCode = "404",description = "Recurso não Encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')OR (hasRole('CLIENTE')AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuService.buscarPorId(id);
        return  ResponseEntity.ok(UsuarioMapper.toDto(user));

    }


    @Operation(summary = "Atualizar senha ",description = "Requisição exige um Token Valido , Acesso Restrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Senha Atualizada com sucesso"),
                    @ApiResponse(responseCode = "400",description = "Senha não confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),
                    @ApiResponse(responseCode = "403",description = "Usuario Sem Permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),
                    @ApiResponse(responseCode = "422",description = "Campos invalidos ou mal formatados",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })
    //PatchMapping -> usado para alterações parciais
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto dto){
        usuService.editarSenha(id, dto.getSenhaAtual(),dto.getNovaSenha(),dto.getConfirmaSenha());
        return  ResponseEntity.noContent().build();

    }


    @Operation(summary = "Listar todos os usuarios cadastrados",description = "Requisição exige um Token Valido , Acesso Restrito a ADMIN|CLIENTE",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista todos os usuarios Cadastrados",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =UsuarioResponseDto.class )))),
                    @ApiResponse(responseCode = "403",description = "Usuario Sem Permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),

            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List <Usuario> users = usuService.buscarTodos();
        return  ResponseEntity.ok(UsuarioMapper.toListDto(users));

    }
}
