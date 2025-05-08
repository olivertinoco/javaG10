package com.oliver.retrofit.mapper;

import com.oliver.retrofit.domain.model.Persona;
import com.oliver.retrofit.dto.PersonaDto;
import com.oliver.retrofit.infrastructure.jooq.record.PersonaRecord;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PersonaMapper {

    PersonaDto toDto(PersonaRecord record);
    Persona toEntity(PersonaDto dto);
    PersonaRecord toRecord(PersonaDto dto);

}
