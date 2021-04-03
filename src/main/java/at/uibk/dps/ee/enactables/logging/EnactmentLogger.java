package at.uibk.dps.ee.enactables.logging;

/**
 * Interface for the classes used for logging of enactments.
 *
 * @author Markus Moosbrugger
 *
 */

public interface EnactmentLogger {

  void logEnactment(EnactmentLogEntry entry);
}
