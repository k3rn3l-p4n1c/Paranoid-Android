package co.rishe.graphql;

/**
 * Created by Bardia on 1/23/17.
 */

public interface ResourceClientBuilder {
    ResourceClientBuilder setUrl(String url);
    String getUrl();
    ResourceClient build();
}
