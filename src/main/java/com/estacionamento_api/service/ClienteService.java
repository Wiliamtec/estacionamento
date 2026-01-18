package com.estacionamento_api.service;

import com.estacionamento_api.entity.Cliente;
import com.estacionamento_api.exception.CpfUniqueViolationException;
import com.estacionamento_api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar (Cliente cliente){
        try{
            return clienteRepository.save(cliente);
        }catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(
                    String.format("CPF '%s' não pode ser cadastrado , ja existe no sistema",cliente.getCpf())
            );

        }
    }

}
