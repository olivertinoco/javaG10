package com.oliver.retrofit;

import org.jooq.DSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.stereotype.Component;
import static org.jooq.impl.DSL.constraint;

@Component
public class CreaTablaPersona {

    private final DSLContext dsl;

    public CreaTablaPersona(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void creaTablaPersona() {
        dsl.createTableIfNotExists("persona")
                .column("id", SQLDataType.INTEGER.identity(true))
                .column("nombres", SQLDataType.VARCHAR(200))
                .column("apellidoPaterno", SQLDataType.VARCHAR(200))
                .column("apellidoMaterno", SQLDataType.VARCHAR(200))
                .column("nombreCompleto", SQLDataType.VARCHAR(500))
                .column("tipoDocumento", SQLDataType.VARCHAR(2))
                .column("numeroDocumento", SQLDataType.VARCHAR(10))
                .column("digitoVerificador", SQLDataType.VARCHAR(2))
                .constraints(
                        constraint("pk_persona").primaryKey("id"),
                        constraint("uk_numero_documento").unique("numeroDocumento")
                )
                .execute();
    }
}

