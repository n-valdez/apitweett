package com.example.twitterapi.api.helper;

import com.example.twitterapi.api.bean.ApiResponse;
import com.example.twitterapi.api.bean.ApiSingleResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ResponseUtils {


    public static final ResponseEntity<ApiResponse> API_RESPONSE_BAD_REQUEST = new ResponseEntity<>(
            new ApiResponse(false, Collections.singletonList(new ApiSingleResponse("Error en los parametros requeridos.")),
                    HttpStatus.SC_BAD_REQUEST), BAD_REQUEST);

    public static final ResponseEntity<ApiResponse> DEFAULT_ERROR_RESPONSE = new ResponseEntity<>(
            new ApiResponse(false, Collections.singletonList(new ApiSingleResponse("Error inesperado.")),
                    HttpStatus.SC_INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);

    private ResponseUtils() {
        throw new IllegalStateException("Utility class");
    }
}
