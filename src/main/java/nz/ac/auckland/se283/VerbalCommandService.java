package nz.ac.auckland.se283;

import java.util.Set;

public class VerbalCommandService {

  private static final Set<String> commands = Set.of("forwards", "backwards", "left", "right");
  private static final String PASS = "pass";
  private static final String FAIL = "fail";
  private static final String ASK_AGAIN = "ask again";

  private Robot robot = null;
  private boolean isVerbalCommandsOn;

  public VerbalCommandService() {
    this.isVerbalCommandsOn = false;
  }

  public VerbalCommandService(Robot robot) {
    this.robot = robot;
    this.isVerbalCommandsOn = false;
  }

  public boolean isVerbalCommandsOn() {
    return isVerbalCommandsOn;
  }

  public void turnVerbalCommandsOn() {
    this.isVerbalCommandsOn = true;
  }

  public void turnVerbalCommandsOff() {
    this.isVerbalCommandsOn = false;
  }

  public String interpret(String commandToInterpret) {
    // If null or erroneous input throw exception
    if (commandToInterpret == null
        || commandToInterpret.isEmpty()
        || commandToInterpret.isBlank()) {
      throw new IllegalArgumentException();
    }

    boolean wasExecutedSuccessfully;

    // Check if the command is valid
    if (isVerbalCommandsOn) {
      if (commands.contains(commandToInterpret)) {
        wasExecutedSuccessfully = robot.execute(commandToInterpret);
        if (wasExecutedSuccessfully) {
          return PASS;
        } else {
          return FAIL;
        }
      }
    } else {
      throw new IllegalArgumentException();
    }

    // If not sure, clarify with the user
    boolean isCommandCorrect = robot.clarify(commandToInterpret);
    if (isCommandCorrect) {
      wasExecutedSuccessfully = robot.execute(commandToInterpret);
      if (wasExecutedSuccessfully) {
        return PASS;
      } else {
        return FAIL;
      }
    }

    return ASK_AGAIN;
  }

  boolean checkTimeListening(double i) {
    if (i >= 3 || i < 0) {
      throw new RobotListeningTimeOutException();
    }

    return true;
  }
}
