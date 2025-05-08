package com.oliver.retrofit.application.web;

import com.oliver.retrofit.application.service.PersonaService;
import com.oliver.retrofit.dto.PersonaDto;
import com.oliver.retrofit.utils.ApiResponse;
import com.oliver.retrofit.exception.InvalidCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import static com.oliver.retrofit.utils.Constants.*;

@RestController
@RequestMapping("/personas/v1")
@RequiredArgsConstructor
@Log4j2
public class PersonaController {
    private final PersonaService personaService;

    @GetMapping("/listar")
    public ResponseEntity<ApiResponse<List<PersonaDto>>> getByNroDoc(
            @RequestParam(required = false) String dni
    ) {
        if (dni != null) {
            dni = dni.trim();
            if (dni.isEmpty()) {
                throw new InvalidCustomException(MESSAGE_DNI_EMPTY);
            }
            if (!dni.matches("\\d{8}")) {
                throw new InvalidCustomException(MESSAGE_DNI_NO_VALID);
            }
        }
        try{
            List<PersonaDto> lista = personaService.getByNumeroDocumento(dni);
            return Optional.ofNullable(lista)
                    .filter(l -> !l.isEmpty())
                    .map(l -> ResponseEntity.ok(
                            ApiResponse.<List<PersonaDto>>builder()
                                    .code(CODE_SUCCESS)
                                    .message(MESSAGE_SUCCESS_FIND)
                                    .data(l)
                                    .build()
                    ))
                    .orElseGet(() -> ResponseEntity.status(CODE_NOT_FOUND).body(
                            ApiResponse.<List<PersonaDto>>builder()
                                    .code(CODE_NOT_FOUND)
                                    .message(MESSAGE_NOT_FOUND)
                                    .data(null)
                                    .build()
                    ));
        }catch (Exception e){
            log.error("Error al listar personas", e);
            return ResponseEntity.status(CODE_BAD_REQUEST).body(
                    ApiResponse.<List<PersonaDto>>builder()
                            .code(CODE_BAD_REQUEST)
                            .message(MESSAGE_ALREADY_EXISTS + e.getMessage())
                            .data(null)
                            .build()
            );
        }
    }

    @GetMapping("/nuevo/{dni}")
    public PersonaDto create(@PathVariable String dni) {
        return personaService.create(dni);
    }


    @GetMapping("/nuevoAsync/{dni}")
    public CompletableFuture<ResponseEntity<PersonaDto>> buscarPorDni(@PathVariable String dni) {
        return personaService.createAsync(dni)
                .thenApply(persona -> ResponseEntity.ok(persona));
    }


    @PutMapping("/upd/{id}")
    public PersonaDto update(
            @PathVariable Integer id,
            @RequestBody PersonaDto dto
    ) {
        dto.setId(id);
        return personaService.update(dto);
    }

    @DeleteMapping("/del/{id}")
    public void delete(@PathVariable Integer id) {
        personaService.delete(id);
    }
}
