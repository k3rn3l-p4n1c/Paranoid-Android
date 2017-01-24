import java.io.IOException;
import junit.framework.*;
import co.rishe.graphql.Errors.InvalidResponse;
import co.rishe.graphql.implementation.GraphClient;
import co.rishe.graphql.implementation.GraphClient.GraphRequest;

/**
 * Created by Bardia on 12/27/16.
 */
public class GraphQueryTest extends TestCase {
    public void testSimple() throws IOException, InvalidResponse {
        GraphClient client = new GraphClient("http://graphql-swapi.parseapp.com/");
        GraphRequest request = client.createRequest(MyQuery.class);
        MyQuery data = (MyQuery) request.execute();
        assertEquals(data.allFilms.films.size(), 6);
    }
}
