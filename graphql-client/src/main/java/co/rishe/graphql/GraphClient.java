package co.rishe.graphql;

import co.rishe.graphql.Errors.InvalidResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.*;
import rx.Observable;
import rx.Subscriber;


import java.io.IOException;


/**
 * Graph client request a Graph query from a GraphQL server.
 * Created by Bardia on 12/10/16.
 */
public class GraphClient {

    private final OkHttpClient httpClient;
    private final Gson gson;
    private final String baseUrl;


    public GraphClient(String baseUrl) {
        this.gson = new Gson();
        this.httpClient = new OkHttpClient();
        this.baseUrl = baseUrl;
    }

    public <T extends GraphModel> GraphRequest createRequest(Class<T> query) {
        return new GraphRequest<T>(query);
    }


    public class GraphRequest<T extends GraphModel> {
        private final Class<T> tClass;
        private final GraphQuery query;

        GraphRequest(Class<T> qClass) {
            this.tClass = qClass;
            this.query = new GraphQuery(qClass);

        }

        public GraphQuery getQuery() {
            return query;
        }

        public Call getRequest() {
            final MediaType mediaType = MediaType.parse("application/json");
            String bodyString = "{\n\t\"query\":\n\t\t \"{ " + query.getQueryString() + " }\"\n}";

            final RequestBody body = RequestBody.create(mediaType, bodyString);

            final Request request = new Request.Builder()
                    .url(baseUrl)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .build();
            return httpClient.newCall(request);
        }

        private T extractObject(Response response) throws InvalidResponse, IOException {
            String json = response.body().string();

            JsonParser parser = new JsonParser();

            JsonObject jObject = parser.parse(json).getAsJsonObject();
            if (jObject == null)
                throw new InvalidResponse("Server does not return a valid response. Response is not json.\n" + json);


            JsonObject data = jObject.getAsJsonObject("data"); // get data object
            if (data == null) {
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
