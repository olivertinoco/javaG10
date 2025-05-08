package com.oliver.retrofit.domain.port.repository;

import com.oliver.retrofit.dto.PersonaDto;
import com.oliver.retrofit.infrastructure.jooq.record.PersonaRecord;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PersonaRepository {

    List<PersonaDto> findByNumeroDocumento(String numeroDocumento);
    PersonaRecord save(String dni);
    CompletableFuture<PersonaRecord> saveAsync(String dni);
    void update(PersonaDto persona);
    void deleteById(Integer id);
}
