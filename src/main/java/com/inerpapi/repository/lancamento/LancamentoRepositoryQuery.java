package com.inerpapi.repository.lancamento;

import com.inerpapi.model.Lancamento;
import com.inerpapi.repository.filter.LancamentoFilter;
import com.inerpapi.repository.projection.ResumoLancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery{

    public Page<Lancamento> buscarLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable);

    public Page<ResumoLancamento> buscarResumoLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable);
}