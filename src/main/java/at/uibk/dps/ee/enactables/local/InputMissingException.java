package at.uibk.dps.ee.enactables.local;

/**
 * Thrown when an input of a function executed by Apollo itself is not present.
 * 
 * @author Fedor Smirnov
 */
public class InputMissingException extends Exception{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Constructor
   * 
   * @param message failure message
   */
  public InputMissingException(String message) {
    super(message);
  }
}
