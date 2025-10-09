package at.spengergasse.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

// JPA
@Entity
@Table(name = "todo")

// Lombok
@Getter
@ToString(callSuper = true)
public class Todo extends BaseEntity {

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
