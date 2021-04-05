package at.uibk.dps.ee.enactables.logging.composite;

import at.uibk.dps.ee.enactables.logging.EnactmentLogEntry;
import at.uibk.dps.ee.enactables.logging.EnactmentLogger;

import javax.inject.Inject;
import java.util.Set;

public class CompositeEnactmentLogger implements EnactmentLogger {

  protected Set<EnactmentLogger> enactmentLoggers;

  @Inject public CompositeEnactmentLogger(Set<EnactmentLogger> enactmentLoggers) {
    this.enactmentLoggers = enactmentLoggers;
  }

  @Override public void logEnactment(EnactmentLogEntry entry) {
    enactmentLoggers.stream().forEach(logger -> logger.logEnactment(entry));
  }
}
