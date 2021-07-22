package at.uibk.dps.ee.enactables.decorators;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

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
