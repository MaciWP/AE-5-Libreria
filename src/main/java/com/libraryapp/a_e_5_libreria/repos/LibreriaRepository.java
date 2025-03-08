package com.libraryapp.a_e_5_libreria.repos;

import com.libraryapp.a_e_5_libreria.domain.Libreria;
import com.libraryapp.a_e_5_libreria.domain.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibreriaRepository extends JpaRepository<Libreria, Long> {

    Libreria findFirstByLibro(Libro libro);

    List<Libreria> findAllByLibro(Libro libro);

}
