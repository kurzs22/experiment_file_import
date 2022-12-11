package de.kurzware.experiment.fileimport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatasource {

    private Connection connection;

    private final String url = "jdbc:h2:./test";
    private final String user = "sa";
    private final String password = "";

    public void openConnection() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
