module ru.cs213.frontend {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.cs213 to javafx.fxml;
    exports ru.cs213.frontend;
}
