package com.oliver.retrofit.infrastructure.config;

import com.oliver.retrofit.dto.PersonaDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ReniecApiClient {

    @GET("/v2/reniec/dni")
    Call<PersonaDto> getDatosPorDni(
            @Query("numero") String dni,
            @Header("Authorization") String token
    );
}
