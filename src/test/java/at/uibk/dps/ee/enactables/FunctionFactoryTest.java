package at.uibk.dps.ee.enactables;

import static org.junit.Assert.*;
import org.junit.Test;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.EnactmentFunctionDecorator;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.HashSet;
import java.util.Set;
import static org.mockito.Mockito.verify;

public class FunctionFactoryTest {

  protected static class TestedFactory extends FunctionFactory<Object, EnactmentFunction>{
    public TestedFactory(Set<FunctionDecoratorFactory> decoratorSet) {
      super(decoratorSet);
    }
    
    @Override
    protected EnactmentFunction makeActualFunction(Object input) {
      return null;
    }
  }
  
  @Test
  public void test() {
    
    EnactmentFunction original = mock(EnactmentFunction.class);
    EnactmentFunctionDecorator decoratorMockInner = mock(EnactmentFunctionDecorator.class);
    EnactmentFunctionDecorator decoratorMockOuter = mock(EnactmentFunctionDecorator.class);
    
    FunctionDecoratorFactory factoryInner = mock(FunctionDecoratorFactory.class);
    FunctionDecoratorFactory factoryOuter = mock(FunctionDecoratorFactory.class);
    
    when(factoryInner.getPriority()).thenReturn(2);
    when(factoryOuter.getPriority()).thenReturn(1);
    
    when(factoryInner.decorateFunction(original)).thenReturn(decoratorMockInner);
    when(factoryOuter.decorateFunction(decoratorMockInner)).thenReturn(decoratorMockOuter);
    
    Set<FunctionDecoratorFactory> factories = new HashSet<>();
    factories.add(factoryOuter);
    factories.add(factoryInner);
    TestedFactory tested = new TestedFactory(factories);
    
    assertEquals(factoryInner, tested.decoratorFactories.get(0));
    assertEquals(factoryOuter, tested.decoratorFactories.get(1));
    
    EnactmentFunction result = tested.decorate(original);
    assertEquals(decoratorMockOuter, result);
    
    verify(factoryInner).decorateFunction(original);
    verify(factoryOuter).decorateFunction(decoratorMockInner);
  }

}
