package com.inerpapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.inerpapi.event.RecursoCriadoEvent;
import com.inerpapi.model.Categoria;
import com.inerpapi.repository.CategoriaRepository;
import com.inerpapi.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope(read)")
    public List<Categoria> listCategoria() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Categoria> getCategoria(@PathVariable Long id) {
        return categoriaRepository.findById(id).map(categoria -> ResponseEntity.ok(categoria))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Categoria> saveCategoria(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
        Categoria categoriaUpdated = categoriaService.categoriaUpdate(id, categoria);

        return ResponseEntity.ok(categoriaUpdated);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria,
            HttpServletResponse response) {

        Categoria newCategoria = categoriaRepository.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoria(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
    }

}