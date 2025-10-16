package at.spengergasse.todo;

import at.spengergasse.todo.controller.TodoController;
import at.spengergasse.todo.persistence.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApi3Application {

    public static void main(String[] args) {
        SpringApplication.run(TodoApi3Application.class, args);
    }

}
