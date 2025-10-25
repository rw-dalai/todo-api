package at.spengergasse.todo.viewmodel;

import jakarta.validation.constraints.NotBlank;

// DTO
// Data Transfer Object

// Immutable (nicht veränderbar, keine setter)
// getters, hashCode, equals, toString,

public record TodoRequest(
        // Fail Fast in the API Boundary
        @NotBlank(message = "title should not be blank")
        String title
) { }