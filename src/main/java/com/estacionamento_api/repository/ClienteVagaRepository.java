package com.estacionamento_api.repository;

import com.estacionamento_api.entity.ClienteVaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga,Long> {
}
