package com.libraryapp.a_e_5_libreria.rest;

import com.libraryapp.a_e_5_libreria.model.AutorDTO;
import com.libraryapp.a_e_5_libreria.service.AutorService;
import com.libraryapp.a_e_5_libreria.util.ReferencedException;
import com.libraryapp.a_e_5_libreria.util.ReferencedWarning;
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
@RequestMapping(value = "/api/autors", produces = MediaType.APPLICATION_JSON_VALUE)
public class AutorResource {

    private final AutorService autorService;

    public AutorResource(final AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> getAllAutors() {
        return ResponseEntity.ok(autorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> getAutor(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(autorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createAutor(@RequestBody @Valid final AutorDTO autorDTO) {
        final Long createdId = autorService.create(autorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAutor(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AutorDTO autorDTO) {
        autorService.update(id, autorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = autorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
