package com.oliver.retrofit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDto {
    private Integer id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;
}
