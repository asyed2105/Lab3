package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // Task: pick appropriate instance variables for this class
    private Map<String, Map<String,String>> TranslationInfo; // key holds country code and its value is language codes and their translations
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString); // makes into JSON object -> allows us to iterate through each element
            TranslationInfo = new HashMap<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i); // gets us the ith element from jsonArray
                String code = jsonObject.getString("alpha2"); // country code for map key

                Map<String, String> translation = new HashMap<>(); // map for translation info coming from jsonObject
                for (String countryKey: jsonObject.keySet()) {
                    // we don't want id/alpha2/alpha3 in our map of translation details --> unnecessary
                    // therefore, condition to filter it out:
                    if(!countryKey.equals("id") && !countryKey.equals("alpha2") && !countryKey.equals("alpha3")){
                        translation.put(countryKey, jsonObject.getString(countryKey));
                    }
                }
                TranslationInfo.put(code, translation);
            }

            // Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        Map<String, String> translation = TranslationInfo.get(country);
        if (translation == null) {
            return new ArrayList<>(); // returns empty array
        }
        else{
            return new ArrayList<>(translation.keySet());
        }
    }

    @Override
    public List<String> getCountries() {
        // Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(TranslationInfo.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // Task: complete this method using your instance variables as needed
        Map<String, String> translation = TranslationInfo.get(country);
        if (translation != null && translation.containsKey(language)) {
            return translation.get(language);
        }
        else{
            return null;
        }
    }
}
