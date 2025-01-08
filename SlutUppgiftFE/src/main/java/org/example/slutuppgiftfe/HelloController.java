package org.example.slutuppgiftfe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HelloController {

    @FXML // Används för att binda knappen i FXML till detta fält
    private Button addButton, deleteButton, editButton;

    @FXML // Används för att binda listvyn i FXML till detta fält
    private ListView<String> taskListView;

    @FXML // Används för att binda textfältet i FXML till detta fält
    private TextField textInput;

    // Metod för att lägga till en uppgift
    @FXML
    void handleAddTask(ActionEvent event) {
        String taskName = textInput.getText(); // Hämtar text från textfältet
        if (taskName.isEmpty()) { // Kontroll om textfältet är tomt
            showError("Task name cannot be empty."); // Visar ett felmeddelande
            return; // Avslutar metoden
        }
        try {
            // Skapar en anslutning till backend för att lägga till uppgiften
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/api/tasks").openConnection();
            connection.setRequestMethod("POST"); // Sätter metoden till POST
            connection.setRequestProperty("Content-Type", "application/json"); // Anger Content-Type som JSON
            connection.setDoOutput(true); // Tillåter att data skrivs till servern

            // Skapar JSON för att skicka till backend
            String json = "{\"taskName\":\"" + taskName + "\"}";
            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes()); // Skriver JSON-data
                os.flush(); // Säkerställer att data skickas
            }

            // Kontrollerar om uppgiften skapades framgångsrikt
            if (connection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                loadTasks(); // Laddar om uppgifterna
            } else {
                showError("Failed to add task. HTTP response code: " + connection.getResponseCode()); // Felhantering
            }
        } catch (Exception e) {
            showError("Error adding task: " + e.getMessage()); // Felhantering vid undantag
        }
    }

    // Metod för att ta bort en uppgift
    @FXML
    void handleDeleteTask(ActionEvent event) {
        String selectedTask = taskListView.getSelectionModel().getSelectedItem(); // Hämtar vald uppgift
        if (selectedTask == null) { // Kontroll om ingen uppgift är vald
            showError("Please select a task to delete."); // Visar ett felmeddelande
            return; // Avslutar metoden
        }
        try {
            int id = extractTaskId(selectedTask); // Hämtar ID från den valda uppgiften
            // Skapar en DELETE-anslutning till backend
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/api/tasks/" + id).openConnection();
            connection.setRequestMethod("DELETE"); // Sätter metoden till DELETE

            // Kontrollerar om uppgiften togs bort framgångsrikt
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                loadTasks(); // Laddar om uppgifterna
            } else {
                showError("Failed to delete task. HTTP response code: " + connection.getResponseCode()); // Felhantering
            }
        } catch (Exception e) {
            showError("Error deleting task: " + e.getMessage()); // Felhantering vid undantag
        }
    }

    // Metod för att redigera en uppgift
    @FXML
    void handleEditTask(ActionEvent event) {
        String selectedTask = taskListView.getSelectionModel().getSelectedItem(); // Hämtar vald uppgift
        String updatedTaskName = textInput.getText(); // Hämtar ny text från textfältet
        if (selectedTask == null || updatedTaskName.isEmpty()) { // Kontroll om ingen uppgift är vald eller textfältet är tomt
            showError("Please select a task and enter a new name."); // Visar ett felmeddelande
            return; // Avslutar metoden
        }
        try {
            int id = extractTaskId(selectedTask); // Hämtar ID från den valda uppgiften
            // Skapar en PUT-anslutning till backend
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/api/tasks/" + id).openConnection();
            connection.setRequestMethod("PUT"); // Sätter metoden till PUT
            connection.setRequestProperty("Content-Type", "application/json"); // Anger Content-Type som JSON
            connection.setDoOutput(true); // Tillåter att data skrivs till servern

            // Skapar JSON för att skicka till backend
            String json = "{\"id\":" + id + ",\"taskName\":\"" + updatedTaskName + "\"}";
            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes()); // Skriver JSON-data
                os.flush(); // Säkerställer att data skickas
            }

            // Kontrollerar om uppgiften uppdaterades framgångsrikt
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                loadTasks(); // Laddar om uppgifterna
            } else {
                showError("Failed to edit task. HTTP response code: " + connection.getResponseCode()); // Felhantering
            }
        } catch (Exception e) {
            showError("Error editing task: " + e.getMessage()); // Felhantering vid undantag
        }
    }

    // Metod för att ladda alla uppgifter från backend
    @FXML
    public void initialize() {
        loadTasks(); // Laddar uppgifter vid initialisering
    }

    private void loadTasks() {
        taskListView.getItems().clear(); // Tömmer listan innan laddning
        try {
            // Skapar en GET-anslutning till backend
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/api/tasks").openConnection();
            connection.setRequestMethod("GET"); // Sätter metoden till GET
            Scanner scanner = new Scanner(connection.getInputStream()); // Läser svar från backend
            StringBuilder response = new StringBuilder(); // Bygger upp svaret som en sträng
            while (scanner.hasNext()) {
                response.append(scanner.nextLine()); // Lägger till varje rad i svaret
            }
            scanner.close();

            // Splittar upp svaret i enskilda uppgifter och lägger till dem i listan
            String[] tasks = response.toString().replace("[", "").replace("]", "").split("},");
            for (String task : tasks) {
                if (!task.isBlank()) {
                    taskListView.getItems().add(task + "}"); // Lägg till uppgiften i listvyn
                }
            }
        } catch (Exception e) {
            showError("Error loading tasks: " + e.getMessage()); // Felhantering vid undantag
        }
    }

    // Extraherar ID från en uppgift
    private int extractTaskId(String task) {
        return Integer.parseInt(task.split(",")[0].split(":")[1].trim()); // Splittar upp texten och hämtar ID:t
    }

    // Visar ett felmeddelande i en dialogruta
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Skapar en felalert
        alert.setContentText(message); // Sätter felmeddelandet som innehåll
        alert.show(); // Visar alerten
    }
}
