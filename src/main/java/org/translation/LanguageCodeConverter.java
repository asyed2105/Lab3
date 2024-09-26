package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    // Task: pick appropriate instance variables to store the data necessary for this class
    private Map<String, List<String>> languageCodetoName;
    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        languageCodetoName = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (String line : lines) {
                String[] parts = line.split("\t");  // Splitting by tab between names and code
                String[] names = parts[0].split(", ");  // Splitting multiple names by ", "

                // Adding the list of names to the map with the code as the key
                languageCodetoName.put(parts[1], List.of(names));
            }

        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // Task: update this code to use your instance variable to return the correct value
        List<String> language_names = languageCodetoName.get(code);
        if (language_names == null) {
            return "";
        }
        else{
            return String.join(", ", language_names);
        }
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // Task: update this code to use your instance variable to return the correct value
        List<String> language_codes = languageCodetoName.get(language);
        if (language_codes == null) {
            return "";
        }
        else{
            return String.join(", ", language_codes);
        }
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // Task: update this code to use your instance variable to return the correct value
        return languageCodetoName.size();
    }
}
