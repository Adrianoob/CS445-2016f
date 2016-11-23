package priv.jj.lf2u.RESTservice;

import priv.jj.lf2u.system.OrderSystem;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by adrianoob on 11/22/16.
 */
@Path("delivery")
public class DeliveryResource {
    private OrderSystem os = OrderSystem.INSTANCE;

    @POST @Path("/{oid}")
    // 200 404
    public Response confirmDeliveryOfOid(@PathParam("oid") String oid) {
        if (os.confirmDelivery(oid)) return Response.ok().build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }
}
