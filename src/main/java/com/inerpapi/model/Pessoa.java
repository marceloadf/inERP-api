package com.inerpapi.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pessoa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pessoa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @Embedded
    private Endereco endereco;

    @NotNull
    private String nome;

    @NotNull
    private boolean ativo;

    public Endereco getEndereco() {
        return endereco;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @JsonIgnoreProperties
    @Transient
    public boolean isInativo(){
        return !this.ativo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ativo ? 1231 : 1237);
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        if (ativo != other.ativo)
            return false;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (endereco == null) {
            if (other.endereco != null)
                return false;
        } else if (!endereco.equals(other.endereco))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }
    
    
}