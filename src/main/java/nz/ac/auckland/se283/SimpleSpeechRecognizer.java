package nz.ac.auckland.se283;

public class SimpleSpeechRecognizer implements SpeechRecognizer {

  private static final String DEFAULT_RESPONSE_IN_ENGLISH = "hello";
  private static final String MAORI = "Maori";

  @Override
  public String recognize(String language) {
    if (MAORI.equals(language)) {
      return "kia ora";
    }
    return DEFAULT_RESPONSE_IN_ENGLISH; // default English
  }
}
