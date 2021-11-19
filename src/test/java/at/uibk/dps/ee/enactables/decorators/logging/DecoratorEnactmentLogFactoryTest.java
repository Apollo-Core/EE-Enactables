package at.uibk.dps.ee.enactables.decorators.logging;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import at.uibk.dps.ee.enactables.logging.LoggingParamsExtractor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

public class DecoratorEnactmentLogFactoryTest {

  @Test
  public void test() {
    int prio = 10;
    EnactmentFunction original = mock(EnactmentFunction.class);
    EnactmentLogger enactmentLogger = mock(EnactmentLogger.class);
    LoggingParamsExtractor extractor = mock(LoggingParamsExtractor.class);
    DecoratorEnactmentLogFactory tested =
        new DecoratorEnactmentLogFactory(prio, enactmentLogger, extractor);
    assertEquals(10, tested.getPriority());
    EnactmentFunction result = tested.decorateFunction(original);
    assertTrue(result instanceof DecoratorEnactmentLog);
  }
}
