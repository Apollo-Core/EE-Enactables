package at.uibk.dps.ee.enactables.logging.influxdb;

import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.time.Instant;

/**
 * The {@link InfluxDBEnactmentLogEntry} is a POJO which contains all the required annotations
 * for writing an enactment entry to InfluxDB.
 *
 * @author Markus Moosbrugger
 */

@Measurement(name = "EnactmentEntry") public class InfluxDBEnactmentLogEntry
    extends EnactmentLogEntry {

  @Column(name = "functionId", tag = true) protected String id;

  @Column(name = "functionType", tag = true) protected String type;

  @Column(name = "time") protected Instant timestamp;

  @Column(name = "success") protected boolean success;

  @Column(name = "inputComplexity") protected double inputComplexity;

  @Column(name = "executionTime") protected double executionTime;


  /**
   * The constructor which takes an instance of the parent class {@link EnactmentLogEntry} and
   * creates a more specific entry for InfluxDB.
   *
   * @param entry the general enactment log entry
   */
  public InfluxDBEnactmentLogEntry(EnactmentLogEntry entry) {
    super(entry.getTimestamp(), entry.getId(), entry.getType(), entry.getExecutionTime(),
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
    super(timestamp, id, type, executionTime, success, inputComplexity);
  }


}
