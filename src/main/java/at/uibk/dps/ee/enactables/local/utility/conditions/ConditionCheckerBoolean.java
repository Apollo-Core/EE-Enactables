package at.uibk.dps.ee.enactables.local.utility.conditions;

import com.google.gson.JsonElement;

/**
 * The condition checker for boolean values.
 * 
 * @author Fedor Smirnov
 */
public class ConditionCheckerBoolean extends ConditionCheckerAbstract<Boolean> {

  @Override
  protected boolean equal(final Boolean firstArgument, final Boolean secondArgument) {
    return firstArgument.booleanValue() == secondArgument.booleanValue();
  }

  @Override
  protected boolean less(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException("The operation less is not applicable for booleans.");
  }

  @Override
  protected boolean greater(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException("The operation greater is not applicable for booleans.");
  }

  @Override
  protected boolean lessEqual(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException("The operation lessEqual is not applicable for booleans.");
  }

  @Override
  protected boolean greaterEqual(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException(
        "The operation greaterEqual is not applicable for booleans.");
  }

  @Override
  protected boolean contains(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException("The operation contains is not applicable for booleans.");
  }

  @Override
  protected boolean startsWith(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException("The operation startsWith is not applicable for booleans.");
  }

  @Override
  protected boolean endsWith(final Boolean firstArgument, final Boolean secondArgument) {
    throw new IllegalArgumentException("The operation endsWith is not applicable for booleans.");
  }

  @Override
  protected Boolean extractArgument(final JsonElement element) {
    return Boolean.valueOf(element.getAsBoolean());
  }
}
