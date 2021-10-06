package rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

public class TestRS1 {
    public static final String BASE_URI = "http://localhost:8080";
    private HttpServer server;

    public HttpServer startServer() throws IOException {
        System.out.println("starting grizzly...");
        final ResourceConfig rc = new PackagesResourceConfig("EJBLite/src/test/java/rest");
        return GrizzlyServerFactory.createHttpServer(UriBuilder.fromUri(BASE_URI).build(), rc);
    }

    @Before
    public void setUp() {
        try {
            server = startServer();
        } catch (IOException e) {
            throw new RuntimeException("Can't open http server");
        }
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...", BASE_URI));

    }

    @Test
    public void testGetMsg() {
        String prefix = "Message requested : ";
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
        Client client = Client.create(clientConfig);

        WebResource resource = client.resource(BASE_URI).path("EJBLite/src/test/java/rest").path("show");

        String assa = null;
        assa = resource.path("assa").accept(MediaType.TEXT_PLAIN_TYPE).get(String.class);
        System.out.printf("for 'assa' rest returned %s\n", assa);

        Assert.assertEquals(prefix + "assa", assa);
        assa = resource.path("bessa").accept(MediaType.TEXT_PLAIN_TYPE).get(String.class);
        System.out.printf("for 'bessa' rest returned %s\n", assa);
        Assert.assertEquals(prefix + "bessa", assa);

    }


    @After
    public void tearDown() {
        if (server != null) {
            server.stop();
        }
    }

}
