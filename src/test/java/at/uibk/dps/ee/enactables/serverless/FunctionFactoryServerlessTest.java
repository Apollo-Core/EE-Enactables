package at.uibk.dps.ee.enactables.serverless;

import static org.junit.jupiter.api.Assertions.*;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.FactoryInputUser;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import io.vertx.ext.web.client.WebClient;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping.EnactmentMode;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class FunctionFactoryServerlessTest {

  @Test
  public void test() {
    WebClient mockClient = mock(WebClient.class);
    VertxProvider vProv = mock(VertxProvider.class);
    when(vProv.getWebClient()).thenReturn(mockClient);
    FunctionFactoryServerless tested = new FunctionFactoryServerless(new HashSet<>(), vProv);
    Resource slRes = PropertyServiceResourceServerless.createServerlessResource("res", "link");
    Task task = PropertyServiceFunctionUser.createUserTask("task", "addition");
    Mapping<Task, Resource> mapping =
        PropertyServiceMapping.createMapping(task, slRes, EnactmentMode.Serverless, "link");
    EnactmentFunction result = tested.makeFunction(new FactoryInputUser(task, mapping));
    assertTrue(result instanceof ServerlessFunction);
    ServerlessFunction slResult = (ServerlessFunction) result;
    assertEquals("link", slResult.url);
  }
}
