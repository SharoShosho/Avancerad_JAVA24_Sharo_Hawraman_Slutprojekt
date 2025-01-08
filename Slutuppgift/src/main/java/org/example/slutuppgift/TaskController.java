package org.example.slutuppgift;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // Markerar klassen som en REST-controller
@RequestMapping("/api/tasks") // Grundläggande URL-väg för alla endpoints i denna controller
public class TaskController {
    private List<Task> taskList = new ArrayList<>(); // Lista som håller alla uppgifter
    private int currentId = 1; // Variabel för att generera unika ID:n för nya uppgifter

    public TaskController() {
        // Skapar två standarduppgifter
        taskList.add(new Task(currentId++, "Sova"));
        taskList.add(new Task(currentId++, "Träna"));
    }

    @GetMapping // Hämtar alla uppgifter
    public List<Task> getAllTasks() {
        return taskList; // Returnerar hela listan med uppgifter
    }

    @GetMapping("/{id}") // Hämtar en uppgift baserat på dess ID
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        // Letar efter en uppgift med det givna ID:t
        return taskList.stream()
                .filter(task -> task.getId() == id) // Filtrerar efter ID
                .findFirst() // Tar den första matchande uppgiften
                .map(ResponseEntity::ok) // Returnerar uppgiften med HTTP 200
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Returnerar 404 om ingen hittas
    }

    @PostMapping // Skapar en ny uppgift
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        // Kontrollerar att uppgiften inte är null och har ett giltigt namn
        if (task != null && task.getTaskName() != null && !task.getTaskName().isEmpty()) {
            task.setId(currentId++); // Tilldelar ett nytt ID
            taskList.add(task); // Lägger till uppgiften i listan
            return ResponseEntity.status(HttpStatus.CREATED).body(task); // Returnerar 201 Created med uppgiften
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Returnerar 400 Bad Request
        }
    }

    @PutMapping("/{id}") // Uppdaterar en befintlig uppgift
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        // Itererar igenom listan för att hitta rätt uppgift
        for (Task task : taskList) {
            if (task.getId() == id) { // Om ID matchar
                task.setTaskName(updatedTask.getTaskName()); // Uppdaterar namnet
                return ResponseEntity.ok(task); // Returnerar uppdaterad uppgift med HTTP 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returnerar 404 om ID inte hittas
    }

    @DeleteMapping("/{id}") // Tar bort en uppgift baserat på dess ID
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        // Försöker ta bort uppgiften från listan
        boolean removed = taskList.removeIf(task -> task.getId() == id);
        if (removed) { // Om en uppgift togs bort
            return ResponseEntity.ok("Task with ID " + id + " has been deleted."); // Returnerar 200 OK
        } else { // Om ingen uppgift hittades
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with ID " + id + " not found."); // Returnerar 404
        }
    }
}
