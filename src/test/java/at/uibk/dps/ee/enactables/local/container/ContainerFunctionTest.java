package at.uibk.dps.ee.enactables.local.container;

import static org.junit.jupiter.api.Assertions.*;
import com.google.gson.JsonObject;
import at.uibk.dps.ee.core.ContainerManager;
import at.uibk.dps.ee.enactables.EnactmentMode;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import static org.mockito.Mockito.mock;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContainerFunctionTest {

  @Test
  public void test() {
    String typeId = "funcType";
    String implId = "localComp";
    Set<SimpleEntry<String, String>> additional = new HashSet<>();
    ContainerManager mockManager = mock(ContainerManager.class);
    Vertx mockVertx = mock(Vertx.class);
    String imageName = "image";
    ContainerFunction tested =
        new ContainerFunction(typeId, implId, additional, mockManager, imageName, mockVertx);
    // getters
    assertEquals(typeId, tested.getTypeId());
    assertEquals(implId, tested.getImplementationId());
    assertEquals(EnactmentMode.Local.name(), tested.getEnactmentMode());
    assertEquals(additional, tested.getAdditionalAttributes());
    // running the function
    JsonObject expectedResult = new JsonObject();
    Future<JsonObject> expected = Future.succeededFuture(expectedResult);
    JsonObject input = new JsonObject();
    when(mockManager.runImage(imageName, input)).thenReturn(expected);

    tested.processInput(input);

    verify(mockManager).runImage(imageName, input);
  }
}
