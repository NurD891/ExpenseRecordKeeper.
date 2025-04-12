module com.example.finel_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finel_project to javafx.fxml;
    exports com.example.finel_project;
}
