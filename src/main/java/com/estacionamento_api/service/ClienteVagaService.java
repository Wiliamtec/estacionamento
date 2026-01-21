package com.estacionamento_api.service;

import com.estacionamento_api.entity.ClienteVaga;
import com.estacionamento_api.exception.EntityNotFoundException;
import com.estacionamento_api.repository.ClienteVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVagaRepository vagaRepository;

    @Transactional
    public ClienteVaga salvar (ClienteVaga clienteVaga){
        return vagaRepository.save(clienteVaga);
    }

    @Transactional(readOnly = true)
    public ClienteVaga buscarPorRecibo(String recibo) {
        return vagaRepository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Recibo '%s' não encontrado no sistema ou check-out ja realizado",recibo))
        );
    }
}
