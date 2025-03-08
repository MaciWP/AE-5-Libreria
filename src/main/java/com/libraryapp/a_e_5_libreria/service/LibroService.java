package com.libraryapp.a_e_5_libreria.service;

import com.libraryapp.a_e_5_libreria.domain.Autor;
import com.libraryapp.a_e_5_libreria.domain.Editorial;
import com.libraryapp.a_e_5_libreria.domain.Libro;
import com.libraryapp.a_e_5_libreria.model.LibroDTO;
import com.libraryapp.a_e_5_libreria.repos.AutorRepository;
import com.libraryapp.a_e_5_libreria.repos.EditorialRepository;
import com.libraryapp.a_e_5_libreria.repos.LibreriaRepository;
import com.libraryapp.a_e_5_libreria.repos.LibroRepository;
import com.libraryapp.a_e_5_libreria.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final EditorialRepository editorialRepository;
    private final LibreriaRepository libreriaRepository;

    public LibroService(final LibroRepository libroRepository,
            final AutorRepository autorRepository, final EditorialRepository editorialRepository,
            final LibreriaRepository libreriaRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.editorialRepository = editorialRepository;
        this.libreriaRepository = libreriaRepository;
    }

    public List<LibroDTO> findAll() {
        final List<Libro> libroes = libroRepository.findAll(Sort.by("id"));
        return libroes.stream()
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .toList();
    }

    public LibroDTO get(final Long id) {
        return libroRepository.findById(id)
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LibroDTO libroDTO) {
        final Libro libro = new Libro();
        mapToEntity(libroDTO, libro);
        return libroRepository.save(libro).getId();
    }

    public void update(final Long id, final LibroDTO libroDTO) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroDTO, libro);
        libroRepository.save(libro);
    }

    public void delete(final Long id) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        libreriaRepository.findAllByLibro(libro)
                .forEach(libreria -> libreria.getLibro().remove(libro));
        libroRepository.delete(libro);
    }

    private LibroDTO mapToDTO(final Libro libro, final LibroDTO libroDTO) {
        libroDTO.setId(libro.getId());
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor() == null ? null : libro.getAutor().getId());
        libroDTO.setEditorial(libro.getEditorial() == null ? null : libro.getEditorial().getId());
        return libroDTO;
    }

    private Libro mapToEntity(final LibroDTO libroDTO, final Libro libro) {
        libro.setTitulo(libroDTO.getTitulo());
        final Autor autor = libroDTO.getAutor() == null ? null : autorRepository.findById(libroDTO.getAutor())
                .orElseThrow(() -> new NotFoundException("autor not found"));
        libro.setAutor(autor);
        final Editorial editorial = libroDTO.getEditorial() == null ? null : editorialRepository.findById(libroDTO.getEditorial())
                .orElseThrow(() -> new NotFoundException("editorial not found"));
        libro.setEditorial(editorial);
        return libro;
    }

}
