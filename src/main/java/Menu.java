import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private ExpenseManager manager;
    private Scanner scanner;

    public Menu() {
        manager = new ExpenseManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                addExpense();
            } else if (choice.equals("2")) {
                viewExpenses();
            } else if (choice.equals("3")) {
                updateExpense();
            } else if (choice.equals("4")) {
                deleteExpense();
            } else if (choice.equals("5")) {
                generateReports();
            } else if (choice.equals("6")) {
                importExpenses();
            } else if (choice.equals("7")) {
                exportExpenses();
            } else if (choice.equals("8")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Expense Record Keeper ===");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Update Expense");
        System.out.println("4. Delete Expense");
        System.out.println("5. Generate Reports");
        System.out.println("6. Import Expenses");
        System.out.println("7. Export Expenses");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    private void addExpense() {
        System.out.print("Enter amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        System.out.print("Enter category (e.g., Food, Travel): ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }

        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date;
        try {
            date = LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("Description cannot be empty.");
            return;
        }

        manager.addExpense(amount, category, date, description);
        System.out.println("Expense added successfully.");
    }

    private void viewExpenses() {
        System.out.println("\nAll Expenses:");
        for (Expense expense : manager.getAllExpenses()) {
            System.out.println(expense);
        }
    }

    private void updateExpense() {
        System.out.print("Enter expense ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        if (manager.getExpenseById(id) == null) {
            System.out.println("Expense not found.");
            return;
        }

        System.out.print("Enter new amount (or press Enter to skip): ");
        String amountInput = scanner.nextLine();
        Double amount = null;
        if (!amountInput.isEmpty()) {
            try {
                amount = Double.parseDouble(amountInput);
                if (amount <= 0) {
                    System.out.println("Amount must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount.");
                return;
            }
        }

        System.out.print("Enter new category (or press Enter to skip): ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            category = null;
        }

        System.out.print("Enter new date (YYYY-MM-DD, or press Enter to skip): ");
        String dateInput = scanner.nextLine();
        LocalDate date = null;
        if (!dateInput.isEmpty()) {
            try {
                date = LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format.");
                return;
            }
        }

        System.out.print("Enter new description (or press Enter to skip): ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            description = null;
        }

        if (manager.updateExpense(id, amount, category, date, description)) {
            System.out.println("Expense updated successfully.");
        } else {
            System.out.println("Failed to update expense.");
        }
    }

    private void deleteExpense() {
        System.out.print("Enter expense ID to delete: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        if (manager.deleteExpense(id)) {
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Expense not found.");
        }
    }

    private void generateReports() {
        System.out.println("\n=== Reports ===");
        System.out.printf("Total Expenses: $%.2f%n", manager.getTotalExpenses());
        System.out.println("\nExpenses by Category:");
        Map<String, Double> byCategory = manager.getExpensesByCategory();
        for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
            System.out.printf("%s: $%.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private void importExpenses() {
        System.out.print("Enter CSV file to import (e.g., import.csv): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Filename cannot be empty.");
            return;
        }
        try {
            manager.importExpenses(filename);
            System.out.println("Expenses imported successfully.");
        } catch (Exception e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }

    private void exportExpenses() {
        System.out.print("Enter CSV file to export to (e.g., export.csv): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) {
            System.out.println("Filename cannot be empty.");
            return;
        }
        try {
            manager.exportExpenses(filename);
            System.out.println("Expenses exported successfully.");
        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}