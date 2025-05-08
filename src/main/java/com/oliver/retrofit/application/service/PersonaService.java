package com.oliver.retrofit.application.service;

import com.oliver.retrofit.domain.port.repository.PersonaRepository;
import com.oliver.retrofit.dto.PersonaDto;
import com.oliver.retrofit.mapper.PersonaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepository repository;
    private final PersonaMapper mapper;

    public List<PersonaDto> getByNumeroDocumento(String nroDoc) {
        return repository.findByNumeroDocumento(nroDoc);
    }

    public PersonaDto create(String dni) {
        var personaRecord = repository.save(dni);
        return mapper.toDto(personaRecord);
    }

    public CompletableFuture<PersonaDto> createAsync(String dni) {
        return repository.saveAsync(dni)
                .thenApply(mapper::toDto);
    }

    public PersonaDto update(PersonaDto dto) {
        repository.update(dto);
        return dto;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
