package at.uibk.dps.ee.enactables.modules;

import at.uibk.dps.ee.enactables.decorators.DecoratorEnactmentLogFactory;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import at.uibk.dps.ee.enactables.logging.influxdb.InfluxDBEnactmentLogger;
import org.opt4j.core.config.annotations.File;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Constant;
import at.uibk.dps.ee.guice.modules.FunctionModule;
import ch.qos.logback.classic.util.ContextInitializer;

/**
 * The {@link FunctionsModule} defines the dynamic aspects of the configuration
 * of functions.
 * 
 * @author Fedor Smirnov
 */
public class FunctionsModule extends FunctionModule {

  @Order(1)
  @Info("If checked, the execution time of functions will be logged.")
  public boolean logTime;

  @Order(2)
  @Info("The path to the logback configuration file.")
  @File
  @Required(property = "logTime")
  public String pathToLoggingConfiguration = "./logging/config/logback.xml";

  @Order(3)
  @Constant(value = "prio", namespace = DecoratorEnactmentLogFactory.class)
  @Required(property = "logTime")
  @Info("Decorators with lower prio are applied later.")
  public int timeLoggingDecoratorPriority = 10;

  @Override
  protected void config() {

    if (logTime) {
      bind(EnactmentLogger.class).to(InfluxDBEnactmentLogger.class);
      // configure the location of the logback config file
      System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, pathToLoggingConfiguration);
      // add the function wrapper
      addFunctionDecoratorFactory(DecoratorEnactmentLogFactory.class);
    }

  }

  public String getPathToLoggingConfiguration() {
    return pathToLoggingConfiguration;
  }

  public void setPathToLoggingConfiguration(final String pathToLoggingConfiguration) {
    this.pathToLoggingConfiguration = pathToLoggingConfiguration;
  }

  public boolean isLogTime() {
    return logTime;
  }

  public void setLogTime(final boolean logTime) {
    this.logTime = logTime;
  }

  public int getTimeLoggingDecoratorPriority() {
    return timeLoggingDecoratorPriority;
  }

  public void setTimeLoggingDecoratorPriority(final int timeLoggingDecoratorPriority) {
    this.timeLoggingDecoratorPriority = timeLoggingDecoratorPriority;
  }
}
