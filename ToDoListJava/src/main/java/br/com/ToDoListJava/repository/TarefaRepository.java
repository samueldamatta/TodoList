package br.com.ToDoListJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ToDoListJava.model.Tarefas;

public interface TarefaRepository extends JpaRepository<Tarefas, Long> {
}
