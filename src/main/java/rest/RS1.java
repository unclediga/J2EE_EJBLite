package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/rest")
public class RS1 {
    @GET
    @Path("/show/{message}")
    @Produces("text/plain")
    public Response getMsg(@PathParam("message") String msg) {
        String output = "Message requested : " + msg;
        //Simply return the parameter passed as message
        return Response.status(200).entity(output).build();
    }
}

