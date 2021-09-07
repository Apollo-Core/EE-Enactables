package at.uibk.dps.ee.enactables;

import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * Used as the input for factories creating user functions. Required in cases
 * where task and mapping source are not the same (happens, e.g., due to
 * parallel-for replication).
 * 
 * @author Fedor Smirnov
 */
public class FactoryInputUser {

  protected final Task task;
  protected final Mapping<Task, Resource> mapping;

  /**
   * Standard constructor
   * 
   * @param task the task of the function
   * @param mapping the mapping of the task
   */
  public FactoryInputUser(final Task task, final Mapping<Task, Resource> mapping) {
    this.task = task;
    this.mapping = mapping;
  }

  public Task getTask() {
    return task;
  }

  public Mapping<Task, Resource> getMapping() {
    return mapping;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mapping == null) ? 0 : mapping.hashCode());
    result = prime * result + ((task == null) ? 0 : task.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof FactoryInputUser)) {
      return false;
    }
    final FactoryInputUser other = (FactoryInputUser) obj;
    return task.equals(other.task) && mapping.equals(other.mapping);
  }
}
