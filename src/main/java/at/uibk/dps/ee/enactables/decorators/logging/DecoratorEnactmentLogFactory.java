package at.uibk.dps.ee.enactables.decorators.logging;

import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.core.function.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import at.uibk.dps.ee.enactables.logging.LoggingParamsExtractor;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.opt4j.core.start.Constant;

/**
 * The {@link DecoratorEnactmentLogFactory} decorates functions by wrapping them
 * in {@link DecoratorEnactmentLog}s.
 *
 * @author Markus Moosbrugger
 */
@Singleton
public class DecoratorEnactmentLogFactory extends FunctionDecoratorFactory {

  public final int priority;
  public final EnactmentLogger enactmentLogger;
  protected final LoggingParamsExtractor paramsExtractor;

  /**
   * The injection constructor.
   *
   * @param priority the priority of the decorator (see parent class comments)
   */
  @Inject
  public DecoratorEnactmentLogFactory(
      @Constant(value = "prio", namespace = DecoratorEnactmentLogFactory.class) final int priority,
      final EnactmentLogger enactmentLogger, LoggingParamsExtractor paramsExtractor) {
    this.priority = priority;
    this.enactmentLogger = enactmentLogger;
    this.paramsExtractor = paramsExtractor;
  }

  @Override
  public EnactmentFunction decorateFunction(final EnactmentFunction function) {
    return new DecoratorEnactmentLog(function, this.enactmentLogger, this.paramsExtractor);
  }

  @Override
  public int getPriority() {
    return priority;
  }
}
