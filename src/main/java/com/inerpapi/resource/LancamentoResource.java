package com.inerpapi.resource;

import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.inerpapi.event.RecursoCriadoEvent;
import com.inerpapi.model.Lancamento;
import com.inerpapi.repository.LancamentoRepository;
import com.inerpapi.repository.filter.LancamentoFilter;
import com.inerpapi.repository.projection.ResumoLancamento;
import com.inerpapi.service.LancamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> getLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.buscarLancamentos(lancamentoFilter, pageable);
    }

    @GetMapping(params = "resumo")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<ResumoLancamento> getResumoLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.buscarResumoLancamentos(lancamentoFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> getLancamento(@PathVariable Long id) {
        return lancamentoRepository.findById(id).map(lancamento -> ResponseEntity.ok(lancamento))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/now")
    LocalDate getNow(@RequestParam LocalDate data) {
        return data;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> createLancamento(@Valid @RequestBody Lancamento lancamento,
            HttpServletResponse response) {
        Lancamento lancamentoSaved = lancamentoService.createLancamento(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSaved.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSaved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    public void deleteLancamento(@PathVariable Long id){
        lancamentoRepository.deleteById(id);
    }
}