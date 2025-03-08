package com.libraryapp.a_e_5_libreria.rest;

import com.libraryapp.a_e_5_libreria.model.EditorialDTO;
import com.libraryapp.a_e_5_libreria.service.EditorialService;
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
@RequestMapping(value = "/api/editorials", produces = MediaType.APPLICATION_JSON_VALUE)
public class EditorialResource {

    private final EditorialService editorialService;

    public EditorialResource(final EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    @GetMapping
    public ResponseEntity<List<EditorialDTO>> getAllEditorials() {
        return ResponseEntity.ok(editorialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditorialDTO> getEditorial(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(editorialService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createEditorial(
            @RequestBody @Valid final EditorialDTO editorialDTO) {
        final Long createdId = editorialService.create(editorialDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEditorial(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EditorialDTO editorialDTO) {
        editorialService.update(id, editorialDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditorial(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = editorialService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        editorialService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
