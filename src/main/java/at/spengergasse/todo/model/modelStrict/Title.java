package at.spengergasse.todo.model.modelStrict;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import static at.spengergasse.todo.validation.Guard.hasTextMax;

// JPA: Makes this embeddable in entities (no separate table)
@Getter
@Embeddable
public class Title {

    public static final int MAX_LEN = 100;
    public static final String MAX_LEN_MSG = String.format("title should be between 1..%d chars", MAX_LEN);

    @Column(name = "title", nullable = false, length = MAX_LEN)
    private String title;

    // JPA constructor
    protected Title() {}

    // Business Constructor
    public Title(String value) {
        // Guard ensures: not-null, not-blank, length <= MAX_LEN
        this.title = hasTextMax(value, MAX_LEN, MAX_LEN_MSG);
    }
}
