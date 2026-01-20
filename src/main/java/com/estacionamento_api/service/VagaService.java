package com.estacionamento_api.service;

import com.estacionamento_api.entity.Vaga;
import com.estacionamento_api.exception.EntityNotFoundException;
import com.estacionamento_api.exception.codigoUniqueViolationException;
import com.estacionamento_api.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.estacionamento_api.entity.Vaga.StatusVaga.LIVRE;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga){

        try{
           return vagaRepository.save(vaga);
        }catch (DataIntegrityViolationException ex){
            throw new codigoUniqueViolationException(String.format("Vaga com código '%s' ja cadastrada",vaga.getCodigo()));

        }

    }

    @Transactional(readOnly = true)
    public Vaga buscarPorcodigo(String codigo){
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Vaga com código '%s' não foi encontrada",codigo))
        );
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Nenhuma Vaga Livre foi encontrada"))
        );
    }
}
