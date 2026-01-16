package com.estacionamento_api.service;

import com.estacionamento_api.entity.Usuario;
import com.estacionamento_api.exception.EntityNotFoundException;
import com.estacionamento_api.exception.PasswordInvalidException;
import com.estacionamento_api.exception.UserNameUniqueViolationException;
import com.estacionamento_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //efetuado injeção de dependecia pelo construtor da classe
@Service
public class UsuarioService {

    //atenção verificar se o plugin do lombok esta instalado na ide , caso contrario dara erro de syntax ao usar final na variavel
    private final  UsuarioRepository usuRepo;
    private final PasswordEncoder encoder;


    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            return usuRepo.save(usuario);
        }catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UserNameUniqueViolationException(String.format("Username {%s} ja cadastrado",usuario.getUsername()));

        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuRepo.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario id = %s não encontrado",id)));
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha");

        }

        Usuario user = buscarPorId(id); //utilizando metodo customizado criado para busca por id

        if(!encoder.matches(senhaAtual,user.getPassword())){
            throw new PasswordInvalidException("sua senha não confere");
        }
        user.setPassword(encoder.encode(novaSenha));
        return user;
    }
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuRepo.findByUsername(username).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario id com username: {%s} não encontrado",username)));
    };
    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuRepo.findRoleByUserName(username);
    }
}

