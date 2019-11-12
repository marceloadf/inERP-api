package com.inerpapi.service;

import com.inerpapi.constants.ConstantsInErp;
import com.inerpapi.model.Pessoa;
import com.inerpapi.repository.PessoaRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PessoaService{

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MessageSource messageSource;

    public Pessoa updatePessoa(Long id, Pessoa pessoa){
        
        Pessoa pessoaFromDb = searchPessoaByCodigo(id);

        BeanUtils.copyProperties(pessoa, pessoaFromDb, "codigo");
        return pessoaRepository.save(pessoaFromDb);
    }

    public void updateAtivoProperty(Long id, Boolean ativo){
        Pessoa pessoaFromDb = searchPessoaByCodigo(id);
        pessoaFromDb.setAtivo(ativo);
        pessoaRepository.save(pessoaFromDb);
    }

    private Pessoa searchPessoaByCodigo(Long id) {
        Pessoa pessoaFromDb = pessoaRepository
            .findById(id)
            .orElseThrow(() -> 
                new EmptyResultDataAccessException(
                    messageSource
                    .getMessage(ConstantsInErp.MENSAGEM_RECURSO_EXPERADO, new Object[]{"1", "0"}, LocaleContextHolder.getLocale()), 1));
        return pessoaFromDb;
    }
}