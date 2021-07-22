package at.uibk.dps.ee.enactables.local.utility;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import at.uibk.dps.ee.model.constants.ConstantsEEModel;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.objects.Condition.CombinedWith;
import at.uibk.dps.ee.model.objects.Condition.Operator;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUtilityCondition;
import at.uibk.dps.ee.model.properties.PropertyServiceData.DataType;
import net.sf.opendse.model.Task;

public class ConditionEvaluationTest {

  @Test
  public void test() {
    JsonElement first = JsonParser.parseString("0.001");
    JsonElement second = JsonParser.parseString("0.001000000001");
    JsonObject input = new JsonObject();
    input.add("first", first);
    input.add("second", second);

    Condition condition1 =
        new Condition("first", "second", Operator.EQUAL, true, DataType.Number, CombinedWith.And);
    Condition condition2 =
        new Condition("first", "second", Operator.EQUAL, false, DataType.Number, CombinedWith.And);
    Condition condition3 = new Condition("first", "second", Operator.LESS_EQUAL, true,
        DataType.Number, CombinedWith.And);
    Condition condition4 = new Condition("first", "second", Operator.GREATER_EQUAL, false,
        DataType.Number, CombinedWith.And);
    List<Condition> conditions = new ArrayList<>();
    conditions.add(condition1);
    conditions.add(condition2);
    conditions.add(condition3);
    conditions.add(condition4);

    Task funcNode =
        PropertyServiceFunctionUtilityCondition.createConditionEvaluation("task", conditions);
    ConditionEvaluation tested = new ConditionEvaluation(funcNode, "id", "type");
    String expectedKey = ConstantsEEModel.JsonKeyIfDecision;

    JsonObject jsonResult = tested.processInput(input).result();
    boolean result = jsonResult.get(expectedKey).getAsBoolean();
    assertFalse(result);
  }
}
