package at.spengergasse.todo.model2;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import static at.spengergasse.todo.validation.Guard.textLength;

@Embeddable
public class Title {

    public static final int MAX_LEN = 100;

    public static final String MAX_LEN_MSG = String.format("title should be between 1..%d chars", MAX_LEN);

    @Column(name = "title", nullable = false, length = MAX_LEN)
    private String title;

    protected Title() {}

    public Title(String value) {
        this.title = textLength(title, MAX_LEN, MAX_LEN_MSG);
    }
}
