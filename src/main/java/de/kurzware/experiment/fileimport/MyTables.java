package de.kurzware.experiment.fileimport;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface MyTables {
    String getCreateStatement();

    String getDeleteStatement();

    String getInsertStatement();

    void setInsertStatement(PreparedStatement statement) throws SQLException;

    void setFields(DataLine dataLine);
}
