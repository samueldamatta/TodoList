package br.com.ToDoListJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ToDoListJava.model.Tarefas;
import br.com.ToDoListJava.repository.TarefaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repositorio;

    // 1. Listar todas as tarefas
    @GetMapping
    public List<Tarefas> listarTarefas() {
        return repositorio.findAll();
    }

    // 2. Criar uma nova tarefa
    @PostMapping
    public Tarefas criarTarefa(@RequestBody Tarefas tarefa) {
        return repositorio.save(tarefa);
    }

    // 3. Buscar uma tarefa pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefas> buscarTarefaPorId(@PathVariable Long id) {
        Optional<Tarefas> tarefa = repositorio.findById(id);
        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4. Atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefas> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefas tarefaAtualizada) {
        Optional<Tarefas> tarefaExistente = repositorio.findById(id);

        if (tarefaExistente.isPresent()) {
            Tarefas tarefa = tarefaExistente.get();
            tarefa.setDescricaoTarefa(tarefaAtualizada.getDescricaoTarefa());
            repositorio.save(tarefa);
            return ResponseEntity.ok(tarefa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Excluir uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        Optional<Tarefas> tarefaExistente = repositorio.findById(id);

        if (tarefaExistente.isPresent()) {
            repositorio.delete(tarefaExistente.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
