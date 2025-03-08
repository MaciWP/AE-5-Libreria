package com.libraryapp.a_e_5_libreria.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LibreriaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    private List<Long> libro;

}
