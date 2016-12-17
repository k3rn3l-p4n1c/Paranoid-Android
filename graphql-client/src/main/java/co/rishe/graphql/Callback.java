package co.rishe.graphql;


public interface Callback<T> {
    void onFailure(Exception e);
    void onResponse(T response);
}
