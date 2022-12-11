package de.kurzware.experiment.fileimport;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class MyRepo<T extends MyTables> {

    private final MyDatasource datasource;
    private final Connection connection;
    private final Class<T> tableClass;

    public MyRepo(Class<T> tableClass) throws SQLException {
        this.datasource = new MyDatasource();
        this.datasource.openConnection();
        this.connection = datasource.getConnection();
        this.tableClass = tableClass;
    }

    private T getTableInstance() {
        final Constructor<T> constructor;
        final T any;

        try {
            constructor = tableClass.getConstructor();
            any = constructor.newInstance();
        } catch(Exception ex) {
            // NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
            log.error("Instantiation of table class failed", ex);
            throw new RuntimeException(ex);
        }

        return any;
    }

    public void createDDL() throws SQLException {
        final T any = getTableInstance();
        final String statementString = any.getCreateStatement();

        executeSQL(statementString);
    }

    public void deleteAll() throws SQLException {
        final T any = getTableInstance();
        final String statementString = any.getDeleteStatement();

        executeSQL(statementString);
    }

    public void saveAll(List<T> entities) throws SQLException {
        final T any = getTableInstance();
        final String statementString = any.getInsertStatement();

        try (PreparedStatement statement = connection.prepareStatement(statementString)) {
            for (T entity : entities) {
                entity.setInsertStatement(statement);
                statement.addBatch();
            }

            int[] resultExecuteBatch = statement.executeBatch();
            connection.commit();

            for (int i = 0; i < resultExecuteBatch.length; i++)
                if (resultExecuteBatch[i] == PreparedStatement.EXECUTE_FAILED) {
                    log.warn("Execution of batch statement failed");
                    throw new SQLException("Execution of batch statement failed");
                }
            log.info(resultExecuteBatch.length + " rows inserted");
        } catch (SQLException prepstmtex) {
            log.warn("Prepared SQL statement failed: " + statementString);
            throw prepstmtex;
        }
    }

    private void executeSQL(String statementString) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeUpdate(statementString);
            connection.commit();
        } catch (SQLException prepstmtex) {
            log.warn("Execution of SQL statement failed: " + statementString);
            throw prepstmtex;
        }
    }
}
