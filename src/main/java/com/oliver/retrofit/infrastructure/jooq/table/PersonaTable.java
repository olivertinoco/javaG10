package com.oliver.retrofit.infrastructure.jooq.table;

import com.oliver.retrofit.infrastructure.jooq.record.PersonaRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import static org.jooq.impl.Internal.createUniqueKey;

public class PersonaTable extends TableImpl<PersonaRecord> {

    public static final PersonaTable PERSONA = new PersonaTable();

    public final TableField<PersonaRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this);
    public final TableField<PersonaRecord, String> NOMBRES = createField(DSL.name("nombres"), SQLDataType.VARCHAR(200).nullable(true),this);
    public final TableField<PersonaRecord, String> APELLIDO_PATERNO = createField(DSL.name("apellidoPaterno"), SQLDataType.VARCHAR(200).nullable(true), this);
    public final TableField<PersonaRecord, String> APELLIDO_MATERNO = createField(DSL.name("apellidoMaterno"), SQLDataType.VARCHAR(200).nullable(true), this);
    public final TableField<PersonaRecord, String> NOMBRE_COMPLETO = createField(DSL.name("nombreCompleto"), SQLDataType.VARCHAR(500).nullable(true), this);
    public final TableField<PersonaRecord, String> TIPO_DOCUMENTO = createField(DSL.name("tipoDocumento"), SQLDataType.VARCHAR(2).nullable(true), this);
    public final TableField<PersonaRecord, String> NUMERO_DOCUMENTO = createField(DSL.name("numeroDocumento"), SQLDataType.VARCHAR(10).nullable(true), this);
    public final TableField<PersonaRecord, String> DIGITO_VERIFICADOR = createField(DSL.name("digitoVerificador"), SQLDataType.VARCHAR(2).nullable(true), this);

    public PersonaTable() {
        super(DSL.name("persona"));
    }


    //NOTA: ESTO ES PARA FIJAR LA PRIMARY KEY PARA UPDATE / DELETE
    // ***********************************************************
    public final UniqueKey<PersonaRecord> PK_PERSONA =
            createUniqueKey(PERSONA, DSL.name("pk_persona"), ID);

    @Override
    public UniqueKey<PersonaRecord> getPrimaryKey() {
        return PK_PERSONA;
    }

    @Override
    public Class<? extends PersonaRecord> getRecordType() {
        return PersonaRecord.class;
    }

}
