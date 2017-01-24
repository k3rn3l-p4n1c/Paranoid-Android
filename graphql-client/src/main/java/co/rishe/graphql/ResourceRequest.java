package co.rishe.graphql;

import java.io.IOException;

import co.rishe.graphql.Errors.InvalidResponse;
import rx.Observable;

/**
 * Created by Bardia on 1/23/17.
 */

public interface ResourceRequest<T> {
    Observable<T> promise();
    T execute() throws IOException, InvalidResponse;
}
