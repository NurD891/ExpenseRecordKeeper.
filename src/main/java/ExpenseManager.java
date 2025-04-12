import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseManager {
    private List<Expense> expenses;
    private int nextId;
    private FileHandler fileHandler;

    public ExpenseManager() {
        expenses = new ArrayList<>();
        nextId = 1;
        fileHandler = new FileHandler("expenses.csv");
        loadExpenses();
    }

    // Create
    public void addExpense(double amount, String category, LocalDate date, String description) {
        Expense expense = new Expense(nextId++, amount, category, date, description);
        expenses.add(expense);
        saveExpenses();
    }

    // Read
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public Expense getExpenseById(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return expense;
            }
        }
        return null;
    }

    // Update
    public boolean updateExpense(int id, Double amount, String category, LocalDate date, String description) {
        Expense expense = getExpenseById(id);
        if (expense == null) {
            return false;
        }
        if (amount != null) {
            expense.setAmount(amount);
        }
        if (category != null) {
            expense.setCategory(category);
        }
        if (date != null) {
            expense.setDate(date);
        }
        if (description != null) {
            expense.setDescription(description);
        }
        saveExpenses();
        return true;
    }

    // Delete
    public boolean deleteExpense(int id) {
        Expense expense = getExpenseById(id);
        if (expense == null) {
            return false;
        }
        expenses.remove(expense);
        saveExpenses();
        return true;
    }

    // Report: Total expenses
    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    // Report: Expenses by category
    public Map<String, Double> getExpensesByCategory() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)));
    }

    // Load expenses from file
    private void loadExpenses() {
        List<String> lines = fileHandler.readFile();
        for (String line : lines) {
            Expense expense = Expense.fromCsv(line, nextId++);
            expenses.add(expense);
        }
    }

    // Save expenses to file
    private void saveExpenses() {
        List<String> lines = new ArrayList<>();
        for (Expense expense : expenses) {
            lines.add(expense.toCsv());
        }
        fileHandler.writeFile(lines);
    }

    // Import expenses from CSV
    public void importExpenses(String filename) {
        FileHandler importFile = new FileHandler(filename);
        List<String> lines = importFile.readFile();
        for (String line : lines) {
            Expense expense = Expense.fromCsv(line, nextId++);
            expenses.add(expense);
        }
        saveExpenses();
    }

    // Export expenses to CSV
    public void exportExpenses(String filename) {
        FileHandler exportFile = new FileHandler(filename);
        List<String> lines = new ArrayList<>();
        for (Expense expense : expenses) {
            lines.add(expense.toCsv());
        }
        exportFile.writeFile(lines);
    }
}