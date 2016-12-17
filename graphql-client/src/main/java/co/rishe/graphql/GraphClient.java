package co.rishe.graphql;

import co.rishe.graphql.Errors.InvalidResponse;

import com.google.gson.Gson;

import okhttp3.*;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Graph client request a Graph query from a GraphQL server.
 * Created by Bardia on 12/10/16.
 */
public class GraphClient {

    private final OkHttpClient httpClient;
    private final MediaType mediaType;
    private final Gson gson;
    private final String url;


    public GraphClient(String url) {
        this.gson = new Gson();
        this.httpClient = new OkHttpClient();
        this.mediaType = MediaType.parse("application/json");
        this.url = url;
    }

    public <T extends GraphQuery> GraphRequest getRequest(final T query) {
        return new GraphRequest<T>(query);
    }


    public class GraphRequest<T extends GraphQuery> {
        private final Class<T> tClass;
        private final T query;

        GraphRequest(final T query) {
            this.tClass = (Class<T>) query.getClass();
            this.query = query;
        }

        public T getQuery() {
            return query;
        }

        public Call getRequest() {
            String bodyString = "{\n\t\"query\":\n\t\t \"{ " + query.getQueryString() + " }\"\n}";

            final RequestBody body = RequestBody.create(mediaType, bodyString);

            final Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();
            return httpClient.newCall(request);
        }

        private T extractObject(Response response) throws InvalidResponse, IOException {
            String json = response.body().string();

            JSONObject jObject = null;
            try {
                jObject = new JSONObject(json);
            } catch (JSONException e) {
                throw new InvalidResponse("Server does not return a valid response. Response is not json.\n" +
                        "The response is:\n" + json);
            }
            JSONObject data;
            try {
                data = jObject.getJSONObject("data"); // get data object
            } catch (JSONException e) {
                throw new InvalidResponse("Server does not return a valid response. \"data\" field expected in response. Check your GraphQuery class.\n" +
                        "The response is:\n" + json);
            }

            json = data.toString();
            return gson.fromJson(json, tClass);
        }

        public Observable<T> promise() {
            return Observable.create(
                    new Observable.OnSubscribe<T>() {
                        public void call(Subscriber<? super T> subscriber) {
                            try {
                                T response = execute();
                                subscriber.onNext(response);
                                subscriber.onCompleted();
                            } catch (IOException e) {
                                subscriber.onError(e);
                                e.printStackTrace();
                            } catch (InvalidResponse e) {
                                subscriber.onError(e);
                                e.printStackTrace();
                            }
                        }
                    });
        }

        public T execute() throws IOException, InvalidResponse {
            Response response = getRequest().execute();
            return extractObject(response);

        }
    }
}
