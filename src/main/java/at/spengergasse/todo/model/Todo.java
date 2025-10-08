package at.spengergasse.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

// JPA
@Entity
@Table(name = "todo")

// Lombok
@Getter
@ToString
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // JPA
    @Column(name = "title", unique = false, nullable = false, length = 100)
    // Bean Validation
    @NotBlank @Size(max = 100)
    private String title;

    // JPA Ctor
    protected Todo() {}

    // Business Ctor
    public Todo(String title) {
        this.title = title;
    }
}
