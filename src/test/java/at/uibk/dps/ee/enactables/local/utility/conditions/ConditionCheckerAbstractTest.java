package at.uibk.dps.ee.enactables.local.utility.conditions;

import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.model.objects.Condition.Operator;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.spy;

public class ConditionCheckerAbstractTest {

  class MockChecker extends ConditionCheckerAbstract<Object> {
    @Override
    protected boolean equal(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean less(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean greater(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean lessEqual(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean greaterEqual(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean contains(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean startsWith(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected boolean endsWith(Object firstArgument, Object secondArgument) {
      return false;
    }

    @Override
    protected Object extractArgument(JsonElement element) {
      return element;
    }
  }

  @Test
  public void testConditionCheck() {
    MockChecker checker = new MockChecker();
    JsonElement first = new JsonObject();
    JsonElement second = new JsonObject();
    assertFalse(checker.checkCondition(first, second, Operator.EQUAL, false));
    assertTrue(checker.checkCondition(first, second, Operator.EQUAL, true));
  }

  @Test
  public void test() {
    MockChecker mockChecker = new MockChecker();
    MockChecker spy = spy(mockChecker);
    Object first = new Object();
    Object second = new Object();

    spy.getPreNegationResult(first, second, Operator.EQUAL);
    verify(spy).equal(first, second);

    spy.getPreNegationResult(first, second, Operator.UNEQUAL);
    verify(spy, times(2)).equal(first, second);

    spy.getPreNegationResult(first, second, Operator.LESS);
    verify(spy).less(first, second);

    spy.getPreNegationResult(first, second, Operator.LESS_EQUAL);
    verify(spy).lessEqual(first, second);

    spy.getPreNegationResult(first, second, Operator.GREATER);
    verify(spy).greater(first, second);

    spy.getPreNegationResult(first, second, Operator.GREATER_EQUAL);
    verify(spy).greaterEqual(first, second);

    spy.getPreNegationResult(first, second, Operator.CONTAINS);
    verify(spy).contains(first, second);

    spy.getPreNegationResult(first, second, Operator.STARTS_WITH);
    verify(spy).startsWith(first, second);

    spy.getPreNegationResult(first, second, Operator.ENDS_WITH);
    verify(spy).endsWith(first, second);
  }
}
