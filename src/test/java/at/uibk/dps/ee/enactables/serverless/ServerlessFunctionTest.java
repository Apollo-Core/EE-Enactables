package at.uibk.dps.ee.enactables.serverless;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.CountDownLatch;
import org.junit.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ServerlessFunctionTest {

  @Test
  public void test() {
    String inputKey = "inputKey";
    String outputKey = "outputKey";
    String inputString = "why are we here?";
    // set up the mock server
    JsonObject output = new JsonObject();
    output.add(outputKey, new JsonPrimitive(42));
    String url = "/address/function";
    try (MockWebServer serverMock = new MockWebServer()) {
      serverMock.enqueue(new MockResponse().setBody(output.toString()));
      serverMock.start();
      HttpUrl baseUrl = serverMock.url(url);
      String serverUrl = baseUrl.toString();
      Resource serverless =
          PropertyServiceResourceServerless.createServerlessResource("resId", serverUrl);
      Task task = PropertyServiceFunctionUser.createUserTask("task", "addition");
      Mapping<Task, Resource> mapping = PropertyServiceMapping.createMapping(task, serverless,
          EnactmentMode.Serverless, serverUrl);

      Vertx vertx = Vertx.vertx();
      WebClient client = WebClient.create(vertx);

      ServerlessFunction tested = new ServerlessFunction(mapping, client);
      JsonObject input = new JsonObject();
      assertEquals(serverUrl, tested.getImplementationId());
      assertEquals("addition", tested.getTypeId());
      SimpleEntry<String, String> expected = new SimpleEntry<String, String>("url", serverUrl);
      assertTrue(tested.getAdditionalAttributes().contains(expected));
      assertEquals(EnactmentMode.Serverless.name(), tested.getEnactmentMode());
      input.add(inputKey, new JsonPrimitive(inputString));
      CountDownLatch latch = new CountDownLatch(1);
      Future<JsonObject> futureResult = tested.processInput(input);
      futureResult.onComplete(res -> {
        latch.countDown();
      });
      try {
        latch.await();
      } catch (InterruptedException e1) {
        fail();
      }
      assertEquals(output, futureResult.result());
      RecordedRequest request;
      try {
        request = serverMock.takeRequest();
        assertEquals("[text=" + input.toString() + "]", request.getBody().toString());
      } catch (InterruptedException e) {
        fail();
      }
    } catch (IOException exc) {
      fail();
    }
  }
}
