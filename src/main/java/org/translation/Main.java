package org.translation;

import java.util.*;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator); // country name returned meaning this works
            System.out.println("TEST0");
            System.out.println(country);
            String quit = "quit";
            if (country.equals(quit)) {
                break;
            }

            CountryCodeConverter codeConverter = new CountryCodeConverter();
            String countryCodes = codeConverter.fromCountry(country);
            System.out.println("TEST1");
            System.out.println(countryCodes);

            String language = promptForLanguage(translator, countryCodes);
            System.out.println("TEST2");
            System.out.println(language);


            LanguageCodeConverter langConverter = new LanguageCodeConverter();
            String lanCodes = langConverter.fromLanguage(language);
            System.out.println("TEST3 ");
            System.out.println(lanCodes);

            System.out.println(language);
            if (language.equals(quit)) {
                break;
            }
            System.out.println(country + " in " + language + " is " + translator.translate(countryCodes, lanCodes));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if ("quit".equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        Map<String,String> codeToName = new HashMap<>();

        CountryCodeConverter codeConverter = new CountryCodeConverter();
        for (String country : countries) {
            String countryName = codeConverter.fromCountryCode(country);
            codeToName.put(country, countryName);
        }

        List<String> countryNames = new ArrayList<>(codeToName.values());
        Collections.sort(countryNames);
        for (String countryName : countryNames) {
            System.out.println(countryName);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        List<String> langCodes = translator.getCountryLanguages(country); // Get language codes for the country


        Map<String, String> nameToCode = new HashMap<>();
        LanguageCodeConverter langConverter = new LanguageCodeConverter();

        for (String code : langCodes) {
            String langNames = langConverter.fromLanguageCode(code);
            nameToCode.put(langNames, code);
        }

        // Sort language names for display
        List<String> languageNames = new ArrayList<>(nameToCode.keySet());
        Collections.sort(languageNames);

        // Display the list of languages
        for (String name : languageNames) {
            System.out.println(name);
        }

        // Prompt the user to select a language
        System.out.println("Select a language from the list above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();  // Get user input
    }
}
