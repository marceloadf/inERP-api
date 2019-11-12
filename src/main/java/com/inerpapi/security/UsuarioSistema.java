package com.inerpapi.security;

import java.util.Collection;

import com.inerpapi.model.Usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User {

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

    private static final long serialVersionUID = 1L;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

}