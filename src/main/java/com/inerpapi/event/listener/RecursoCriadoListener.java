package com.inerpapi.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import com.inerpapi.event.RecursoCriadoEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

    @Override
    public void onApplicationEvent(RecursoCriadoEvent event) {
        HttpServletResponse response = event.getResponse();
        Long codigo = event.getCodigo();

        adicionaHeaderLocation(response, codigo);
    }

    private void adicionaHeaderLocation(HttpServletResponse response, Long codigo) {
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path("/{codigo}")
            .buildAndExpand(codigo)
            .toUri();
        
        response.setHeader("location", location.toASCIIString());
    }

}