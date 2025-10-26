package nz.ac.auckland.se283;

public interface Robot {

  // Returns true if command was intentional false otherwise
  boolean clarify(String commandToClarify);

  // Returns true if command was executed successfully false otherwise
  boolean execute(String commandToInterpret);
}
