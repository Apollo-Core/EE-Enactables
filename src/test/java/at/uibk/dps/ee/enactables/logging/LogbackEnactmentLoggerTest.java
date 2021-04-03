package at.uibk.dps.ee.enactables.logging;

import at.uibk.dps.ee.core.exception.StopException;
import at.uibk.dps.ee.enactables.decorators.DecoratorTimingLog;
import at.uibk.dps.ee.enactables.decorators.DecoratorTimingLogTest;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LogbackEnactmentLoggerTest {

  @Test public void test() {
    String id = "id";
    String type = "type";
    double executionTime = 1.12;
    boolean success = true;

    LogbackEnactmentLogger logbackEnactmentLogger = new LogbackEnactmentLogger();
    Logger logger = (Logger) logbackEnactmentLogger.logger;

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    logger.addAppender(listAppender);

    String expectedLogEntry = "TYPE " + type + " ID " + id + " EXEC TIME " + executionTime + " ms SUCCESS " + success +".";

    EnactmentLogEntry entry = new EnactmentLogEntry(id, type, executionTime);
    entry.setSuccess(true);
    logbackEnactmentLogger.logEnactment(entry);


    List<ILoggingEvent> logList = listAppender.list;
    assertEquals(1, logList.size());
    assertEquals(Level.INFO, logList.get(0).getLevel());
    assertEquals(expectedLogEntry,
        logList.get(0).getFormattedMessage());
  }
}
