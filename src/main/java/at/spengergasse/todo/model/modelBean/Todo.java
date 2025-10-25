package at.spengergasse.todo.model.modelBean;

// Domain Model - Bean Validation Approach
// ---------------------------------
// Uses Jakarta Bean Validation annotations for declarative validation
// Validation is triggered by @Valid or by Hibernate before persisting in JPA
// Simpler to implement, less boilerplate than rich domain model


// When to Use Bean Validation
// ---------------------------------
// - Not Secure/Critical Domains
// - When team prefers declarative style
// - DTO/Request validation


import at.spengergasse.todo.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

// JPA Annotations
@Entity
@Table(name = "todo")

// Lombok Annotations
@Getter
@ToString(callSuper = true)
public class Todo extends BaseEntity {

    // JPA Mapping
    @Column(name = "title", unique = false, nullable = false, length = 100)

    // Bean Validation
    @NotBlank(message = "title must not be blank")
    @Size(max = 100, message = "title must not exceed 100 characters")
    private String title;


    // --- Constructors ---

    // JPA requires no-arg constructor
    protected Todo() {}

    // Business Constructor
    public Todo(String title) {
        this.title = title;
    }


    // --- Business Methods ---

    public void renameTitle(String title) {
        this.title = title;
    }
}
