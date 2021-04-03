package at.uibk.dps.ee.enactables.decorators;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class DecoratorEnactmentLogFactoryTest {

  @Test
  public void test() {
    int prio = 10;
    EnactmentFunction original = mock(EnactmentFunction.class);
    EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);
    DecoratorEnactmentLogFactory tested = new DecoratorEnactmentLogFactory(prio, enactmentLogger);
    assertEquals(10, tested.getPriority());
    EnactmentFunction result = tested.decorateFunction(original);
    assertTrue(result instanceof DecoratorEnactmentLog);
  }
}
