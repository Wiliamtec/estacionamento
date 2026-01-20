package com.estacionamento_api.repository;

import com.estacionamento_api.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VagaRepository extends JpaRepository<Vaga,Long> {
}
