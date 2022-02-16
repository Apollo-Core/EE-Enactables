package at.uibk.dps.ee.enactables.demo;

/**
 * Static constants for the local enactables.
 * 
 * @author Fedor Smirnov
 *
 */
public class ConstantsLocal {

  /**
   * Defines the type strings for which local functions are available.
   * 
   * @author Fedor Smirnov
   *
   */
  public enum LocalCalculations {
    Addition, Subtraction, SumCollection, SplitArray
  }

  // Expected input and output names

  // addition
  public static final String inputAdditionFirst = "firstSummand";
  public static final String inputAdditionSecond = "secondSummand";
  public static final String outputAdditionResult = "sum";

  // substraction
  public static final String inputSubtractionMinuend = "minuend";
  public static final String inputSubtractionSubtrahend = "subtrahend";
  public static final String outputSubstractionResult = "difference";

  // sum aggregation
  public static final String inputSumCollection = "collectionToSum";
  public static final String outputSumCollection = "collectionSum";

  // split
  public static final String inputSplitArrayArray = "arrayToSplit";
  public static final String inputSplitArrayNumber = "splitNumber";
  public static final String outputSplitArray = "subArrays";
  
  // used by multiple functions
  public static final String inputWaitTime = "waitTimeIn";
}
