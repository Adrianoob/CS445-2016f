package priv.jj.lf2u;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/world")
public class HelloWorldTest {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "Hello, hello! ";
    }
}