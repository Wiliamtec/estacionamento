package com.estacionamento_api.web.controller;

import com.estacionamento_api.entity.Vaga;
import com.estacionamento_api.service.VagaService;
import com.estacionamento_api.web.dto.VagaCreateDto;
import com.estacionamento_api.web.dto.VagaResponseDto;
import com.estacionamento_api.web.dto.mapper.VagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void>create(@RequestBody @Valid VagaCreateDto dto){
        Vaga vaga = VagaMapper.toVaga(dto);
        vagaService.salvar(vaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(vaga.getCodigo())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto>getByCodigo(@PathVariable String codigo){
        Vaga vaga = vagaService.buscarPorcodigo(codigo);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
