package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LanguageServiceTest {

  private static final String EMPTY_STRING = "";
  private static final String BLANK_STRING_ONE_SPACE = " ";
  public static final String ENGLISH = "English";
  public static final String MAORI = "Maori";

  private LanguageService ls;
  private SpeechRecognizer recognizer;

  @BeforeEach
  void setUp() {
    this.recognizer = Mockito.mock(SpeechRecognizer.class);
    this.ls = new LanguageService(recognizer);
  }

  // The app and robot will support different language options (mainly English or Maori)

  @Test
  void setLanguage_whenNull_returnsError() {
    // Arrange

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> ls.setLanguage(null));
  }

  @Test
  void setLanguage_whenEmpty_returnsError() {
    // Arrange

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> ls.setLanguage(EMPTY_STRING));
  }

  @Test
  void setLanguage_whenBlank_returnsError() {
    // Arrange

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> ls.setLanguage(BLANK_STRING_ONE_SPACE));
  }

  @Test
  void languageService_onStart_setToEnglish() {
    // Arrange

    // Act

    // Assert
    assertEquals(ENGLISH, ls.getLanguage());
  }

  @Test
  void setLanguage_whenEnglish_setsLanguageToEnglish() {
    // Arrange
    ls.setLanguage(MAORI);

    // Act
    ls.setLanguage(ENGLISH);

    // Assert
    assertEquals(ENGLISH, ls.getLanguage());
  }

  @Test
  void setLanguage_whenMaori_setsLanguageToMaori() {
    // Arrange

    // Act
    ls.setLanguage(MAORI);

    // Assert
    assertEquals(MAORI, ls.getLanguage());
  }

  // All instructions, feedback, menus are provided in the selected language

  @Test
  void translate_whenKeyInEnglish_returnsEnglishText() {
    // Arrange
    ls.setLanguage(ENGLISH);

    // Act
    String text = ls.translate("INSTRUCTION");

    // Assert
    assertEquals("Follow the instructions", text);
  }

  @Test
  void translate_whenKeyInMaori_returnsMaoriText() {
    // Arrange
    ls.setLanguage(MAORI);

    // Act
    String text = ls.translate("INSTRUCTION");

    // Assert
    assertEquals("Whaia nga tohutohu", text);
  }

  // Languages are easily configurable at any point

  @Test
  void translate_afterChangingLanguage_isValid() {
    // Arrange
    ls.setLanguage(ENGLISH);
    String englishTitle = ls.translate("MAIN_MENU");

    // Act
    ls.setLanguage(MAORI);
    String maoriTitle = ls.translate("MAIN_MENU");

    // Assert
    assertEquals("Main Menu", englishTitle);
    assertEquals("Tahua Matua", maoriTitle);
  }

  // Voice input in other languages are recognized and understood by the LLM

  @Test
  void recognizeLanguage_whenEnglish_callsRecognizerWithEnglish() {
    // Arrange
    ls.setLanguage(ENGLISH);
    Mockito.when(recognizer.recognize(ENGLISH)).thenReturn("hello");

    // Act
    String result = ls.recognizeLanguage();

    // Assert
    assertEquals("hello", result);
    Mockito.verify(recognizer).recognize(ENGLISH);
  }

  @Test
  void recognizeLanguage_whenMaori_callsRecognizerWithMaori() {
    // Arrange
    ls.setLanguage(MAORI);
    Mockito.when(recognizer.recognize(MAORI)).thenReturn("kia ora");

    // Act
    String result = ls.recognizeLanguage();

    // Assert
    assertEquals("kia ora", result);
    Mockito.verify(recognizer).recognize(MAORI);
  }

  @Test
  void recognizeLanguage_afterChangingLanguage_callsRecognizerWithMaori() {
    // Arrange
    ls.setLanguage(ENGLISH);
    ls.setLanguage(MAORI);
    Mockito.when(recognizer.recognize(MAORI)).thenReturn("kia ora");

    // Act
    String result = ls.recognizeLanguage();

    // Assert
    assertEquals("kia ora", result);
    Mockito.verify(recognizer).recognize(MAORI);
  }
}
