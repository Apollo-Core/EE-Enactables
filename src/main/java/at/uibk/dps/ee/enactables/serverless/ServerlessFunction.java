package at.uibk.dps.ee.enactables.serverless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import at.uibk.dps.ee.enactables.FunctionAbstract;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

/**
 * The {@link ServerlessFunction} models the enactment of an atomic serverless
 * function.
 * 
 * @author Fedor Smirnov
 */
public class ServerlessFunction extends FunctionAbstract {

  protected final WebClient client;

  protected final Logger logger = LoggerFactory.getLogger(ServerlessFunction.class);

  /**
   * Default constructor.
   * 
   * @param task the task associated with the function
   * @param url the url to access the serverless function
   * @param client the http client used for the serverless requests
   */
  public ServerlessFunction(final Task task, final Mapping<Task, Resource> serverlessMapping,
      final WebClient client) {
    super(task, serverlessMapping);
    this.client = client;
  }

  /**
   * Method defining the way to retrieve the Url used to trigger the function
   * execution.
   * 
   * @return Url used to trigger the function execution
   */
  protected String getFaaSUrl() {
    final Mapping<Task, Resource> mappingEdge = getMappingOptional().get();
    final Resource mappingTarget = mappingEdge.getTarget();
    return PropertyServiceResourceServerless.getUri(mappingTarget);
  }

  @Override
  public Future<JsonObject> processVerifiedInput(final JsonObject input) {
    final String url = getFaaSUrl();
    final Promise<JsonObject> resultPromise = Promise.promise();
    final Future<HttpResponse<Buffer>> futureResponse =
        client.postAbs(url).sendJson(new io.vertx.core.json.JsonObject(input.toString()));
    logger.info("Serverless function {} triggerred.", url);
    futureResponse.onSuccess(asyncRes -> {
      logger.info("Serverless function {} finished", url);
      final JsonObject resultJson =
          JsonParser.parseString(asyncRes.body().toString()).getAsJsonObject();
      resultPromise.complete(resultJson);
    }).onFailure(failureThrowable -> resultPromise.fail(failureThrowable));
    return resultPromise.future();
  }
}
