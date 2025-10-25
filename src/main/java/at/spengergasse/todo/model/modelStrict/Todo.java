package at.spengergasse.todo.model.modelStrict;

// Domain Model - Rich Domain Model Approach
// ---------------------------------
// Uses value objects (Title) with guards for validation
// Validation happens at object creation (constructor)
// "Make illegal states unrepresentable"
// Type-safe, self-validating objects


// "Make Illegal States Unrepresentable"
// ---------------------------------
// Design principle: Use types to prevent invalid data
// Example: Title (value object) can never be invalid
// vs. String title which can be null, empty, or too long


// When to Use Rich Domain Model
// ---------------------------------
// - Complex business rules
// - Mission-critical domains
// - When type safety matters


import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;


// @Entity
// @Table(name = "todo")

// Lombok Annotations
@Getter
@ToString
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;


    // --- Constructors ---

    // JPA requires no-arg constructor
    protected Todo() {}

    // Business Constructor
    public Todo(Title title) {
        this.title = title;
    }


    // --- Business Methods ---

    public void renameTitle(Title title) {
        this.title = title;
    }
}
