package at.uibk.dps.ee.enactables.logging;

import java.time.Instant;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.model.graph.EnactmentSpecification;
import at.uibk.dps.ee.model.graph.SpecificationProvider;

/**
 * Class used to extract the logging parameters for user functions.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class LoggingParamsExtractor {

  protected final EnactmentSpecification spec;

  /**
   * Injection constructor
   * 
   * @param specProvider provider for the spec
   */
  @Inject
  public LoggingParamsExtractor(final SpecificationProvider specProvider) {
    this.spec = specProvider.getSpecification();
  }

  /**
   * Extracts the function log entry based on the task and the mapping of the
   * given function.
   * 
   * @param function the given function
   * @return the log entry to create for the function call
   */
  public EnactmentLogEntry extractLogEntry(final FunctionAbstract function, final Instant timeStamp,
      final double execTime, final boolean success, final double inputComplexity) {
    throw new IllegalStateException("Not yet implemented");
  }
}
