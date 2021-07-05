package at.uibk.dps.ee.enactables.logging;


import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;
import java.util.Objects;
import java.util.Set;
import at.uibk.dps.ee.core.function.EnactmentFunction;

/**
 * The {@link EnactmentLogEntry} contains the relevant information about an
 * enactment which may be logged.
 *
 * @author Markus Moosbrugger
 */
public class EnactmentLogEntry {

  protected String typeId;
  protected String enactmentMode;
  protected String implementationId;
  protected Set<SimpleEntry<String, String>> additionalAttributes;
  protected Instant timestamp;
  protected boolean success;
  protected double inputComplexity;
  protected double executionTime;

  /**
   * Default constructor containing all attributes.
   *
   * @param timestamp        the timestamp of the enactment
   * @param typeId           the type id
   * @param enactmentMode    the enactment mode
   * @param implementationId the implementation id
   * @param executionTime    the execution time
   * @param success          the success status
   * @param inputComplexity  the complexity of the input values
   */
  public EnactmentLogEntry(final Instant timestamp, final String typeId, final String enactmentMode,
      final String implementationId, final Set<SimpleEntry<String, String>> additionalAttributes,
      final double executionTime, final boolean success, final double inputComplexity) {
    this.typeId = typeId;
    this.enactmentMode = enactmentMode;
    this.implementationId = implementationId;
    this.additionalAttributes = additionalAttributes;
    this.executionTime = executionTime;
    this.timestamp = timestamp;
    this.success = success;
    this.inputComplexity = inputComplexity;
  }

  /**
   * Additional convenience constructor to provide the enactment function
   * directly.
   *
   * @param timestamp         the timestamp of the enactment
   * @param enactmentFunction the enactment function
   * @param executionTime     the execution time
   * @param success           the success status
   * @param inputComplexity   the complexity of the input values
   */
  public EnactmentLogEntry(final Instant timestamp, final EnactmentFunction enactmentFunction,
      final double executionTime, final boolean success, final double inputComplexity) {
    this(timestamp, enactmentFunction.getTypeId(), enactmentFunction.getEnactmentMode(),
        enactmentFunction.getImplementationId(), enactmentFunction.getAdditionalAttributes(),
        executionTime, success, inputComplexity);
  }

  public String getImplementationId() {
    return implementationId;
  }

  public void setImplementationId(final String implementationId) {
    this.implementationId = implementationId;
  }

  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(final String typeId) {
    this.typeId = typeId;
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

  public String getEnactmentMode() {
    return enactmentMode;
  }

  public void setEnactmentMode(final String enactmentMode) {
    this.enactmentMode = enactmentMode;
  }

  public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
    return additionalAttributes;
  }

  public void setAdditionalAttributes(final Set<SimpleEntry<String, String>> additionalAttributes) {
    this.additionalAttributes = additionalAttributes;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final EnactmentLogEntry that = (EnactmentLogEntry) obj;
    return success == that.success && Double.compare(that.executionTime, executionTime) == 0
        && typeId.equals(that.typeId) && enactmentMode.equals(that.enactmentMode)
        && implementationId.equals(that.implementationId) && timestamp.equals(that.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeId, enactmentMode, implementationId, timestamp, success, executionTime);
  }
}
