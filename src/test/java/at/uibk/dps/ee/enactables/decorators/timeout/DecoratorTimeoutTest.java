package at.uibk.dps.ee.enactables.decorators.timeout;

import static org.junit.jupiter.api.Assertions.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DecoratorTimeoutTest {

  protected Vertx vertx;
  protected MockEnactmentFunction mockFunction;
  protected VertxProvider vProv;

  protected boolean succeeded = true;

  @BeforeEach
  void setup() {
    vertx = Vertx.vertx();
    mockFunction = new MockEnactmentFunction(vertx);
    vProv = mock(VertxProvider.class);
    when(vProv.getVertx()).thenReturn(vertx);
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testNotInTime() throws InterruptedException {
    DecoratorTimeout tested = new DecoratorTimeout(mockFunction, 100, vProv);
    CountDownLatch cd = new CountDownLatch(1);
    tested.processInput(new JsonObject()).onComplete(asyncRes -> {
      assertTrue(asyncRes.failed());
      cd.countDown();
    });
    cd.await();
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testInTime() throws InterruptedException {
    DecoratorTimeout tested = new DecoratorTimeout(mockFunction, 300, vProv);
    CountDownLatch cd = new CountDownLatch(1);
    tested.processInput(new JsonObject()).onComplete(asyncRes -> {
      assertTrue(asyncRes.succeeded());
      cd.countDown();
    });
    cd.await();
  }

  @Test
  void testWaitLonger() throws InterruptedException {
    DecoratorTimeout tested = new DecoratorTimeout(mockFunction, 100, vProv);
    tested.processInput(new JsonObject()).onComplete(asyncRes -> {
      succeeded = asyncRes.succeeded();
    });
    CountDownLatch latch = new CountDownLatch(1);
    vertx.setTimer(500, timerId -> {
      latch.countDown();
    });
    latch.await();
    assertFalse(succeeded);
  }

  /**
   * Mocks an operation taking 2 seconds
   * 
   * @author Fedor Smirnov
   *
   */
  protected class MockEnactmentFunction implements EnactmentFunction {

    protected final Vertx vertx;

    public MockEnactmentFunction(Vertx vertx) {
      this.vertx = vertx;
    }

    @Override
    public Future<JsonObject> processInput(JsonObject input) {
      Promise<JsonObject> resultPromise = Promise.promise();
      vertx.setTimer(200, timerID -> {
        resultPromise.complete(new JsonObject());
      });
      return resultPromise.future();
    }

    @Override
    public String getTypeId() {
      return null;
    }

    @Override
    public String getFunctionId() {
      return null;
    }

    @Override
    public String getEnactmentMode() {
      return null;
    }

    @Override
    public String getImplementationId() {
      return null;
    }

    @Override
    public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
      return null;
    }

  }

}
