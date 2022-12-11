package de.kurzware.experiment.fileimport;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PersonTable implements MyTables {

    private BigDecimal id;
    private String name;
    private Timestamp modified;

    public PersonTable() {
        // nothing to do
    }

    public PersonTable(PersonTable personTable) {
        this.id = personTable.id;
        this.name = personTable.name;
        this.modified = personTable.modified;
    }

    @Override
    public String getCreateStatement() {
        return """
               DROP TABLE IF EXISTS person;
               CREATE TABLE person (
                    id NUMBER,
                    name TEXT,
                    modified TIMESTAMP
               )
               """;
    }

    @Override
    public String getDeleteStatement() {
        return "DELETE FROM person";
    }

    @Override
    public String getInsertStatement() {
        return """
               INSERT INTO person (id, name, modified)
               VALUES (?, ?, ?)
               """;
    }

    @Override
    public void setInsertStatement(PreparedStatement statement) throws SQLException {
        statement.setBigDecimal(1, id);
        statement.setString(2, name);
        statement.setTimestamp(3, modified);
    }

    @Override
    public void setFields(DataLine dataLine) {
        this.id = new BigDecimal(Integer.parseInt(dataLine.get(1)));
        this.name = dataLine.get(2);
        this.modified = Timestamp.valueOf(LocalDateTime.parse(dataLine.get(3)));
    }

}
