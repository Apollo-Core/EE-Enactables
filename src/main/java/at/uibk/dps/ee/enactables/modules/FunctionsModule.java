package at.uibk.dps.ee.enactables.modules;

import at.uibk.dps.ee.enactables.decorators.DecoratorEnactmentLogFactory;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import at.uibk.dps.ee.enactables.logging.dynamodb.DynamoDBEnactmentLogger;
import at.uibk.dps.ee.enactables.logging.influxdb.InfluxDBEnactmentLogger;
import at.uibk.dps.ee.enactables.logging.logback.LogbackEnactmentLogger;
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
  @Info("If checked, the execution properties of functions will be logged.")
  public boolean logFunctionProperties;

  @Order(2)
  @Constant(value = "prio", namespace = DecoratorEnactmentLogFactory.class)
  @Required(property = "logFunctionProperties")
  @Info("Decorators with lower prio are applied later.")
  public int enactmentLoggingDecoratorPriority = 10;

  @Order(3)
  @Info("If checked, the execution properties are logged with Logback.")
  @Required(property = "logFunctionProperties")
  public boolean useLogback;

  @Order(4)
  @Info("The path to the logback configuration file.")
  @File
  @Required(property = "useLogback")
  public String pathToLogbackConfiguration = "./logging/config/logback.xml";

  @Order(5)
  @Info("If checked, the execution properties are logged to InfluxDB.")
  @Required(property = "logFunctionProperties")
  public boolean useInfluxDB;

  @Order(6)
  @Info("The path to the InfluxDB properties file.")
  @File
  @Required(property = "useInfluxDB")
  @Constant(value = "pathToInfluxDBProperties", namespace = InfluxDBEnactmentLogger.class)
  public String pathToInfluxDBProperties = "./logging/config/database/influxdb/influxdb.properties";

  @Order(7)
  @Info("If checked, the execution properties are logged to DynamoDB.")
  @Required(property = "logFunctionProperties")
  public boolean useDynamoDB;

  @Order(8)
  @Info("The path to the DynamoDB properties file.")
  @File
  @Required(property = "useDynamoDB")
  @Constant(value = "pathToDynamoDBProperties", namespace = DynamoDBEnactmentLogger.class)
  public String pathToDynamoDBProperties = "./logging/config/database/dynamodb/dynamodb.properties";

  @Override
  protected void config() {

    if (logFunctionProperties) {
      // TODO how should we handle multi selection
      if (useLogback) {
        bind(EnactmentLogger.class).to(LogbackEnactmentLogger.class);
        // configure the location of the logback config file
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, pathToLogbackConfiguration);
      }
      else if (useInfluxDB){
        bind(EnactmentLogger.class).to(InfluxDBEnactmentLogger.class);
      }
      else if(useDynamoDB) {
        bind(EnactmentLogger.class).to(DynamoDBEnactmentLogger.class);
      }

      // add the function wrapper
      addFunctionDecoratorFactory(DecoratorEnactmentLogFactory.class);
    }

  }

  public String getPathToLogbackConfiguration() {
    return pathToLogbackConfiguration;
  }

  public void setPathToLogbackConfiguration(final String pathToLogbackConfiguration) {
    this.pathToLogbackConfiguration = pathToLogbackConfiguration;
  }

  public String getPathToInfluxDBProperties() {
    return pathToInfluxDBProperties;
  }

  public void setPathToInfluxDBProperties(String pathToInfluxDBProperties) {
    this.pathToInfluxDBProperties = pathToInfluxDBProperties;
  }

  public String getPathToDynamoDBProperties() {
    return pathToDynamoDBProperties;
  }

  public void setPathToDynamoDBProperties(String pathToDynamoDBProperties) {
    this.pathToDynamoDBProperties = pathToDynamoDBProperties;
  }

  public boolean isLogFunctionProperties() {
    return logFunctionProperties;
  }

  public void setLogFunctionProperties(final boolean logFunctionProperties) {
    this.logFunctionProperties = logFunctionProperties;
  }

  public int getEnactmentLoggingDecoratorPriority() {
    return enactmentLoggingDecoratorPriority;
  }

  public void setEnactmentLoggingDecoratorPriority(final int enactmentLoggingDecoratorPriority) {
    this.enactmentLoggingDecoratorPriority = enactmentLoggingDecoratorPriority;
  }

  public boolean isUseLogback() {
    return useLogback;
  }

  public void setUseLogback(boolean useLogback) {
    this.useLogback = useLogback;
  }

  public boolean isUseInfluxDB() {
    return useInfluxDB;
  }

  public void setUseInfluxDB(boolean useInfluxDB) {
    this.useInfluxDB = useInfluxDB;
  }

  public boolean isUseDynamoDB() {
    return useDynamoDB;
  }

  public void setUseDynamoDB(boolean useDynamoDB) {
    this.useDynamoDB = useDynamoDB;
  }
}
