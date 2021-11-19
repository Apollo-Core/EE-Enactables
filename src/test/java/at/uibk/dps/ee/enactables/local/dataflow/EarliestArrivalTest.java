package at.uibk.dps.ee.enactables.local.dataflow;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import net.sf.opendse.model.Task;

public class EarliestArrivalTest {

  @Test
  public void test() {
    String content = "myInput";
    JsonObject input = new JsonObject();
    input.add(ConstantsEEModel.EarliestArrivalJsonKey, JsonParser.parseString(content));
    Task task = new Task("task");
    EarliestArrival tested = new EarliestArrival(task);
    JsonObject result = tested.processInput(input).result();
    assertEquals(content, result.get(ConstantsEEModel.EarliestArrivalJsonKey).getAsString());
  }
}
