package co.rishe.graphql.Errors;

import java.io.IOException;

/**
 * Created by Bardia on 12/12/16.
 */
public class InvalidResponse extends Exception {
    public InvalidResponse(String msg) {
        super(msg);
    }
}
