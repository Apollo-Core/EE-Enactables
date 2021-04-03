package at.uibk.dps.ee.enactables.decorators;

import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.core.enactable.FunctionDecoratorFactory;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;
import com.google.inject.Inject;
import org.opt4j.core.start.Constant;

/**
 * The {@link DecoratorEnactmentLogFactory} decorates functions by wrapping them in
 * {@link DecoratorEnactmentLog}s.
 *
 * @author Markus Moosbrugger
 */
public class DecoratorEnactmentLogFactory extends FunctionDecoratorFactory {

  public final int priority;
  public final EnactmentLogger enactmentLogger;

  /**
   * The injection constructor.
   *
   * @param priority the priority of the decorator (see parent class comments)
   */
  @Inject public DecoratorEnactmentLogFactory(
      @Constant(value = "prio", namespace = DecoratorEnactmentLogFactory.class) final int priority,
      EnactmentLogger enactmentLogger) {
    this.priority = priority;
    this.enactmentLogger = enactmentLogger;
  }

  @Override public EnactmentFunction decorateFunction(EnactmentFunction function) {
    return new DecoratorEnactmentLog(function, this.enactmentLogger);
  }

  @Override public int getPriority() {
    return priority;
  }
}
