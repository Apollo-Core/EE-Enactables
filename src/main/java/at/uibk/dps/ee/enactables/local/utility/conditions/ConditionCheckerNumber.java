package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;
import at.uibk.dps.ee.enactables.local.utility.ConstantsUtility;

/**
 * The condition checker for numeric inputs.
 * 
 * @author Fedor Smirnov
 */
public class ConditionCheckerNumber extends ConditionCheckerAbstract<Number> {

  @Override
  protected boolean equal(final Number firstArgument, final Number secondArgument) {
    final double first = firstArgument.doubleValue();
    final double second = secondArgument.doubleValue();
    final double differenceAbs = Math.abs(first - second);
    return differenceAbs < ConstantsUtility.epsilon;
  }

  @Override
  protected boolean less(final Number firstArgument, final Number secondArgument) {
    final double first = firstArgument.doubleValue();
    final double second = secondArgument.doubleValue();
    return !equal(firstArgument, secondArgument) && first < second;
  }

  @Override
  protected boolean greater(final Number firstArgument, final Number secondArgument) {
    final double first = firstArgument.doubleValue();
    final double second = secondArgument.doubleValue();
    return !equal(firstArgument, secondArgument) && first > second;
  }

  @Override
  protected boolean lessEqual(final Number firstArgument, final Number secondArgument) {
    final double first = firstArgument.doubleValue();
    final double second = secondArgument.doubleValue();
    return equal(firstArgument, secondArgument) || first < second;
  }

  @Override
  protected boolean greaterEqual(final Number firstArgument, final Number secondArgument) {
    final double first = firstArgument.doubleValue();
    final double second = secondArgument.doubleValue();
    return equal(firstArgument, secondArgument) || first > second;
  }

  @Override
  protected boolean contains(final Number firstArgument, final Number secondArgument) {
    throw new IllegalArgumentException("The operation contains is not applicable to numbers.");
  }

  @Override
  protected boolean startsWith(final Number firstArgument, final Number secondArgument) {
    throw new IllegalArgumentException("The operation startsWith is not applicable to numbers.");
  }

  @Override
  protected boolean endsWith(final Number firstArgument, final Number secondArgument) {
    throw new IllegalArgumentException("The operation endsWith is not applicable to numbers.");
  }

  @Override
  protected Number extractArgument(final JsonElement element) {
    if (element.isJsonPrimitive()) {
      return element.getAsNumber();
    } else if (element.isJsonArray()) {
      return element.getAsJsonArray().size()-1;
    } else {
      throw new IllegalStateException(
          "Cannot parse json element " + element.toString() + "as a number.");
    }
  }
}
