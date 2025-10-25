package at.spengergasse.todo.controller;

import at.spengergasse.todo.model.modelBean.Todo;
import at.spengergasse.todo.persistence.TodoRepository;
import at.spengergasse.todo.viewmodel.TodoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


// OLD Controller without Service Layer !!!

// @RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoControllerOLD
{
    private final TodoRepository todoRepository;


    // GET /api/todos -> 200 OK
    @GetMapping
//    public ResponseEntity<List<Todo>> getAllTodos()
    public List<Todo> getAllTodos()
    {
        // Return 200 OK with List of Todos
        // return ResponseEntity.ok(todoRepository.findAll());
        return todoRepository.findAll();
    }


    // GET /api/todos/{id} -> 200 OK | 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getOneTodo(@PathVariable Long id)
    {
//        IMPERATIVE Version
//        Optional<Todo> todoOptional = todoRepository.findById(id);
//        if (todoOptional.isPresent()) {
//            Todo todo = todoOptional.get();
//            return ResponseEntity.ok(todo);
//        } else {
//            return ResponseEntity.notFound().build();
//        }

        // DECLARATIVE Version
        // Return 200 OK with Todo or 404 Not Found
        return todoRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // POST /api/todos -> 201 Created (Location) + body
    // Returns Location Header: { Location: /api/todos/1 }
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody @Valid TodoRequest todoRequest)
    {
        // Create new Todo
        Todo newTodo = new Todo(todoRequest.title());
        Todo todoSaved = todoRepository.save(newTodo);

        // Return 201 Created with Location Header and body
        return ResponseEntity
            .created(URI.create("/api/todos/" + todoSaved.getId()))
            .body(todoSaved);
    }


    // PUT /api/todos/{id} -> 200 OK + body | 404 Not Found
    // Replaces entire todo
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id, @RequestBody @Valid TodoRequest todoRequest)
    {
        return todoRepository.findById(id)
            .map(existingTodo -> {
                // Update existing Todo
                existingTodo.renameTitle(todoRequest.title());
                Todo updatedTodo = todoRepository.save(existingTodo);

                // Return 200 (OK) with body
                return ResponseEntity.ok(updatedTodo);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // DELETE /api/todos/{id} -> 204 No Content | 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id)
    {
        if (!todoRepository.existsById(id)) {
            // Return 404 (Not Found)
            return ResponseEntity.notFound().build();
        }

        // Return 204 (No Content)
        todoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
