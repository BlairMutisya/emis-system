package com.eduvod.eduvod.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseApiResponse<T> {

    @Schema(description = "HTTP status code")
    private int statusCode;

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Response data payload")
    private T data;
}
