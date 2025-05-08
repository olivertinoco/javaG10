package com.oliver.retrofit.infrastructure.jooq.record;

import lombok.Getter;
import lombok.Setter;
import org.jooq.impl.UpdatableRecordImpl;
import com.oliver.retrofit.infrastructure.jooq.table.PersonaTable;

@Getter
@Setter
public class PersonaRecord extends UpdatableRecordImpl<PersonaRecord> {
    private Integer id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String digitoVerificador;

    public PersonaRecord() {
        super(PersonaTable.PERSONA);
    }
}
