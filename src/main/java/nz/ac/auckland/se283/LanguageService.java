package nz.ac.auckland.se283;

import java.util.HashMap;
import java.util.Map;

public class LanguageService {

  public static final String ENGLISH = "English";
  public static final String MAORI = "Maori";

  private static final Map<String, Map<String, String>> messages = createMessages();

  private String language = ENGLISH; // Default
  private SpeechRecognizer recognizer;

  private static Map<String, Map<String, String>> createMessages() {
    // Create a new hashmap instance where the key is the type of message
    // and the value is another map where the key is the language
    // and the value is the message itself in the respective language
    Map<String, Map<String, String>> messages = new HashMap<>();

    Map<String, String> instruction = new HashMap<>();
    instruction.put(ENGLISH, "Follow the instructions");
    instruction.put(MAORI, "Whaia nga tohutohu");
    messages.put("INSTRUCTION", instruction);

    Map<String, String> feedbackOk = new HashMap<>();
    feedbackOk.put(ENGLISH, "Great job!");
    feedbackOk.put(MAORI, "Ka pai!");
    messages.put("FEEDBACK_OK", feedbackOk);

    Map<String, String> feedbackError = new HashMap<>();
    feedbackError.put(ENGLISH, "Try again.");
    feedbackError.put(MAORI, "Ngana ano.");
    messages.put("FEEDBACK_ERROR", feedbackError);

    Map<String, String> mainMenu = new HashMap<>();
    mainMenu.put(ENGLISH, "Main Menu");
    mainMenu.put(MAORI, "Tahua Matua");
    messages.put("MAIN_MENU", mainMenu);

    // Returns the map
    return messages;
  }

  public LanguageService(SpeechRecognizer recognizer) {
    this.recognizer = recognizer;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    if (language == null || language.isEmpty() || language.isBlank()) {
      throw new IllegalArgumentException();
    }
    if (!language.equals(ENGLISH) && !language.equals(MAORI)) {
      throw new IllegalArgumentException();
    }
    this.language = language;
  }

  public String recognizeLanguage() {
    return recognizer.recognize(language);
  }

  public String translate(String key) {
    Map<String, String> translationsForKey = messages.get(key);
    String text = translationsForKey.get(language);
    return text;
  }
}
