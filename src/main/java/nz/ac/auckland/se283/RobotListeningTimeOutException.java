package nz.ac.auckland.se283;

public class RobotListeningTimeOutException extends RuntimeException {

  public RobotListeningTimeOutException() {
    super("The robot stopped listening! Please press the record button to record another command");
  }
}
