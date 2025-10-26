package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LanguageRecognitionIntegrationTest {

  private static final String MAORI_HELLO = "kia ora";

  @Test
  void recognizeLanguage_whenMaoriHello_returnsKiaOra() {
    // Arrange
    SimpleSpeechRecognizer ssr = new SimpleSpeechRecognizer();
    LanguageService ls = new LanguageService(ssr);
    ls.setLanguage(LanguageService.MAORI);

    // Act
    String responseHeard = ls.recognizeLanguage();

    // Assert
    assertEquals(MAORI_HELLO, responseHeard);
  }
}
