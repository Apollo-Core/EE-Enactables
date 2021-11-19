package at.uibk.dps.ee.enactables;

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
  public InputMissingException(final String message) {
    super(message);
  }
}
