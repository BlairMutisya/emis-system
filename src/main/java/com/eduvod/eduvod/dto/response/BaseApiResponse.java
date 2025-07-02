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

    // Add this constructor for use when no data is returned
    public BaseApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }
    // Static factory method for success response
    public static <T> BaseApiResponse<T> success(T data) {
        return BaseApiResponse.<T>builder()
                .statusCode(200)
                .message("Success")
                .data(data)
                .build();
    }

    // Static factory method for custom success with message
    public static <T> BaseApiResponse<T> success(String message, T data) {
        return BaseApiResponse.<T>builder()
                .statusCode(200)
                .message(message)
                .data(data)
                .build();
    }
    // Static factory method for success with no data
    public static <T> BaseApiResponse<T> success() {
        return BaseApiResponse.<T>builder()
                .statusCode(200)
                .message("Success")
                .data(null)
                .build();
    }

    // Static factory method for error response
    public static <T> BaseApiResponse<T> error(String message) {
        return BaseApiResponse.<T>builder()
                .statusCode(400)
                .message(message)
                .data(null)
                .build();
    }
}
