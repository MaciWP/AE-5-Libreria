package com.libraryapp.a_e_5_libreria.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AutorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

}
