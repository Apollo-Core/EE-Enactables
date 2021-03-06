package at.uibk.dps.ee.enactables.local.utility.conditions;

import java.util.Iterator;
import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.model.objects.Condition;
import at.uibk.dps.ee.model.objects.Condition.CombinedWith;
import at.uibk.dps.ee.model.properties.PropertyServiceData.DataType;

/**
 * The {@link ConditionEvaluator} evaluates a set of conditions and calculates a
 * boolean result.
 * 
 * @author Fedor Smirnov
 */
public class ConditionEvaluator {

  /**
   * Evaluates the conditions and returns the result.
   * 
   * @return the result of the condition evaluation.
   */
  public boolean evaluate(final List<Condition> conditions, final JsonObject jsonInput) {
    final Iterator<Condition> iterator = conditions.iterator();
    final Condition first = iterator.next();
    boolean result = processCondition(first, jsonInput);
    boolean andCombination = first.getCombinedWith().equals(CombinedWith.And);
    while (iterator.hasNext()) {
      final Condition next = iterator.next();
      final boolean nextRes = processCondition(next, jsonInput);
      result = andCombination ? result &= nextRes : (result |= nextRes);
      andCombination = next.getCombinedWith().equals(CombinedWith.And);
    }
    return result;
  }

  /**
   * Calculates the result of the given condition.
   * 
   * @param condition the given condition.
   * @param jsonInput the json object containing the input
   * @return the result of the given condition
   */
  protected boolean processCondition(final Condition condition, final JsonObject jsonInput) {
    final ConditionChecker checker = getConditionChecker(condition.getType());
    final JsonElement firstElement = jsonInput.get(condition.getFirstInputId());
    final JsonElement secondElement = jsonInput.get(condition.getSecondInputId());
    return checker.checkCondition(firstElement, secondElement, condition.getOperator(),
        condition.isNegation());
  }

  /**
   * Returns the appropriate condition checker for the requested type.
   * 
   * @param type
   * @return
   */
  protected ConditionChecker getConditionChecker(final DataType type) {
    switch (type) {
      case String:
        return new ConditionCheckerString();
      case Number:
        return new ConditionCheckerNumber();
      case Boolean:
        return new ConditionCheckerBoolean();
      case Object:
        return new ConditionCheckerObject();
      default:
        throw new IllegalArgumentException(
            "Conditions defined for the type " + type.name() + " are not supported.");
    }
  }
}
