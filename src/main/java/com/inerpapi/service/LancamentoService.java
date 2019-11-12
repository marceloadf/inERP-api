package com.inerpapi.service;

import java.util.List;
import java.util.Optional;

import com.inerpapi.model.Lancamento;
import com.inerpapi.model.Pessoa;
import com.inerpapi.repository.LancamentoRepository;
import com.inerpapi.repository.PessoaRepository;
import com.inerpapi.repository.filter.LancamentoFilter;
import com.inerpapi.service.exception.PessoaInexistenteOuInativaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento createLancamento(Lancamento lancamento) {

        Optional<Pessoa> pessoaFromDb = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

        if (!pessoaFromDb.isPresent() || pessoaFromDb.get().isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }

        return lancamentoRepository.save(lancamento);
    }

    public List<Lancamento> getLancamentos(LancamentoFilter lancamentoFilter) {

        
        return null;

        // if (lancamentoFilter.getDataVencimentoAte() == null || lancamentoFilter.getDataVencimentoDe() == null)
        //     return lancamentoRepository.findByDescricaoContaining(lancamentoFilter.getDescricao());
        
        // if((lancamentoFilter.getDataVencimentoAte() != null || lancamentoFilter.getDataVencimentoDe() != null) && !StringUtils.isNotEmpty(lancamentoFilter.getDescricao()) )
        //     return 
        
        // return lancamentoRepository.findByDescricaoContainingOrDataVencimentoBetween(
        //     lancamentoFilter.getDescricao(), 
        //     lancamentoFilter.getDataVencimentoDe(), 
        //     lancamentoFilter.getDataVencimentoAte());
    }
}