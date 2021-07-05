package at.uibk.dps.ee.enactables.serverless;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import at.uibk.dps.ee.core.function.EnactmentFunction;
import at.uibk.dps.ee.enactables.EnactmentMode;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceMapping;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import io.vertx.core.Future;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The {@link ServerlessFunction} models the enactment of an atomic serverless
 * function.
 * 
 * @author Fedor Smirnov
 */
public class ServerlessFunction implements EnactmentFunction {

  protected final String typeId;
  protected final String implementationId;
  protected final Set<SimpleEntry<String, String>> additionalAttributes;

  protected final String url;
  protected final OkHttpClient client;

  protected final Logger logger = LoggerFactory.getLogger(ServerlessFunction.class);

  /**
   * Default constructor.
   * 
   * @param url the url to access the serverless function
   * @param client the http client used for the serverless requests
   */
  public ServerlessFunction(final Mapping<Task, Resource> serverlessMapping,
      final OkHttpClient client) {
    final Task task = serverlessMapping.getSource();
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
    return Future.succeededFuture(enactServerlessFunction(url, input));
  }



  /**
   * Enacts the serverless function located at the provided url with the provided
   * jsonobject as input. Returns the {@link JsonObject} produced by the resource.
   * 
   * @param url the url of the function
   * @return the {@link JsonObject} containing the result of the enactment
   */
  protected JsonObject enactServerlessFunction(final String url, final JsonObject input) {
    final RequestBody body =
        RequestBody.create(new Gson().toJson(input), ConstantsServerless.MediaTypeJson);
    final Request request = new Request.Builder().url(url).post(body).build();
    try (Response response = client.newCall(request).execute()) {
      final String resultString = response.body().string();
      return JsonParser.parseString(resultString).getAsJsonObject();
    } catch (IOException exc) {
      throw new IllegalStateException(
          "IOException when enacting the serverless function with the url:\n" + url, exc);
    }
  }

  @Override
  public String getTypeId() {
    return typeId;
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
