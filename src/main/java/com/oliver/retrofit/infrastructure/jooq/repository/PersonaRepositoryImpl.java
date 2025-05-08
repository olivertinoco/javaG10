package com.oliver.retrofit.infrastructure.jooq.repository;

import com.oliver.retrofit.domain.port.repository.PersonaRepository;
import com.oliver.retrofit.dto.PersonaDto;
import com.oliver.retrofit.infrastructure.config.ReniecApiClient;
import com.oliver.retrofit.infrastructure.jooq.record.PersonaRecord;
import com.oliver.retrofit.infrastructure.jooq.table.PersonaTable;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Repository
@RequiredArgsConstructor
public class PersonaRepositoryImpl implements PersonaRepository {

    private final DSLContext dsl;
    private final ReniecApiClient reniecApiClient;
    private static final PersonaTable p = PersonaTable.PERSONA;

    @Value("${token.api}")
    private String token;

    @Override
    public List<PersonaDto> findByNumeroDocumento(String nroDni) {
        return
            dsl.select(
                    p.ID,
                    p.NOMBRES,
                    p.APELLIDO_PATERNO,
                    p.APELLIDO_MATERNO,
                    p.NOMBRE_COMPLETO,
                    p.TIPO_DOCUMENTO,
                    p.NUMERO_DOCUMENTO,
                    p.DIGITO_VERIFICADOR
            )
            .from(p)
            .where(
                    nroDni != null
                    ? p.NUMERO_DOCUMENTO.eq(nroDni)
                    : DSL.noCondition()
            )
            .orderBy(p.ID)
            .limit(10)
            .fetchInto(PersonaDto.class);
    }

    @Override
    public PersonaRecord save(String dni) {
        try {

            Response<PersonaDto> response = reniecApiClient
                    .getDatosPorDni(dni, "Bearer " + token).execute();

            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                PersonaDto data = response.body();

                PersonaRecord persona = new PersonaRecord();
                persona.setNombres(data.getNombres());
                persona.setApellidoPaterno(data.getApellidoPaterno());
                persona.setApellidoMaterno(data.getApellidoMaterno());
                persona.setNombreCompleto(data.getNombreCompleto());
                persona.setTipoDocumento(data.getTipoDocumento());
                persona.setNumeroDocumento(data.getNumeroDocumento());
                persona.setDigitoVerificador(data.getDigitoVerificador());

                dsl.insertInto(p)
                    .set(p.NOMBRES, persona.getNombres())
                    .set(p.APELLIDO_PATERNO, persona.getApellidoPaterno())
                    .set(p.APELLIDO_MATERNO, persona.getApellidoMaterno())
                    .set(p.NOMBRE_COMPLETO, persona.getNombreCompleto())
                    .set(p.TIPO_DOCUMENTO, persona.getTipoDocumento())
                    .set(p.NUMERO_DOCUMENTO, persona.getNumeroDocumento())
                    .set(p.DIGITO_VERIFICADOR, persona.getDigitoVerificador())
                    .execute();

                return persona;
            } else {
                throw new RuntimeException("Error al consultar RENIEC: " + response.code());
            }
        } catch (Exception e) {
            throw new RuntimeException("Fallo en llamada a RENIEC", e);
        }
    }

    @Override
    public CompletableFuture<PersonaRecord> saveAsync(String dni) {
        CompletableFuture<PersonaRecord> future = new CompletableFuture<>();

        reniecApiClient.getDatosPorDni(dni, "Bearer " + token).enqueue(new Callback<PersonaDto>() {
            @Override
            public void onResponse(Call<PersonaDto> call, Response<PersonaDto> response) {
                if (response.isSuccessful() && Objects.nonNull(response.body())) {
                    PersonaDto data = response.body();

                    PersonaRecord persona = new PersonaRecord();
                    persona.setNombres(data.getNombres());
                    persona.setApellidoPaterno(data.getApellidoPaterno());
                    persona.setApellidoMaterno(data.getApellidoMaterno());
                    persona.setNombreCompleto(data.getNombreCompleto());
                    persona.setTipoDocumento(data.getTipoDocumento());
                    persona.setNumeroDocumento(data.getNumeroDocumento());
                    persona.setDigitoVerificador(data.getDigitoVerificador());

                    dsl.insertInto(p)
                            .set(p.NOMBRES, persona.getNombres())
                            .set(p.APELLIDO_PATERNO, persona.getApellidoPaterno())
                            .set(p.APELLIDO_MATERNO, persona.getApellidoMaterno())
                            .set(p.NOMBRE_COMPLETO, persona.getNombreCompleto())
                            .set(p.TIPO_DOCUMENTO, persona.getTipoDocumento())
                            .set(p.NUMERO_DOCUMENTO, persona.getNumeroDocumento())
                            .set(p.DIGITO_VERIFICADOR, persona.getDigitoVerificador())
                            .execute();

                    future.complete(persona);
                } else {
                    future.completeExceptionally(new RuntimeException("Error al consultar RENIEC: " + response.code()));
                }
            }
            @Override
            public void onFailure(Call<PersonaDto> call, Throwable t) {
                future.completeExceptionally(new RuntimeException("Fallo en llamada a RENIEC", t));
            }
        });
        return future;
    }


    @Override
    public void update(PersonaDto dto) {
        PersonaRecord record = dsl
                .select()
                .from(p)
                .where(p.ID.eq(dto.getId()))
                .fetchOneInto(PersonaRecord.class);

        if (record != null) {
            if (dto.getNombres() != null) record.set(p.NOMBRES, dto.getNombres());
            if (dto.getApellidoPaterno() != null) record.set(p.APELLIDO_PATERNO, dto.getApellidoPaterno());
            if (dto.getApellidoMaterno() != null) record.set(p.APELLIDO_MATERNO, dto.getApellidoMaterno());
            if (dto.getNombreCompleto() != null) record.set(p.NOMBRE_COMPLETO, dto.getNombreCompleto());
            if (dto.getTipoDocumento() != null) record.set(p.TIPO_DOCUMENTO, dto.getTipoDocumento());
            if (dto.getNumeroDocumento() != null) record.set(p.NUMERO_DOCUMENTO, dto.getNumeroDocumento());
            if (dto.getDigitoVerificador() != null) record.set(p.DIGITO_VERIFICADOR, dto.getDigitoVerificador());
            record.update();
        }
    }

    @Override
    public void deleteById(Integer id) {
        dsl.deleteFrom(p)
                .where(p.ID.eq(id))
                .execute();
    }

}
