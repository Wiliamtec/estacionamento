package com.estacionamento_api.service;

import com.estacionamento_api.entity.ClienteVaga;
import com.estacionamento_api.exception.EntityNotFoundException;
import com.estacionamento_api.repository.ClienteVagaRepository;
import com.estacionamento_api.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return vagaRepository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return vagaRepository.findAllByClienteCpf(cpf,pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
        return vagaRepository.findAllByClienteUsuarioId(id,pageable);
    }
}
