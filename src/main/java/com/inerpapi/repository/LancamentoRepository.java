package com.inerpapi.repository;

import java.time.LocalDate;
import java.util.List;

import com.inerpapi.model.Lancamento;
import com.inerpapi.repository.lancamento.LancamentoRepositoryQuery;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
 
    List<Lancamento> findByDescricaoContainingOrDataVencimentoBetween(String descricao, LocalDate dataVencimentoDe, LocalDate dataVencimentoAte);

    List<Lancamento> findByDescricaoContaining(String descricao);

}