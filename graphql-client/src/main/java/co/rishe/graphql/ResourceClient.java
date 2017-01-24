package co.rishe.graphql;

import co.rishe.graphql.implementation.GraphClient;

/**
 * Created by Bardia on 1/23/17.
 */

public interface ResourceClient {
    <T extends ResourceModel> GraphClient.GraphRequest createRequest(Class<T> query);
}
