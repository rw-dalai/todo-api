package at.spengergasse.todo.validation;

import java.util.Objects;

public final class Guard {
    // not-null, not-empty, value.length <= maxLength
    public static String textLength(String value, int maxLength, String message) {
        Objects.requireNonNull(value);

        String trimmed = value.trim();
        if (trimmed.isEmpty() || trimmed.length() > maxLength) {
            throw new IllegalArgumentException(message);
        }

        return trimmed;
    }
}
