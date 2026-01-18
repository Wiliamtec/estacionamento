package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Cliente;
import com.estacionamento_api.jwt.JwtUserDetails;
import com.estacionamento_api.service.ClienteService;
import com.estacionamento_api.service.UsuarioService;
import com.estacionamento_api.web.dto.ClienteCraeteDto;
import com.estacionamento_api.web.dto.ClienteResponseDto;
import com.estacionamento_api.web.dto.mapper.ClienteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor //aplicar Injeção pelo construtor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create (@Valid @RequestBody ClienteCraeteDto dto,
                                                      @AuthenticationPrincipal JwtUserDetails userDetails){

        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }
}
