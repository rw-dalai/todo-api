package at.spengergasse.todo.services;

import at.spengergasse.todo.exceptions.ServiceException;
import at.spengergasse.todo.model.modelBean.Todo;
import at.spengergasse.todo.persistence.TodoRepository;
import at.spengergasse.todo.viewmodel.TodoRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service Layer (Business Logic Layer)
// ---------------------------------
// Business Logic Layer
// Handles Business Rules, Complex Business Logic
// Orchestrates between Controller and Repository
// Manages Transactions
// Throws Business Exceptions (ServiceException)


// Transaction Management Best Practice
// ---------------------------------
// CLASS LEVEL:  @Transactional(readOnly = true)  -> Default for all methods
//   - Most service methods are reads (queries)
//   - Safe by default (read-only)
//   - Less code repetition
//
// METHOD LEVEL: @Transactional -> Overrides class-level for write operations
//   - Explicitly marks write operations (INSERT, UPDATE, DELETE)
//   - Clear intent: "this method modifies data"
//   - Only applied where needed


// JPA Dirty Checking
// ---------------------------------
// - JPA automatically tracks changes to managed entities within a transaction
// - No need to explicitly call save() after modifying an entity
// - Changes are flushed to the database when the transaction commits


// Logging Best Practices
// ---------------------------------
// DEBUG level:
//   - For development and debugging
//   - Method entry/exit with parameters
//   - Detailed step-by-step execution
//
// INFO level:
//   - For production logging
//   - Important business events (created, updated, deleted)
//   - Keep concise (performance!)
//
// WARN level:
//   - Business rule violations
//   - Handled exceptions
//
// ERROR level:
//   - Unexpected failures


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)  // <- CLASS LEVEL: Default for all methods
public class TodoService
{
    // SLF4J Logger (Simple Logging Facade for Java)
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;


    public List<Todo> getAllTodos() // <- READ ONLY
    {
        log.debug("Retrieving all todos");
        List<Todo> todos = todoRepository.findAll();

        log.info("Retrieved {} todos", todos.size());
        return todos;
    }


    public Todo getOneTodo(Long id) // <- READ ONLY
    {
        log.debug("Retrieving todo with id: {}", id);

        Todo todo = todoRepository.findById(id).orElseThrow(() -> {
            log.warn("Todo not found with id: {}", id);
            return ServiceException.ofNotFound(id);
        });

        log.info("Found todo: {}", todo);
        return todo;
    }


    @Transactional  // <- WRITE OPERATION, overrides class-level
    public Todo createTodo(TodoRequest todoRequest)
    {
        log.debug("Creating new todo with title: {}", todoRequest.title());
        Todo newTodo = new Todo(todoRequest.title());

        // JPA will INSERT into database when transaction commits
        Todo savedTodo = todoRepository.save(newTodo);

        log.info("Created todo with id: {}", savedTodo.getId());
        return savedTodo;
    }


    @Transactional // <- WRITE OPERATION, overrides class-level
    public Todo updateTodo(Long id, TodoRequest todoRequest)
    {
        log.debug("Updating todo with id: {} to title: {}", id, todoRequest.title());

        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> {
            log.warn("Cannot update - todo not found with id: {}", id);
            return ServiceException.ofNotFound(id);
        });

        // JPA will UPDATE database when transaction commits
        // No need to call save() explicitly, since JPA tracks changes (dirty checking)
        existingTodo.renameTitle(todoRequest.title());
        //Todo updatedTodo = todoRepository.save(existingTodo);

        log.info("Updated todo with id: {}", id);
        return existingTodo;
    }


    @Transactional
    public void deleteTodo(Long id)
    {
        log.debug("Deleting todo with id: {}", id);

        // Verify entity exists before deletion
        if (!todoRepository.existsById(id)) {
            log.warn("Cannot delete - todo not found with id: {}", id);
            throw ServiceException.ofNotFound(id);
        }

        // JPA will DELETE from database when transaction commits
        todoRepository.deleteById(id);
        log.info("Deleted todo with id: {}", id);
    }
}
