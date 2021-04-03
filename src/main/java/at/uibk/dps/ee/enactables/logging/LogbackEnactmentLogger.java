package at.uibk.dps.ee.enactables.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackEnactmentLogger implements EnactmentLogger {
  protected final Logger logger = LoggerFactory.getLogger(LogbackEnactmentLogger.class);

  @Override public void logEnactment(EnactmentLogEntry entry) {
    logger
        .info("TYPE {} ID {} EXEC TIME {} ms SUCCESS {}.", entry.getType(), entry.getId(),
            entry.getExecutionTime(), entry.isSuccess());
  }
}
