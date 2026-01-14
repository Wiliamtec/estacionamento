package com.estacionamento_api.service;

import com.estacionamento_api.entity.Usuario;
import com.estacionamento_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //efetuado injeção de dependecia pelo construtor da classe
@Service
public class UsuarioService {

    //atenção verificar se o plugin do lombok esta instalado na ide , caso contrario dara erro de syntax ao usar final na variavel
    private final  UsuarioRepository usuRepo;


    @Transactional
    public Usuario salvar(Usuario usuario) {
       return usuRepo.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuRepo.findById(id).orElseThrow(
                ()-> new RuntimeException("Usuario Não Encontrado"));
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("nova senha não confere com confirmação de senha");

        }

        Usuario user = buscarPorId(id); //utilizando metodo customizado criado para busca por id

        if(!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("sua senha não confere");
        }
        user.setPassword(novaSenha);
        return user;
    }
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuRepo.findAll();
    }
}
