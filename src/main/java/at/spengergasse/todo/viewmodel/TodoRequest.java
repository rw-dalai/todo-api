package at.spengergasse.todo.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO
// Data Transfer Object

// immutable (nicht veränderbar)
// getters, hashCode, equals, toString,

public record TodoRequest(
        @NotBlank(message = "title should not be blank")
        String title
) { }