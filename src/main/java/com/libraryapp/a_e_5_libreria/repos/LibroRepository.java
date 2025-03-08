package com.libraryapp.a_e_5_libreria.repos;

import com.libraryapp.a_e_5_libreria.domain.Autor;
import com.libraryapp.a_e_5_libreria.domain.Editorial;
import com.libraryapp.a_e_5_libreria.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findFirstByAutor(Autor autor);

    Libro findFirstByEditorial(Editorial editorial);

}
