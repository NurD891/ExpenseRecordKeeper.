import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private int id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    // Constructor
    public Expense(int id, double amount, String category, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Convert Expense to CSV format
    public String toCsv() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return id + "," + amount + "," + category + "," + date.format(formatter) + "," + description;
    }

    // Create Expense from CSV string
    public static Expense fromCsv(String csvLine, int id) {
        String[] parts = csvLine.split(",");
        double amount = Double.parseDouble(parts[1]);
        String category = parts[2];
        LocalDate date = LocalDate.parse(parts[3]);
        String description = parts[4];
        return new Expense(id, amount, category, date, description);
    }

    // String representation for display
    @Override
    public String toString() {
        return "ID: " + id + ", Amount: $" + amount + ", Category: " + category +
                ", Date: " + date + ", Description: " + description;
    }
}