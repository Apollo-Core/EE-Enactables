package at.uibk.dps.ee.enactables.logging.influxdb;


import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The {@link InfluxDBEnactmentLogger} is used to log information about the enactment to the
 * time-series database InfluxDB.
 *
 * @author Markus Moosbrugger
 */
public class InfluxDBEnactmentLogger implements EnactmentLogger {

  // TODO should we hardcode the path to the properties file here
  protected String pathToPropertiesFile = "./logging/config/database/influxdb/influxdb.properties";
  protected final Logger logger = LoggerFactory.getLogger(InfluxDBEnactmentLogger.class);

  protected InfluxDBClient client;
  protected String bucket;
  protected String org;
  protected String url;
  private String token;


  /**
   * Default constructor. Reads the database configuration properties from the specified
   * properties file and creates an InfluxDB client.
   */
  public InfluxDBEnactmentLogger() {
    readProperties();
    this.client = InfluxDBClientFactory.create(this.url, this.token.toCharArray());
  }

  /**
   * Additional constructor which can be used to provide a client.
   * @param client an InfluxDB client
   */
  public InfluxDBEnactmentLogger(InfluxDBClient client){
    readProperties();
    this.client = client;
  }

  @Override public void logEnactment(EnactmentLogEntry entry) {
    /* TODO check if better to write point or use custom class
    Point point = Point.measurement("enactment").addTag("functionId", entry.getId())
        .addTag("functionType", entry.getType()).addField("executionTime",
        entry.getExecutionTime())
        .addField("success", entry.isSuccess()).time(entry.getTimestamp(), WritePrecision.NS);
     */

    InfluxDBEnactmentLogEntry influxDBEntry = new InfluxDBEnactmentLogEntry(entry);
    try (WriteApi writeApi = client.getWriteApi()) {
      writeApi.writeMeasurement(bucket, org, WritePrecision.NS, influxDBEntry);
    }
  }

  /**
   * Reads the needed properties from the specified properties file.
   */
  protected void readProperties() {
    try (InputStream input = new FileInputStream(pathToPropertiesFile)) {
      Properties properties = new Properties();
      properties.load(input);

      this.bucket = (String) properties.get("bucket");
      this.org = (String) properties.get("org");
      this.url = (String) properties.get("url");
      this.token = (String) properties.get("token");

    } catch (FileNotFoundException e) {
      logger.error("Properties file not found at given location: " + pathToPropertiesFile, e);
    } catch (IOException e) {
      logger.error(
          "IO Exception while reading properties file at given location: " + pathToPropertiesFile,
          e);
    }
  }
}
