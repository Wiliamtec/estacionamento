package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Usuario;
import com.estacionamento_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuService;

    @PostMapping
    public ResponseEntity<Usuario> create (@RequestBody Usuario usuario){
     Usuario user = usuService.salvar(usuario);
     return  ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        Usuario user = usuService.buscarPorId(id);
        return  ResponseEntity.ok(user);

    }

    //PatchMapping -> usado para alterações parciais
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id,@RequestBody Usuario usuario){
        Usuario user = usuService.editarSenha(id,usuario.getPassword());
        return  ResponseEntity.ok(user);

    }


    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List <Usuario> users = usuService.buscarTodos();
        return  ResponseEntity.ok(users);

    }
}
