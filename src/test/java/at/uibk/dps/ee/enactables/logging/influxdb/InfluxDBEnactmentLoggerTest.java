package at.uibk.dps.ee.enactables.logging.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class InfluxDBEnactmentLoggerTest {

  public static String testPropertiesPath = "./src/test/resources/influxdb-test.properties";


  @Test public void testLogEnactment() {
    String id = "id";
    String type = "type";
    double executionTime = 1.12;
    boolean success = true;
    double inputComplexity = 0.9;
    Instant timestamp = Instant.now();

    InfluxDBClient clientMock = mock(InfluxDBClient.class);
    WriteApi writeApiMock = mock(WriteApi.class);
    InfluxDBEnactmentLogger influxDBLogger = new InfluxDBEnactmentLogger(clientMock);
    influxDBLogger.bucket = "testbucket";
    influxDBLogger.org = "testorg";

    InfluxDBEnactmentLogEntry entry =
        new InfluxDBEnactmentLogEntry(timestamp, id, type, executionTime, success, inputComplexity);

    when(clientMock.getWriteApi()).thenReturn(writeApiMock);
    influxDBLogger.logEnactment(entry);

    verify(writeApiMock).writeMeasurement("testbucket", "testorg", WritePrecision.NS, entry);
  }

  @Test public void testReadProperties() {
    InfluxDBClient clientMock = mock(InfluxDBClient.class);
    InfluxDBEnactmentLogger influxDBLogger = new InfluxDBEnactmentLogger(clientMock);

    assertNull(influxDBLogger.bucket);
    assertNull(influxDBLogger.org);
    assertNull(influxDBLogger.url);

    influxDBLogger.pathToPropertiesFile = testPropertiesPath;
    influxDBLogger.readProperties();

    assertEquals("testbucket", influxDBLogger.bucket);
    assertEquals("testorg", influxDBLogger.org);
    assertEquals("testurl", influxDBLogger.url);
  }
}
