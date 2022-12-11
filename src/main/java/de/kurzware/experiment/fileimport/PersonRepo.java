package de.kurzware.experiment.fileimport;

import java.sql.SQLException;

public class PersonRepo extends MyRepo<PersonTable> {

    public PersonRepo() throws SQLException {
        super(PersonTable.class);
    }

}
