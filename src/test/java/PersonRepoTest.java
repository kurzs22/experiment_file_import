import de.kurzware.experiment.fileimport.DataLine;
import de.kurzware.experiment.fileimport.PersonRepo;
import de.kurzware.experiment.fileimport.PersonTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonRepoTest {
    static PersonRepo personRepo;

    @BeforeAll
    static void createDDL() throws SQLException {
        personRepo = new PersonRepo();
        personRepo.createDDL();
    }

    @Test
    void insert() throws SQLException {
        List<PersonTable> personTableList = new ArrayList<>();
        PersonTable personTable = new PersonTable();

        personTable.setFields(new DataLine('|', "D|123|Stefan|2022-12-10T11:38:01"));
        personTableList.add(new PersonTable(personTable));
        personTable.setFields(new DataLine('|', "D|1234567890|Uhu|2022-12-10T11:38:02"));
        personTableList.add(new PersonTable(personTable));
        personRepo.saveAll(personTableList);
    }

    @Test
    void delete() throws SQLException {
        personRepo.deleteAll();
    }
}
