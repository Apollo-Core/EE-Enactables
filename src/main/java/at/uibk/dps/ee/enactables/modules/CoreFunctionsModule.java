package at.uibk.dps.ee.enactables.modules;

import at.uibk.dps.ee.enactables.demo.FunctionFactoryDemo;
import at.uibk.dps.ee.enactables.serverless.FunctionFactoryServerless;

/**
 * Configures the usage of serverless and demo functions as the two core
 * function types used in Apollo.
 * 
 * @author Fedor Smirnov
 */
public class CoreFunctionsModule extends FunctionModule {

  @Override
  protected void config() {
    addFunctionFactoryUser(FunctionFactoryServerless.class);
    addFunctionFactoryUser(FunctionFactoryDemo.class);
  }
}
