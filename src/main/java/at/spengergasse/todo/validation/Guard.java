package at.spengergasse.todo.validation;

// Guard Pattern (Defensive Programming)
// ---------------------------------
// Centralized validation logic
// Throws IllegalArgumentException on failure
// Returns validated/normalized value on success
// Used by value objects to enforce invariants


import java.util.Objects;

public final class Guard {

    // Guard: not-null, not-empty, length <= maxLength
    public static String hasTextMax(String value, int maxLength, String message) {
        // Guard 1: null check
        Objects.requireNonNull(value, "value must not be null");

        // Normalize: trim whitespace
        String trimmed = value.trim();

        // Guard 2: not empty and not too long
        if (trimmed.isEmpty() || trimmed.length() > maxLength) {
            throw new IllegalArgumentException(message);
        }

        // Return validated, normalized value
        return trimmed;
    }

    // More guards can be added here:
    // - hasTextMinMax(String value, int min, int max, String message)
    // - isPositive(int value, String message)
    // - isEmail(String value, String message)
    // etc.
}
