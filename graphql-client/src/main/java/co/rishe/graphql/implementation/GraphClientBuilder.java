package co.rishe.graphql.implementation;

import co.rishe.graphql.ResourceClient;
import co.rishe.graphql.ResourceClientBuilder;

/**
 * Created by Bardia on 1/23/17.
 */

public class GraphClientBuilder implements ResourceClientBuilder {
    private String url;

    public GraphClientBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public ResourceClient build() {
        return new GraphClient(getUrl());
    }
}
