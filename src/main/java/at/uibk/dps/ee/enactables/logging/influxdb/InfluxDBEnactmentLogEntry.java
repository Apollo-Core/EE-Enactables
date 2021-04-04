package at.uibk.dps.ee.enactables.logging.influxdb;

import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;
import java.util.Objects;

/**
 * The {@link InfluxDBEnactmentLogEntry} is a POJO which contains all the required annotations
 * for writing an enactment entry to InfluxDB.
 *
 * @author Markus Moosbrugger
 */

@Measurement(name = "EnactmentEntry") public class InfluxDBEnactmentLogEntry {

  @Column(name = "functionId", tag = true) protected String id;

  @Column(name = "functionType", tag = true) protected String type;

  @Column(name = "time") protected Instant timestamp;

  @Column(name = "success") protected boolean success;

  @Column(name = "inputComplexity") protected double inputComplexity;

  @Column(name = "executionTime") protected double executionTime;


  /**
   * The constructor which takes an instance of the class {@link EnactmentLogEntry} and
   * creates a log entry for InfluxDB.
   *
   * @param entry the general enactment log entry
   */
  public InfluxDBEnactmentLogEntry(EnactmentLogEntry entry) {
    this(entry.getTimestamp(), entry.getId(), entry.getType(), entry.getExecutionTime(),
        entry.isSuccess(), entry.getInputComplexity());
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
  public InfluxDBEnactmentLogEntry(Instant timestamp, String id, String type, double executionTime,
      boolean success, double inputComplexity) {
    this.id = id;
    this.type = type;
    this.timestamp = timestamp;
    this.success = success;
    this.executionTime = executionTime;
    this.inputComplexity = inputComplexity;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    InfluxDBEnactmentLogEntry that = (InfluxDBEnactmentLogEntry) o;
    return id.equals(that.id) && type.equals(that.type) && timestamp.equals(that.timestamp);
  }

  @Override public int hashCode() {
    return Objects.hash(id, type, timestamp);
  }
}
