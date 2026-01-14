package com.estacionamento_api.service;

import com.estacionamento_api.entity.Usuario;
import com.estacionamento_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor //efetuado injeção de dependecia pelo construtor da classe
@Service
public class UsuarioService {

    //atenção verificar se o plugin do lombok esta instalado na ide , caso contrario dara erro de syntax ao usar final na variavel
    private final  UsuarioRepository usuRepo;


    @Transactional
    public Usuario salvar(Usuario usuario) {
       return usuRepo.save(usuario);
    }
}
