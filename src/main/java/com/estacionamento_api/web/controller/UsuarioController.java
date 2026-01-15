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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @Operation(summary = "Recuperar um usuario pelo Id",description = "Recuperar um usuario pelo Id",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =UsuarioResponseDto.class ))),
                    @ApiResponse(responseCode = "404",description = "Recurso não Encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuService.buscarPorId(id);
        return  ResponseEntity.ok(UsuarioMapper.toDto(user));

    }


    @Operation(summary = "Atualizar senha ",description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204",description = "Senha Atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =Void.class ))),
                    @ApiResponse(responseCode = "404",description = "Senha não confere",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),
                    @ApiResponse(responseCode = "400",description = "Recurso não Encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class ))),
                    @ApiResponse(responseCode = "422",description = "Campos invalidos ou mal formatados",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation =ErrorMessage.class )))
            })
    //PatchMapping -> usado para alterações parciais
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDto dto){
        Usuario user = usuService.editarSenha(id, dto.getSenhaAtual(),dto.getNovaSenha(),dto.getConfirmaSenha());
        return  ResponseEntity.noContent().build();

    }


    @Operation(summary = "Listar todos os usuarios",description = "Listar todos os usuarios",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista todos os usuarios Cadastrados",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation =UsuarioResponseDto.class ))))

            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List <Usuario> users = usuService.buscarTodos();
        return  ResponseEntity.ok(UsuarioMapper.toListDto(users));

    }
}
