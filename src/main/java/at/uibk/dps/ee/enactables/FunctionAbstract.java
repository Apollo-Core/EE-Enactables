package at.uibk.dps.ee.enactables;

import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import at.uibk.dps.ee.core.function.EnactmentFunction;

/**
 * Parent of all functions. Implements the setting of the attributes used for
 * logging.
 * 
 * @author Fedor Smirnov
 *
 */
public abstract class FunctionAbstract implements EnactmentFunction {

  protected String typeId;
  protected final String implId;
  protected final String enactmentMode;
  protected final String functionId;
  protected final Set<SimpleEntry<String, String>> additionalAttributes;

  /**
   * Constructor setting the logging attributes
   * 
   * @param typeId the type of the operation
   * @param implId the type of the implementation
   * @param enactmentMode details about the enactment mode
   * @param functionId the id of the task corresponsing to the function
   * @param additionalAttributes additional attributes
   */
  public FunctionAbstract(final String typeId, final String implId, final String enactmentMode,
      final String functionId, final Set<SimpleEntry<String, String>> additionalAttributes) {
    this.typeId = typeId;
    this.implId = implId;
    this.enactmentMode = enactmentMode;
    this.functionId = functionId;
    this.additionalAttributes = additionalAttributes;
  }

  @Override
  public String getEnactmentMode() {
    return this.enactmentMode;
  }

  @Override
  public String getTypeId() {
    return this.typeId;
  }

  @Override
  public String getImplementationId() {
    return this.implId;
  }

  @Override
  public String getFunctionId() {
    return this.functionId;
  }

  @Override
  public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
    return this.additionalAttributes;
  }
}
