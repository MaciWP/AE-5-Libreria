package com.libraryapp.a_e_5_libreria.rest;

import com.libraryapp.a_e_5_libreria.model.LibreriaDTO;
import com.libraryapp.a_e_5_libreria.model.LibroDTO;
import com.libraryapp.a_e_5_libreria.service.LibreriaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/librerias", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibreriaResource {

    private final LibreriaService libreriaService;

    public LibreriaResource(final LibreriaService libreriaService) {
        this.libreriaService = libreriaService;
    }

    @GetMapping
    public ResponseEntity<List<LibreriaDTO>> getAllLibrerias() {
        return ResponseEntity.ok(libreriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibreriaDTO> getLibreria(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(libreriaService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLibreria(@RequestBody @Valid final LibreriaDTO libreriaDTO) {
        final Long createdId = libreriaService.create(libreriaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLibreria(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LibreriaDTO libreriaDTO) {
        libreriaService.update(id, libreriaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibreria(@PathVariable(name = "id") final Long id) {
        libreriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroDTO>> getLibrosByLibreria(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(libreriaService.getLibrosByLibreria(id));
    }

}
