package at.spengergasse.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "todo_item")

@Getter
@ToString(callSuper = true)
public class TodoItem extends BaseEntity {
}
