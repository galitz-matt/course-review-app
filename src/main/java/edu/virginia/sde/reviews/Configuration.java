package edu.virginia.sde.reviews;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Collectors;

public class Configuration {
    private final String configFilename = "config.json";
    private String databaseFilename;

    public String getDatabaseFilename() {
        if (databaseFilename == null) {
            parseJsonConfigFile();
        }
        return databaseFilename;
    }

    private void parseJsonConfigFile() {
        try (InputStream inputStream = Objects.requireNonNull(Configuration.class.getResourceAsStream(configFilename));
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            var jsonContent = bufferedReader.lines().collect(Collectors.joining("\n"));
            var jsonConfig = new JSONObject(jsonContent);
            databaseFilename = jsonConfig.get("database").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
