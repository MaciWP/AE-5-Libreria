package com.libraryapp.a_e_5_libreria.service;

import com.libraryapp.a_e_5_libreria.domain.Editorial;
import com.libraryapp.a_e_5_libreria.domain.Libro;
import com.libraryapp.a_e_5_libreria.model.EditorialDTO;
import com.libraryapp.a_e_5_libreria.repos.EditorialRepository;
import com.libraryapp.a_e_5_libreria.repos.LibroRepository;
import com.libraryapp.a_e_5_libreria.util.NotFoundException;
import com.libraryapp.a_e_5_libreria.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EditorialService {

    private final EditorialRepository editorialRepository;
    private final LibroRepository libroRepository;

    public EditorialService(final EditorialRepository editorialRepository,
            final LibroRepository libroRepository) {
        this.editorialRepository = editorialRepository;
        this.libroRepository = libroRepository;
    }

    public List<EditorialDTO> findAll() {
        final List<Editorial> editorials = editorialRepository.findAll(Sort.by("id"));
        return editorials.stream()
                .map(editorial -> mapToDTO(editorial, new EditorialDTO()))
                .toList();
    }

    public EditorialDTO get(final Long id) {
        return editorialRepository.findById(id)
                .map(editorial -> mapToDTO(editorial, new EditorialDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EditorialDTO editorialDTO) {
        final Editorial editorial = new Editorial();
        mapToEntity(editorialDTO, editorial);
        return editorialRepository.save(editorial).getId();
    }

    public void update(final Long id, final EditorialDTO editorialDTO) {
        final Editorial editorial = editorialRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(editorialDTO, editorial);
        editorialRepository.save(editorial);
    }

    public void delete(final Long id) {
        editorialRepository.deleteById(id);
    }

    private EditorialDTO mapToDTO(final Editorial editorial, final EditorialDTO editorialDTO) {
        editorialDTO.setId(editorial.getId());
        editorialDTO.setNombre(editorial.getNombre());
        return editorialDTO;
    }

    private Editorial mapToEntity(final EditorialDTO editorialDTO, final Editorial editorial) {
        editorial.setNombre(editorialDTO.getNombre());
        return editorial;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Editorial editorial = editorialRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Libro editorialLibro = libroRepository.findFirstByEditorial(editorial);
        if (editorialLibro != null) {
            referencedWarning.setKey("editorial.libro.editorial.referenced");
            referencedWarning.addParam(editorialLibro.getId());
            return referencedWarning;
        }
        return null;
    }

}
