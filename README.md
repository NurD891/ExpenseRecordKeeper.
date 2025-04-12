# Expense Record Keeper

## Project Description
The **Expense Record Keeper** is a Java-based command-line application that allows users to track personal or business expenses. Users can record expenses, categorize them, update or delete records, generate summary reports, and import/export data to CSV files. The application ensures data persistence by storing expenses in a file and includes input validation for user-friendly interaction.

##Student: Abdyganiev Nurdan

## Objectives
The project aims to:
- Implement CRUD (Create, Read, Update, Delete) operations for expense records.
- Provide a clear and intuitive command-line interface.
- Ensure data persistence using file storage.
- Validate user inputs to prevent errors.
- Generate reports summarizing expenses.
- Support importing and exporting data in CSV format.
- Demonstrate modular programming with clean and organized code.

## Project Requirements
1. **CRUD Operations**: Users can create, read, update, and delete expense records.
2. **Command Line Interface**: A menu-driven interface with clear prompts guides user interactions.
3. **Input Validation**: Validates expense amount (positive number), category (non-empty), date (YYYY-MM-DD format), and description (non-empty).
4. **Data Persistence**: Expenses are stored in `expenses.csv` to persist between sessions.
5. **Modular Design**: Code is organized into classes (`Expense`, `ExpenseManager`, `FileHandler`, `Menu`) for reusability.
6. **Report Generation**: Generates reports showing total expenses and expenses by category.
7. **File Import/Export**: Supports importing expenses from a CSV file and exporting to a CSV file.
8. **Error Handling**: Handles invalid inputs and file operation errors gracefully.
9. **Clean Code**: Uses meaningful variable names and minimal comments per requirements.
10. **Documentation**: This README explains the project’s purpose, functionality, and usage.

## Documentation
### Algorithms and Data Structures
- **Data Structure**: Uses `ArrayList<Expense>` to store expenses in memory, providing dynamic resizing and easy iteration.
- **File Storage**: Expenses are saved in a CSV file (`expenses.csv`) with the format `id,amount,category,date,description`.
- **CRUD Operations**:
  - **Create**: Adds a new expense with a unique ID and saves to file.
  - **Read**: Retrieves all expenses or a specific expense by ID.
  - **Update**: Modifies specified fields of an expense and updates the file.
  - **Delete**: Removes an expense by ID and updates the file.
- **Reports**: Uses a stream to group expenses by category and calculate totals.
- **File I/O**: Reads/writes CSV files line-by-line using `BufferedReader` and `BufferedWriter`.

### Functions/Modules
- **Expense**: Represents an expense with fields and methods for CSV conversion.
- **ExpenseManager**: Manages CRUD operations, reports, and file operations.
- **FileHandler**: Handles reading/writing to CSV files.
- **Menu**: Provides the command-line interface and input validation.



