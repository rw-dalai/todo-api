package at.spengergasse.todo.controller;

import at.spengergasse.todo.model.Todo;
import at.spengergasse.todo.persistence.TodoRepository;
import at.spengergasse.todo.viewmodel.TodoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Inversion of Control
// Dependency Injection


// HTTP Verb     idempotent    safe
// GET              true       true
// POST             false      false
// PUT              true       false
// PATCH            false      false
// DELETE           true       false

// HTTP Verben -> CRUD Operations
// GET         -> READ
// POST        -> CREATE (insert)
// PUT         -> UPDATE (replace db row)
// PATCH       -> UPDATE (update one column)
// DELETE      -> DELETE


// HTTP Status Codes
// 200 -> OK (GET)
// 201 -> CREATED (POST)
// 204 -> NO_CONTENT (DELETE)
// 400 -> BAD_REQUEST
// 404 -> NOT_FOUND


// RESTful API
// Representation State Transfer
// Roy Fielding, PHD Dissertation, 2001


// GET /api/todos            -> HTTP 200, return all todos
// GET /api/todos/{id}       -> HTTP 200, return one todo by id
// POST /api/todos           -> HTTP 201, save todo into DB
// PUT /api/todos/{id}       -> HTTP 200, updates / replaces one existing todo by id
// DELETE /api/todos/{id}    -> HTTP 204, delete todo by id

// JSON Serialization (Jackson Object Mapper)
// Object -> JSON Object

// JSON -> Object (Jackson Object Mapper)

// spring mvc
@RestController
@RequestMapping("/api/todos")

// lombok
@RequiredArgsConstructor
public class TodoController {

    // Dependency Injection (DI)
    private final TodoRepository todoRepository;

//    public TodoController(TodoRepository todoRepository) {
//        this.todoRepository = todoRepository;
//    }


    // Request: HTTP GET /api/todos -> return all todos
    // Response: HTTP 200 [{ title: "german", }, { title: "english"} ]

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAllTodos() {

        List<Todo> todos = todoRepository.findAll();
        // var todos = todoRepository.findAll();

        return todos;

    }

//    @GetMapping
//    public ResponseEntity<List<Todo>> getTodos() {
//        return ResponseEntity.ok(todoRepository.findAll());
//    }


    @GetMapping("/{id}")
    public ResponseEntity<Todo> getOneTodo(@PathVariable Long id) {

        // if found -> ResponseEntity (code=200, body=todo)
        // else -> ResponseEntity (code=404)

//        IMPERATIVE Version
//        Optional<Todo> todoOptional = todoRepository.findById(id);
//        if (todoOptional.isPresent()) {
//            Todo todo = todoOptional.get();
//            return ResponseEntity.ok(todo);
//        } else {
//            return ResponseEntity.notFound().build();
//        }

        // DECLARATIVE Version
        return todoRepository
            .findById(id)
            .map(todo -> ResponseEntity.ok(todo))
            .orElseGet( () -> ResponseEntity.notFound().build());
    }



    // { title: "Wäsche waschen" }
    @PostMapping
    public Todo createTodo(@RequestBody @Valid TodoRequest todoRequest) {

        Todo todo = new Todo(todoRequest.title());

        Todo savedTodo = todoRepository.save(todo);

        return savedTodo;
    }
}
