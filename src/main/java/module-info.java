module com.kyaw.tableviewdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kyaw.tableviewdemo to javafx.fxml;
    exports com.kyaw.tableviewdemo;
}