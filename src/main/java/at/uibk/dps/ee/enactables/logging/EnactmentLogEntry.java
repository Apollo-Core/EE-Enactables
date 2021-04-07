package at.uibk.dps.ee.enactables.logging;

import java.time.Instant;
import java.util.Objects;

/**
 * The {@link EnactmentLogEntry} contains the relevant information about an enactment which may be
 * logged.
 *
 * @author Markus Moosbrugger
 */
public class EnactmentLogEntry {

  protected String functionId;
  protected String functionType;
  protected Instant timestamp;
  protected boolean success;
  protected double inputComplexity;
  protected double executionTime;

  /**
   * Constructor containing only function id, function type and the execution time.
   *
   * @param functionId    the function id
   * @param functionType  the function type
   * @param executionTime the execution time
   */
  public EnactmentLogEntry(final String functionId, final String functionType,
      final double executionTime) {
    this.functionId = functionId;
    this.functionType = functionType;
    this.executionTime = executionTime;
    this.timestamp = Instant.now();
  }

  /**
   * Additional constructor containing all object attributes.
   *
   * @param timestamp       the timestamp of the enactment
   * @param functionId      the function id
   * @param functionType    the function type
   * @param executionTime   the execution time
   * @param success         the success status
   * @param inputComplexity the complexity of the input values
   */
  public EnactmentLogEntry(final Instant timestamp, final String functionId,
      final String functionType, final double executionTime, final boolean success,
      final double inputComplexity) {
    this(functionId, functionType, executionTime);
    this.timestamp = timestamp;
    this.success = success;
    this.inputComplexity = inputComplexity;
  }

  public String getFunctionId() {
    return functionId;
  }

  public void setFunctionId(final String functionId) {
    this.functionId = functionId;
  }

  public String getFunctionType() {
    return functionType;
  }

  public void setFunctionType(final String functionType) {
    this.functionType = functionType;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(final Instant timestamp) {
    this.timestamp = timestamp;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(final boolean success) {
    this.success = success;
  }

  public double getInputComplexity() {
    return inputComplexity;
  }

  public void setInputComplexity(final double inputComplexity) {
    this.inputComplexity = inputComplexity;
  }

  public double getExecutionTime() {
    return executionTime;
  }

  public void setExecutionTime(final double executionTime) {
    this.executionTime = executionTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final EnactmentLogEntry that = (EnactmentLogEntry) obj;
    return functionId.equals(that.functionId) && functionType.equals(that.functionType) && timestamp
        .equals(that.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(functionId, functionType, timestamp);
  }
}
