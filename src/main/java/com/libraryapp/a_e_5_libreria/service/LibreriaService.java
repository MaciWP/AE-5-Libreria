package com.libraryapp.a_e_5_libreria.service;

import com.libraryapp.a_e_5_libreria.domain.Libreria;
import com.libraryapp.a_e_5_libreria.domain.Libro;
import com.libraryapp.a_e_5_libreria.model.LibreriaDTO;
import com.libraryapp.a_e_5_libreria.model.LibroDTO;
import com.libraryapp.a_e_5_libreria.repos.LibreriaRepository;
import com.libraryapp.a_e_5_libreria.repos.LibroRepository;
import com.libraryapp.a_e_5_libreria.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class LibreriaService {

    private final LibreriaRepository libreriaRepository;
    private final LibroRepository libroRepository;

    public LibreriaService(final LibreriaRepository libreriaRepository,
            final LibroRepository libroRepository) {
        this.libreriaRepository = libreriaRepository;
        this.libroRepository = libroRepository;
    }

    public List<LibreriaDTO> findAll() {
        final List<Libreria> librerias = libreriaRepository.findAll(Sort.by("id"));
        return librerias.stream()
                .map(libreria -> mapToDTO(libreria, new LibreriaDTO()))
                .toList();
    }

    public LibreriaDTO get(final Long id) {
        return libreriaRepository.findById(id)
                .map(libreria -> mapToDTO(libreria, new LibreriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LibreriaDTO libreriaDTO) {
        final Libreria libreria = new Libreria();
        mapToEntity(libreriaDTO, libreria);
        return libreriaRepository.save(libreria).getId();
    }

    public void update(final Long id, final LibreriaDTO libreriaDTO) {
        final Libreria libreria = libreriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libreriaDTO, libreria);
        libreriaRepository.save(libreria);
    }

    public void delete(final Long id) {
        libreriaRepository.deleteById(id);
    }

    private LibreriaDTO mapToDTO(final Libreria libreria, final LibreriaDTO libreriaDTO) {
        libreriaDTO.setId(libreria.getId());
        libreriaDTO.setNombre(libreria.getNombre());
        libreriaDTO.setLibro(libreria.getLibro().stream()
                .map(libro -> libro.getId())
                .toList());
        return libreriaDTO;
    }

    private Libreria mapToEntity(final LibreriaDTO libreriaDTO, final Libreria libreria) {
        libreria.setNombre(libreriaDTO.getNombre());
        final List<Libro> libro = libroRepository.findAllById(
                libreriaDTO.getLibro() == null ? Collections.emptyList() : libreriaDTO.getLibro());
        if (libro.size() != (libreriaDTO.getLibro() == null ? 0 : libreriaDTO.getLibro().size())) {
            throw new NotFoundException("one of libro not found");
        }
        libreria.setLibro(new HashSet<>(libro));
        return libreria;
    }

    public List<LibroDTO> getLibrosByLibreria(final Long id) {
        final Libreria libreria = libreriaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return libreria.getLibro().stream()
                .map(this::mapLibroToDTO)
                .toList();
    }

    private LibroDTO mapLibroToDTO(final Libro libro) {
        final LibroDTO libroDTO = new LibroDTO();
        libroDTO.setId(libro.getId());
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor() == null ? null : libro.getAutor().getId());
        libroDTO.setEditorial(libro.getEditorial() == null ? null : libro.getEditorial().getId());
        return libroDTO;
    }

}
