package at.uibk.dps.ee.enactables.decorators.timeout;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import static org.mockito.Mockito.mock;


class DecoratorTimeoutFactoryTest {

  protected EnactmentFunction original;
  protected VertxProvider vProv;
  protected int timeout;
  protected DecoratorTimeoutFactory tested;

  @BeforeEach
  void setup() {
    original = mock(EnactmentFunction.class);
    vProv = mock(VertxProvider.class);
    timeout = 3;
    tested = new DecoratorTimeoutFactory(vProv, timeout);
  }


  @Test
  void test() {
    assertEquals(-Integer.MAX_VALUE, tested.getPriority());
    EnactmentFunction result = tested.decorateFunction(original);
    assertTrue(result instanceof DecoratorTimeout);
  }
}
