package at.spengergasse.todo.controller;

import at.spengergasse.todo.model.Todo;
import at.spengergasse.todo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


// RESTful API
// Representation State Transfer
// Roy Fielding, PHD Dissertation, 2001


// GET /api/todos            -> HTTP 200, return all todos
// GET /api/todos/{id}       -> HTTP 200, return one todo by id
// POST /api/todos           -> HTTP 201, save todo into DB
// PUT /api/todos/{id}       -> HTTP 200, updates / replaces one existing todo by id
// DELETE /api/todos/{id}    -> HTTP 204, delete todo by id

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
//
//        return ResponseEntity.ok(todoRepository.findAll());
//    }


    @GetMapping("/api/todos/{id}")
    public void getOneTodo() {

    }
}
