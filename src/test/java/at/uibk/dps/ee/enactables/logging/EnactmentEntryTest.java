package at.uibk.dps.ee.enactables.logging;

import at.uibk.dps.ee.model.objects.Condition;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class EnactmentEntryTest {

  @Test public void test() {
    String id = "id1";
    String type = "type1";
    double executionTime = 110.1;
    Instant timestamp = Instant.now();

    EnactmentLogEntry entry = new EnactmentLogEntry(id, type, executionTime);
    assertEquals(id, entry.getId());
    assertEquals(type, entry.getType());
    assertEquals(executionTime, entry.getExecutionTime(), 0.001);
    assertNotNull(entry.getTimestamp());
    assertEquals(0.0, entry.getInputComplexity(), 0.001);
    assertFalse(entry.isSuccess());

    boolean success = true;
    double inputComplexity = 0.8;

    EnactmentLogEntry entryComplete =
        new EnactmentLogEntry(timestamp, id, type, executionTime, success, inputComplexity);
    assertEquals(timestamp, entryComplete.getTimestamp());
    assertEquals(id, entryComplete.getId());
    assertEquals(type, entryComplete.getType());
    assertEquals(executionTime, entryComplete.getExecutionTime(), 0.001);
    assertNotNull(entry.getTimestamp());
    assertEquals(inputComplexity, entryComplete.getInputComplexity(), 0.001);
    assertEquals(success, entryComplete.isSuccess());
  }

  @Test public void testEquals() {
    String id = "id1";
    String type = "type1";
    double executionTime = 9.99;
    Instant instant = Instant.now();

    EnactmentLogEntry entry1 = new EnactmentLogEntry(id, type, executionTime);
    entry1.setTimestamp(instant);

    EnactmentLogEntry entry2 = new EnactmentLogEntry(id, type,executionTime);
    entry2.setTimestamp(instant);

    assertEquals(entry1.hashCode(), entry2.hashCode());
    assertEquals(entry1, entry2);

    entry2.setTimestamp(Instant.now());
    assertNotEquals(entry1.hashCode(), entry2.hashCode());
    assertNotEquals(entry1, entry2);
  }
}
