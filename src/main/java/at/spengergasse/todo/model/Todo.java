package at.spengergasse.todo.model;

import at.spengergasse.todo.model2.Title;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

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


    // --- Ctor ---

    // JPA Ctor
    protected Todo() {}

    // Business Ctor
    public Todo(String title) {
        this.title = title;
    }


    // --- Business Methods ---

    public void renameTitle(String title)
    {
        // TODO use Guard
        this.title = title;
    }
}
