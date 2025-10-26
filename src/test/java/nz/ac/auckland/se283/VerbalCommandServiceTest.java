package nz.ac.auckland.se283;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

public class VerbalCommandServiceTest {

  private static final String EMPTY_STRING = "";
  private static final String BLANK_STRING_ONE_SPACE = " ";
  private static final String BLANK_STRING_THREE_SPACE = "   ";
  private static final String MISINTERPRETED_COMMAND = "abc";
  private static final String PASS = "pass";
  private static final String FAIL = "fail";
  private static final String ASK_AGAIN = "ask again";
  private static final String FORWARDS = "forwards"; // valid command

  private VerbalCommandService vcs;

  @BeforeEach
  void setUp() {
    this.vcs = new VerbalCommandService();
  }

  // The child can indicate when to start / stop giving verbal commands

  @Test
  void isVerbalCommandsOn_onStart_returnsFalse() {
    // Act
    boolean isVerbalCommandsOn = vcs.isVerbalCommandsOn();

    // Assert
    assertFalse(isVerbalCommandsOn);
  }

  @Test
  void turnVerbalCommandsOn_whenExecuted_setsVcTrue() {
    // Act
    vcs.turnVerbalCommandsOn();
    boolean isVerbalCommandsOn = vcs.isVerbalCommandsOn();

    // Assert
    assertTrue(isVerbalCommandsOn);
  }

  @Test
  void turnVerbalCommandsOff_whenExecuted_setsVcFalse() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act
    vcs.turnVerbalCommandsOff();
    boolean isVerbalCommandsOn = vcs.isVerbalCommandsOn();

    // Assert
    assertFalse(isVerbalCommandsOn);
  }

  // If the command is misinterpreted, robot will ask for clarification before executing

  @ParameterizedTest(name = "interpret_when{0}Message_returnsError")
  @ValueSource(strings = {EMPTY_STRING, BLANK_STRING_ONE_SPACE, BLANK_STRING_THREE_SPACE})
  void interpret_whenEmptyMessage_returnsError(String s) {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> vcs.interpret(s));
  }

  @Test
  void interpret_whenNullMessage_returnsError() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> vcs.interpret(null));
  }

  @Test
  void interpret_whenCommandMisinterpreted_callsClarify() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();

    // Act
    vcs.interpret(MISINTERPRETED_COMMAND);

    // Assert
    Mockito.verify(robot).clarify(MISINTERPRETED_COMMAND);
  }

  @Test
  void interpret_whenCommandMisinterpreted_doesNotCallExecute() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();

    // Act
    vcs.interpret(MISINTERPRETED_COMMAND);

    // Assert
    Mockito.verify(robot, never()).execute(anyString());
  }

  @Test
  void interpret_whenVcOff_returnsError() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> vcs.interpret(MISINTERPRETED_COMMAND));
  }

  @Test
  void interpret_whenCommandMisinterpreted_returnsAskAgain() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();
    Mockito.when(robot.clarify(MISINTERPRETED_COMMAND)).thenReturn(false);

    // Act
    String result = vcs.interpret(MISINTERPRETED_COMMAND);

    // Assert
    assertEquals(ASK_AGAIN, result);
  }

  // Commands should be recognised and executed by robot within 3 seconds to maintain child
  // engagement

  @Test
  void checkTimeListening_2_9s_returnsOk() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act
    boolean result = vcs.checkTimeListening(2.9);

    // Assert
    assertTrue(result);
  }

  @Test
  void checkTimeListening_3s_returnsError() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act and Assert
    assertThrows(RobotListeningTimeOutException.class, () -> vcs.checkTimeListening(3.0));
  }

  @Test
  void checkTimeListening_3_1s_returnsError() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act and Assert
    assertThrows(RobotListeningTimeOutException.class, () -> vcs.checkTimeListening(3.1));
  }

  @Test
  void checkTimeListening_negative_0_1s_returnsError() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act and Assert
    assertThrows(RobotListeningTimeOutException.class, () -> vcs.checkTimeListening(-0.1));
  }

  @Test
  void checkTimeListening_0s_returnsOk() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act
    boolean result = vcs.checkTimeListening(0);

    // Assert
    assertTrue(result);
  }

  @Test
  void checkTimeListening_0_1s_returnsOk() {
    // Arrange
    vcs.turnVerbalCommandsOn();

    // Act
    boolean result = vcs.checkTimeListening(0.1);

    // Assert
    assertTrue(result);
  }

  // Confirms if the task is executed successfully

  @Test
  void interpret_whenCommandInterpretedCorrectly_returnsPass() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();
    Mockito.when(robot.execute(FORWARDS)).thenReturn(true);

    // Act
    String result = vcs.interpret(FORWARDS);

    // Assert
    assertEquals(PASS, result);
  }

  @Test
  void interpret_whenCommandFailsToExecute_returnsFail() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();
    Mockito.when(robot.execute(FORWARDS)).thenReturn(false);

    // Act
    String result = vcs.interpret(FORWARDS);

    // Assert
    assertEquals(FAIL, result);
  }

  // New unit tests to increase branch coverage:

  @Test
  void interpret_whenMisinterpretedCommandClarifiedCorrectly_returnsFail() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();
    Mockito.when(robot.clarify(MISINTERPRETED_COMMAND)).thenReturn(true);
    Mockito.when(robot.execute(MISINTERPRETED_COMMAND)).thenReturn(false);

    // Act
    String result = vcs.interpret(MISINTERPRETED_COMMAND);

    // Assert
    assertEquals(FAIL, result);
  }

  @Test
  void interpret_whenMisinterpretedCommandClarifiedCorrectly_returnsPass() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService vcs = new VerbalCommandService(robot);
    vcs.turnVerbalCommandsOn();
    Mockito.when(robot.clarify(MISINTERPRETED_COMMAND)).thenReturn(true);
    Mockito.when(robot.execute(MISINTERPRETED_COMMAND)).thenReturn(true);

    // Act
    String result = vcs.interpret(MISINTERPRETED_COMMAND);

    // Assert
    assertEquals(PASS, result);
  }

  @Test
  void interpret_whenCalled_incrementsTimesInterpreted() {
    // Arrange
    Robot robot = Mockito.mock(Robot.class);
    VerbalCommandService svc = new VerbalCommandService(robot);
    svc.turnVerbalCommandsOn();
    Mockito.when(robot.execute(FORWARDS)).thenReturn(true);

    // Act
    int countBefore = svc.getTimesInterpreted();
    svc.interpret(FORWARDS);

    // Assert
    assertEquals(countBefore + 1, svc.getTimesInterpreted());
  }
}
