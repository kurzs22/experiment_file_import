package de.kurzware.experiment.fileimport;

import java.util.ArrayList;
import java.util.List;

public class DataLine {
    private Character fieldDelimiter = ';';
    private String fieldDelimiterRegex = "[;]"; // to use delimiter safely in split method


    private ArrayList<String> fields;

    public DataLine(Character fieldDelimiter, String line) {
        this.fieldDelimiter = fieldDelimiter;
        this.fieldDelimiterRegex = "[" + fieldDelimiter + "]"; // escape delimiter with brackets in regex.
        // This works for special characters like '|' as well as for normal characters like ';'
        setFields(line);
    }

    public void setFields(String line) {
        //-1 Delimiter saves empty entries!!
        if(line != null) {
            this.fields = new ArrayList<>(List.of(line.split(fieldDelimiterRegex, -1)));
        }
    }

    public List<String> getFields() {
        return fields;
    }

    public String get(int index) {
        return fields.get(index);
    }

    public boolean startsWith(String firstElement) {
        return fields.get(0).equals(firstElement);
    }

    public int size() {
        return fields.size();
    }

    public boolean isWellFormed(int numberOfFields, String firstField) {
        return fields != null &&
                fields.size() == numberOfFields &&
                startsWith(firstField);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String f : fields) {
            sb.append(f).append(fieldDelimiter);
        }

        return sb.substring(0, sb.length() - 1 );
    }

}
