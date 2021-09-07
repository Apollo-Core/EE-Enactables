package at.uibk.dps.ee.enactables.logging;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.EnactmentMode;
import io.vertx.core.Future;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnactmentLogEntryTest {

  protected static class MockFunction implements EnactmentFunction {
    @Override
    public Future<JsonObject> processInput(JsonObject input) {
      return Future.succeededFuture();
    }

    @Override
    public String getTypeId() {
      return "typeId2";
    }

    @Override
    public String getEnactmentMode() {
      return EnactmentMode.Serverless.name();
    }

    @Override
    public String getImplementationId() {
      return "implementationId2";
    }

    @Override
    public String getFunctionId() {
      return "task";
    }

    @Override
    public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
      HashSet<SimpleEntry<String, String>> attributes = new HashSet<>();
      attributes.add(new SimpleEntry<>("key1", "value1"));
      attributes.add(new SimpleEntry<>("key2", "value2"));

      return attributes;
    }
  }

  @Test
  public void testFirstConstructor() {
    String typeId = "type";
    String enactmentMode = "mode";
    String implementationId = "id";

    double executionTime = 110.1;
    double inputComplexity = 0;
    boolean success = false;
    Instant timestamp = Instant.now();

    HashSet<SimpleEntry<String, String>> attributes = new HashSet<>();
    attributes.add(new SimpleEntry<>("key1", "value1"));
    attributes.add(new SimpleEntry<>("key2", "value2"));

    EnactmentLogEntry entry = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, success, inputComplexity);

    assertEquals(typeId, entry.getTypeId());
    assertEquals(enactmentMode, entry.getEnactmentMode());
    assertEquals(implementationId, entry.getImplementationId());
    assertEquals(executionTime, entry.getExecutionTime(), 0.001);
    assertNotNull(entry.getTimestamp());
    assertEquals(0.0, entry.getInputComplexity(), 0.001);
    assertEquals(success, entry.isSuccess());
    assertTrue(entry.getAdditionalAttributes().contains(new SimpleEntry<>("key1", "value1")));
    assertTrue(entry.getAdditionalAttributes().contains(new SimpleEntry<>("key2", "value2")));
    assertFalse(entry.getAdditionalAttributes().contains(new SimpleEntry<>("key3", "value2")));
  }

  @Test
  public void testSecondConstructor() {
    double executionTime2 = 90.01;
    Instant timestamp2 = Instant.now();
    boolean success2 = true;
    double inputComplexity2 = 0.8;

    MockFunction function = new MockFunction();
    EnactmentLogEntry entry =
        new EnactmentLogEntry(timestamp2, function, executionTime2, success2, inputComplexity2);

    assertEquals(timestamp2, entry.getTimestamp());
    assertEquals("typeId2", entry.getTypeId());
    assertEquals(EnactmentMode.Serverless.name(), entry.getEnactmentMode());
    assertEquals("implementationId2", entry.getImplementationId());
    assertEquals(executionTime2, entry.getExecutionTime(), 0.001);
    assertEquals(inputComplexity2, entry.getInputComplexity(), 0.001);
    assertEquals(success2, entry.isSuccess());
    assertTrue(entry.getAdditionalAttributes().contains(new SimpleEntry<>("key1", "value1")));
    assertTrue(entry.getAdditionalAttributes().contains(new SimpleEntry<>("key2", "value2")));
    assertFalse(entry.getAdditionalAttributes().contains(new SimpleEntry<>("key3", "value2")));
  }

  @Test
  public void testEquals() {
    String typeId = "type";
    String enactmentMode = "mode";
    String implementationId = "id";
    Set<SimpleEntry<String, String>> attributes = new HashSet<>();
    double executionTime = 9.99;
    Instant timestamp = Instant.now();

    EnactmentLogEntry entry1 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);

    EnactmentLogEntry entry2 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);
    EnactmentLogEntry entry3 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);
    EnactmentLogEntry entry4 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);
    EnactmentLogEntry entry5 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);
    EnactmentLogEntry entry6 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);
    EnactmentLogEntry entry7 = new EnactmentLogEntry(timestamp, typeId, enactmentMode,
        implementationId, attributes, executionTime, true, 0.2);

    assertEquals(entry1, entry1);
    assertNotEquals(entry1, "not_a_log_entry");

    entry2.setInputComplexity(0.8);
    assertEquals(entry1.hashCode(), entry2.hashCode());
    assertEquals(entry1, entry2);

    entry2.setTimestamp(Instant.now());
    assertNotEquals(entry1.hashCode(), entry2.hashCode());
    assertNotEquals(entry1, entry2);

    assertEquals(entry1, entry3);
    entry3.setEnactmentMode("otherMode");
    assertNotEquals(entry1, entry3);

    assertEquals(entry1, entry4);
    entry4.setSuccess(false);
    assertNotEquals(entry1, entry4);

    assertEquals(entry1, entry5);
    entry5.setExecutionTime(0.4);
    assertNotEquals(entry1, entry5);

    assertEquals(entry1, entry6);
    entry6.setImplementationId("otherImplementationId");
    assertNotEquals(entry1, entry6);

    assertEquals(entry1, entry7);
    entry7.setTypeId("otherType");
    assertNotEquals(entry1, entry7);
  }
}
