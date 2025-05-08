package com.oliver.retrofit.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Persona {
    private Integer id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
