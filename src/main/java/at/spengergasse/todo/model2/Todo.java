package at.spengergasse.todo.model2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import static at.spengergasse.todo.validation.Guard.textLength;


// Invariants
// Statements, which are always true

// Make illegal state unrepresentable


// JPA
//@Entity
//@Table(name = "todo")

// Lombok
@Getter
@ToString

public class Todo
{
    public static final String MAX_LEN_MSG = "title should be between 1..100 chars";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // JPA
    // not-null, not-blank, max 100 chars
    // @Column(name = "title", nullable = false, length = 100)

    @Embedded
    private Title title;


    // --- Ctor ---

    // JPA
    protected Todo() {}

    // Business Ctor
    public Todo(Title title) {
        this.title = title;

    }

    // --- Business Methods ---

    public void renameTitle(Title title)
    {
        this.title = title;

//        // 1. guard
//        if (title == null)
//            throw new IllegalArgumentException("title should be not null");
//
//        // 2. guard
//        var trimmedTitle = title.trim();
//        if (trimmedTitle.isEmpty() || title.length() > 100)
//            throw new IllegalArgumentException("title should be between 1..100 chars");
    }
}

