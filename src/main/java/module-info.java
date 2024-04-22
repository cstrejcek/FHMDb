module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;

    requires com.jfoenix;
    requires com.opencsv;
    requires com.google.gson;
    requires okhttp3;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.models;
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    opens at.ac.fhcampuswien.fhmdb.models.database to ormlite.jdbc;
}