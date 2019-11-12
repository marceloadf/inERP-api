package com.inerpapi.repository;

import java.util.List;

import com.inerpapi.model.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    List<Categoria> findByNome(String nome);

}