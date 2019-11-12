package com.inerpapi.service;

import com.inerpapi.constants.ConstantsInErp;
import com.inerpapi.model.Categoria;
import com.inerpapi.repository.CategoriaRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService{

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MessageSource messageSource;

    public Categoria categoriaUpdate(Long id, Categoria categoria){
        Categoria categoriaFromDb = categoriaRepository.findById(id).orElseThrow(() ->
            new EmptyResultDataAccessException(
                messageSource
                    .getMessage(ConstantsInErp.MENSAGEM_RECURSO_EXPERADO, 
                    new Object[]{"1", "0"}, LocaleContextHolder.getLocale()), 1));

        BeanUtils.copyProperties(categoria, categoriaFromDb, "codigo");

        return categoriaRepository.save(categoriaFromDb);
    }
}