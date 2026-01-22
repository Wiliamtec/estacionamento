package com.estacionamento_api.repository;

import com.estacionamento_api.entity.ClienteVaga;
import com.estacionamento_api.repository.projection.ClienteVagaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga,Long> {
    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    @Query("""
  select new com.estacionamento_api.repository.projection.ClienteVagaProjection(
  c.placa,
  c.marca,
  c.modelo,
  c.cor,
  c.cliente.cpf,
  c.recibo,
  c.dataEntrada,
  c.dataSaida,
  c.vaga.codigo,
  c.valor,
  c.desconto
  )
  from ClienteVaga c
  where c.cliente.cpf like :cpf
  """)

    Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);


    @Query("""
  select new com.estacionamento_api.repository.projection.ClienteVagaProjection(
  c.placa,
  c.marca,
  c.modelo,
  c.cor,
  c.cliente.cpf,
  c.recibo,
  c.dataEntrada,
  c.dataSaida,
  c.vaga.codigo,
  c.valor,
  c.desconto
  )
  from ClienteVaga c
  where c.cliente.usuario.id = :id
  """)
    Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);


}
