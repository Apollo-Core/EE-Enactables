package at.uibk.dps.ee.enactables.serverless;

/**
 * Static container containing the constants used during the enactment of
 * serverless functions.
 * 
 * @author Fedor Smirnov
 */
public final class ConstantsServerless {

  public static final int readWriteTimeoutSeconds = 30;
  
  // names for the additional attributes which are to be logged
  public static final String logAttrSlUrl = "url";
  

  /**
   * No constructor.
   */
  private ConstantsServerless() {}
}
