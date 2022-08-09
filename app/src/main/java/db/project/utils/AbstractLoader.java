package db.project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class AbstractLoader {

    private final String csvPath;
    private final Character fieldDelimiter;

    public AbstractLoader(String csvPath, char fieldDelimiter) {
        this.csvPath = csvPath;
        this.fieldDelimiter = fieldDelimiter;
    }

    protected Collection<List<String>> load() throws IOException {
        Collection<List<String>> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            String line = reader.readLine();
            while (line != null) {
                result.add(Arrays.asList(line.split("[" + fieldDelimiter.toString() + "]")));
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw e;
        }
        return result;
    }

}
