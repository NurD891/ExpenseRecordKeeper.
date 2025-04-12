import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class ExpenseApp extends Application {
    private ExpenseManager manager;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Initialize ExpenseManager
        manager = new ExpenseManager();

        // Create output area for viewing expenses and reports
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Create buttons
        Button addButton = new Button("Add Expense");
        Button viewButton = new Button("View All Expenses");
        Button updateButton = new Button("Update Expense");
        Button deleteButton = new Button("Delete Expense");
        Button reportButton = new Button("Generate Reports");
        Button importButton = new Button("Import Expenses");
        Button exportButton = new Button("Export Expenses");
        Button exitButton = new Button("Exit");

        // Set button actions
        addButton.setOnAction(e -> showAddExpenseForm(primaryStage));
        viewButton.setOnAction(e -> viewExpenses());
        updateButton.setOnAction(e -> showUpdateExpenseForm(primaryStage));
        deleteButton.setOnAction(e -> showDeleteExpensePrompt(primaryStage));
        reportButton.setOnAction(e -> generateReports());
        importButton.setOnAction(e -> importExpenses(primaryStage));
        exportButton.setOnAction(e -> exportExpenses(primaryStage));
        exitButton.setOnAction(e -> primaryStage.close());

        // Layout for buttons (two rows for better spacing)
        HBox topButtonRow = new HBox(10, addButton, viewButton, updateButton, deleteButton);
        HBox bottomButtonRow = new HBox(10, reportButton, importButton, exportButton, exitButton);
        topButtonRow.setAlignment(Pos.CENTER);
        bottomButtonRow.setAlignment(Pos.CENTER);

        // Main layout
        VBox root = new VBox(10, topButtonRow, bottomButtonRow, outputArea);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);

        // Create scene
        Scene scene = new Scene(root, 600, 400);

        // Set up stage
        primaryStage.setTitle("Expense Record Keeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Form for adding an expense
    private void showAddExpenseForm(Stage owner) {
        Stage formStage = new Stage();
        formStage.setTitle("Add Expense");
        formStage.initOwner(owner);

        // Form fields
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter category");
        TextField dateField = new TextField();
        dateField.setPromptText("Enter date (YYYY-MM-DD)");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter description");

        // Buttons
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Submit action
        submitButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    showAlert("Error", "Amount must be positive.");
                    return;
                }
                String category = categoryField.getText().trim();
                if (category.isEmpty()) {
                    showAlert("Error", "Category cannot be empty.");
                    return;
                }
                LocalDate date = LocalDate.parse(dateField.getText());
                String description = descriptionField.getText().trim();
                if (description.isEmpty()) {
                    showAlert("Error", "Description cannot be empty.");
                    return;
                }
                manager.addExpense(amount, category, date, description);
                showAlert("Success", "Expense added successfully.");
                formStage.close();
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid amount.");
            } catch (DateTimeParseException ex) {
                showAlert("Error", "Invalid date format. Use YYYY-MM-DD.");
            }
        });

        // Cancel action
        cancelButton.setOnAction(e -> formStage.close());

        // Form layout
        HBox buttonRow = new HBox(10, submitButton, cancelButton);
        buttonRow.setAlignment(Pos.CENTER);
        VBox formLayout = new VBox(10,
                new Label("Amount:"), amountField,
                new Label("Category:"), categoryField,
                new Label("Date:"), dateField,
                new Label("Description:"), descriptionField,
                buttonRow);
        formLayout.setPadding(new Insets(10));
        formLayout.setAlignment(Pos.CENTER);

        // Create scene
        Scene formScene = new Scene(formLayout, 300, 250);
        formStage.setScene(formScene);
        formStage.show();
    }

    // View all expenses
    private void viewExpenses() {
        outputArea.clear();
        outputArea.appendText("All Expenses:\n");
        for (Expense expense : manager.getAllExpenses()) {
            outputArea.appendText(expense.toString() + "\n");
        }
    }

    // Form for updating an expense
    private void showUpdateExpenseForm(Stage owner) {
        Stage formStage = new Stage();
        formStage.setTitle("Update Expense");
        formStage.initOwner(owner);

        // Form fields
        TextField idField = new TextField();
        idField.setPromptText("Enter expense ID");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter new amount (optional)");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter new category (optional)");
        TextField dateField = new TextField();
        dateField.setPromptText("Enter new date (optional)");
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Enter new description (optional)");

        // Buttons
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Submit action
        submitButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (manager.getExpenseById(id) == null) {
                    showAlert("Error", "Expense not found.");
                    return;
                }
                Double amount = null;
                if (!amountField.getText().trim().isEmpty()) {
                    amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        showAlert("Error", "Amount must be positive.");
                        return;
                    }
                }
                String category = categoryField.getText().trim();
                if (category.isEmpty()) {
                    category = null;
                }
                LocalDate date = null;
                if (!dateField.getText().trim().isEmpty()) {
                    date = LocalDate.parse(dateField.getText());
                }
                String description = descriptionField.getText().trim();
                if (description.isEmpty()) {
                    description = null;
                }
                if (manager.updateExpense(id, amount, category, date, description)) {
                    showAlert("Success", "Expense updated successfully.");
                    formStage.close();
                } else {
                    showAlert("Error", "Failed to update expense.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid ID or amount.");
            } catch (DateTimeParseException ex) {
                showAlert("Error", "Invalid date format. Use YYYY-MM-DD.");
            }
        });

        // Cancel action
        cancelButton.setOnAction(e -> formStage.close());

        // Form layout
        HBox buttonRow = new HBox(10, submitButton, cancelButton);
        buttonRow.setAlignment(Pos.CENTER);
        VBox formLayout = new VBox(10,
                new Label("ID:"), idField,
                new Label("Amount:"), amountField,
                new Label("Category:"), categoryField,
                new Label("Date:"), dateField,
                new Label("Description:"), descriptionField,
                buttonRow);
        formLayout.setPadding(new Insets(10));
        formLayout.setAlignment(Pos.CENTER);

        // Create scene
        Scene formScene = new Scene(formLayout, 300, 300);
        formStage.setScene(formScene);
        formStage.show();
    }

    // Prompt for deleting an expense
    private void showDeleteExpensePrompt(Stage owner) {
        Stage promptStage = new Stage();
        promptStage.setTitle("Delete Expense");
        promptStage.initOwner(owner);

        // Form field
        TextField idField = new TextField();
        idField.setPromptText("Enter expense ID");

        // Buttons
        Button submitButton = new Button("Delete");
        Button cancelButton = new Button("Cancel");

        // Submit action
        submitButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (manager.deleteExpense(id)) {
                    showAlert("Success", "Expense deleted successfully.");
                    promptStage.close();
                } else {
                    showAlert("Error", "Expense not found.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid ID.");
            }
        });

        // Cancel action
        cancelButton.setOnAction(e -> promptStage.close());

        // Form layout
        HBox buttonRow = new HBox(10, submitButton, cancelButton);
        buttonRow.setAlignment(Pos.CENTER);
        VBox formLayout = new VBox(10, new Label("ID:"), idField, buttonRow);
        formLayout.setPadding(new Insets(10));
        formLayout.setAlignment(Pos.CENTER);

        // Create scene
        Scene promptScene = new Scene(formLayout, 250, 150);
        promptStage.setScene(promptScene);
        promptStage.show();
    }

    // Generate reports
    private void generateReports() {
        outputArea.clear();
        outputArea.appendText("=== Reports ===\n");
        outputArea.appendText(String.format("Total Expenses: $%.2f\n", manager.getTotalExpenses()));
        outputArea.appendText("\nExpenses by Category:\n");
        Map<String, Double> byCategory = manager.getExpensesByCategory();
        for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
            outputArea.appendText(String.format("%s: $%.2f\n", entry.getKey(), entry.getValue()));
        }
    }

    // Import expenses
    private void importExpenses(Stage owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File to Import");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(owner);
        if (file != null) {
            try {
                manager.importExpenses(file.getAbsolutePath());
                showAlert("Success", "Expenses imported successfully.");
            } catch (Exception ex) {
                showAlert("Error", "Error importing file: " + ex.getMessage());
            }
        }
    }

    // Export expenses
    private void exportExpenses(Stage owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File to Export");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(owner);
        if (file != null) {
            try {
                manager.exportExpenses(file.getAbsolutePath());
                showAlert("Success", "Expenses exported successfully.");
            } catch (Exception ex) {
                showAlert("Error", "Error exporting file: " + ex.getMessage());
            }
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}