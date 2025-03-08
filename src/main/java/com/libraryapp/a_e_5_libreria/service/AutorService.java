package com.libraryapp.a_e_5_libreria.service;

import com.libraryapp.a_e_5_libreria.domain.Autor;
import com.libraryapp.a_e_5_libreria.domain.Libro;
import com.libraryapp.a_e_5_libreria.model.AutorDTO;
import com.libraryapp.a_e_5_libreria.repos.AutorRepository;
import com.libraryapp.a_e_5_libreria.repos.LibroRepository;
import com.libraryapp.a_e_5_libreria.util.NotFoundException;
import com.libraryapp.a_e_5_libreria.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public AutorService(final AutorRepository autorRepository,
            final LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public List<AutorDTO> findAll() {
        final List<Autor> autors = autorRepository.findAll(Sort.by("id"));
        return autors.stream()
                .map(autor -> mapToDTO(autor, new AutorDTO()))
                .toList();
    }

    public AutorDTO get(final Long id) {
        return autorRepository.findById(id)
                .map(autor -> mapToDTO(autor, new AutorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AutorDTO autorDTO) {
        final Autor autor = new Autor();
        mapToEntity(autorDTO, autor);
        return autorRepository.save(autor).getId();
    }

    public void update(final Long id, final AutorDTO autorDTO) {
        final Autor autor = autorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(autorDTO, autor);
        autorRepository.save(autor);
    }

    public void delete(final Long id) {
        autorRepository.deleteById(id);
    }

    private AutorDTO mapToDTO(final Autor autor, final AutorDTO autorDTO) {
        autorDTO.setId(autor.getId());
        autorDTO.setNombre(autor.getNombre());
        return autorDTO;
    }

    private Autor mapToEntity(final AutorDTO autorDTO, final Autor autor) {
        autor.setNombre(autorDTO.getNombre());
        return autor;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Autor autor = autorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Libro autorLibro = libroRepository.findFirstByAutor(autor);
        if (autorLibro != null) {
            referencedWarning.setKey("autor.libro.autor.referenced");
            referencedWarning.addParam(autorLibro.getId());
            return referencedWarning;
        }
        return null;
    }

}
