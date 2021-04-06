package at.uibk.dps.ee.enactables.logging;

import java.time.Instant;
import java.util.Objects;

/**
 * The {@link EnactmentLogEntry} contains the relevant information about an enactment which may
 * be logged.
 *
 * @author Markus Moosbrugger
 */
public class EnactmentLogEntry {

  protected String id;
  protected String type;
  protected Instant timestamp;
  protected boolean success;
  protected double inputComplexity;
  protected double executionTime;

  /**
   * Constructor containing only function id, function type and the execution time.
   *
   * @param id            the function id
   * @param type          the function type
   * @param executionTime the execution time
   */
  public EnactmentLogEntry(final String id, final String type, final double executionTime) {
    this.id = id;
    this.type = type;
    this.executionTime = executionTime;
    this.timestamp = Instant.now();
  }

  /**
   * Additional constructor containing all object attributes.
   *
   * @param timestamp       the timestamp of the enactment
   * @param id              the function id
   * @param type            the function type
   * @param executionTime   the execution time
   * @param success         the success status
   * @param inputComplexity the complexity of the input values
   */
  public EnactmentLogEntry(final Instant timestamp, final String id, final String type,
      final double executionTime, final boolean success, final double inputComplexity) {
    this(id, type, executionTime);
    this.timestamp = timestamp;
    this.success = success;
    this.inputComplexity = inputComplexity;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
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

  @Override public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    final EnactmentLogEntry that = (EnactmentLogEntry) obj;
    return id.equals(that.id) && type.equals(that.type) && timestamp.equals(that.timestamp);
  }

  @Override public int hashCode() {
    return Objects.hash(id, type, timestamp);
  }
}
