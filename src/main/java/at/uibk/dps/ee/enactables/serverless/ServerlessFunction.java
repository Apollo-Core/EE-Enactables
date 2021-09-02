package at.uibk.dps.ee.enactables.serverless;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
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
public class ServerlessFunction implements EnactmentFunction {

  protected String typeId;
  protected final String implementationId;
  protected final Set<SimpleEntry<String, String>> additionalAttributes;

  protected final String url;
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
    final Resource res = serverlessMapping.getTarget();
    this.typeId = PropertyServiceFunctionUser.getTypeId(task);
    this.implementationId = PropertyServiceMapping.getImplementationId(serverlessMapping);
    this.additionalAttributes = new HashSet<>();
    this.url = PropertyServiceResourceServerless.getUri(res);
    additionalAttributes
        .add(new SimpleEntry<String, String>(ConstantsServerless.logAttrSlUrl, url));
    this.client = client;
  }

  @Override
  public Future<JsonObject> processInput(final JsonObject input) {
    final Promise<JsonObject> resultPromise = Promise.promise();
    Future<HttpResponse<Buffer>> futureResponse =
        client.postAbs(url).sendJson(new io.vertx.core.json.JsonObject(input.toString()));
    logger.info("Serverless function {} triggerred.", url);
    futureResponse.onSuccess(asyncRes -> {
      logger.info("Serverless function {} finished", url);
      final JsonObject resultJson =
          JsonParser.parseString(asyncRes.body().toString()).getAsJsonObject();
      resultPromise.complete(resultJson);
    }).onFailure(failureThrowable -> {
      System.err.println(failureThrowable.getMessage());
    });
    return resultPromise.future();
  }

  @Override
  public String getTypeId() {
    return typeId;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  @Override
  public String getEnactmentMode() {
    return EnactmentMode.Serverless.name();
  }

  @Override
  public String getImplementationId() {
    return implementationId;
  }

  @Override
  public Set<SimpleEntry<String, String>> getAdditionalAttributes() {
    return additionalAttributes;
  }
}
