package at.uibk.dps.ee.enactables.local.container;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import at.uibk.dps.ee.core.ContainerManager;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.guice.container.ContainerManagerProvider;
import at.uibk.dps.ee.guice.starter.VertxProvider;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMappingLocal;
import io.vertx.core.Vertx;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FunctionFactoryLocalTest {

  @Test
  public void test() {
    Set<FunctionDecoratorFactory> decorators = new HashSet<>();
    ContainerManager mockManager = mock(ContainerManager.class);
    ContainerManagerProvider manProv = mock(ContainerManagerProvider.class);
    when(manProv.getContainerManager()).thenReturn(mockManager);
    Vertx mockVertx = mock(Vertx.class);
    VertxProvider vProv = mock(VertxProvider.class);
    when(vProv.getVertx()).thenReturn(mockVertx);
    FunctionFactoryLocal tested = new FunctionFactoryLocal(decorators, manProv, vProv);

    Task task = PropertyServiceFunctionUser.createUserTask("task", "addition");
    Resource res = new Resource("r");
    Mapping<Task, Resource> map =
        PropertyServiceMappingLocal.createMappingLocal(task, res, "image");

    EnactmentFunction result = tested.makeFunction(map);

    assertEquals("addition", result.getTypeId());
    assertEquals(at.uibk.dps.ee.enactables.EnactmentMode.Local.name(), result.getEnactmentMode());
    assertTrue(result.getAdditionalAttributes().isEmpty());
  }
}
