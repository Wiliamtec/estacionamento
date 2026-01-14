package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Usuario;
import com.estacionamento_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
