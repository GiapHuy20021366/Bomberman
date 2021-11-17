module com.example.semesterexam {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.semesterexam.main to javafx.fxml;
    exports com.example.semesterexam.main;
}