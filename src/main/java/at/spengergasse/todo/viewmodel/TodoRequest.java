package at.spengergasse.todo.viewmodel;

import jakarta.validation.constraints.NotBlank;

public record TodoRequest(
        @NotBlank(message = "Title is required")
        String title
) { }