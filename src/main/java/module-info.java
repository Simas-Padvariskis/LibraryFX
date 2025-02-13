module com.example.pamokafx2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.sctp;
    requires java.desktop;
    requires java.sql;


    opens com.example.pamokafx2 to javafx.fxml;
    exports com.example.pamokafx2;
    exports com.example.pamokafx2.Controllers;
    exports com.example.pamokafx2.Models;
    exports com.example.pamokafx2.Views;
}