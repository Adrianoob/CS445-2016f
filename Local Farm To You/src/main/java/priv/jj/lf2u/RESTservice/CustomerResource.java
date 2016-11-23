package priv.jj.lf2u.RESTservice;

import com.google.gson.Gson;
import priv.jj.lf2u.dataFormatting.OrderData;
import priv.jj.lf2u.dataFormatting.OrderInfo;
import priv.jj.lf2u.entity.Customer;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.system.CustomerSystem;
import priv.jj.lf2u.system.OrderSystem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Created by adrianoob on 11/22/16.
 */
@Path("/customers")
public class CustomerResource {
    private static final CustomerSystem cs = CustomerSystem.INSTANCE;
    private static final OrderSystem os = OrderSystem.INSTANCE;
    private static final Gson gson = new Gson();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    // 201 created + json + link
    public Response createCustomer(String str) {
        Hashtable<String, String> data = gson.fromJson(str, Hashtable.class);
        String cid = cs.addCustomer(data.get("name"), data.get("email"), data.get("phone"), data.get("street"), data.get("zip"));
        URI uri = UriBuilder.fromUri("http://localhost:8080/lf2u/farmers/" + cid).build();
        data.clear();
        data.put("cid", cid);
        return Response.created(uri).entity(gson.toJson(data)).build();
    }

    @GET @Path("/{cid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response returnCustomerOfCid(@PathParam("cid") String cid) {
        Customer cus = cs.customerOfCid(cid);
        if (cus == null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok().entity(gson.toJson(cus.getFormattedInfo())).build();
    }

    @PUT @Path("/{cid}")
    // 200ok 404nf 400bad if missing info
    public Response updateCustomerOfCid(@PathParam("cid") String cid, String str) {
        Hashtable<String, String> data = gson.fromJson(str, Hashtable.class);
        if (data.get("name") == null || data.get("email") == null || data.get("zip") == null
                || data.get("phone") == null || data.get("street") == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        boolean isSuccessful = cs.setCustomer(cid, data.get("name"), data.get("email"), data.get("phone"), data.get("street"), data.get("zip"));
        if (isSuccessful) return Response.ok().build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET @Path("/{cid}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response returnOrdersOfCid(@PathParam("cid") String cid) {
        Customer cus = cs.customerOfCid(cid);
        if (cus == null) return Response.status(Response.Status.NOT_FOUND).build();

        Order [] orders = os.ordersByCustomerOfCid(cid);
        LinkedHashMap<String, String> [] data = new LinkedHashMap[orders.length];
        for (int i = 0; i < orders.length; i++) {
            data[i] = orders[i].getFormattedInfo();
        }
        return Response.ok().entity(gson.toJson(data)).build();
    }

    @POST @Path("/{cid}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    // 201 link+content 404
    public Response createOrderForCid(@PathParam("cid") String cid, String str) {
        OrderData data = gson.fromJson(str, OrderData.class);

        String oid = os.addOrder(cid, data.getFid(), data.getDelivery_note(), data.getFspidList(), data.getAmountList());
        if (oid.equals("-1")) return Response.status(Response.Status.NOT_FOUND).build();
        if (oid.equals("0")) return Response.status(422).build();

        URI uri = UriBuilder.fromUri("http://localhost:8080/lf2u/customers/" + cid + "/orders/" + oid).build();
        LinkedHashMap<String, String> res = new LinkedHashMap<>();
        res.put("oid", oid);
        return Response.created(uri).entity(gson.toJson(res)).build();
    }

    @POST @Path("/{cid}/orders/{oid}")
    //204 400
    public Response cancelOrder(@PathParam("cid") String cid, @PathParam("oid") String oid, String str) {
        Hashtable<String,String> data = gson.fromJson(str, Hashtable.class);
        if (data.get("status") == null || !data.get("status").equals("cancelled"))
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (os.cancelOrder(oid, cid)) return Response.noContent().build();
        else return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET @Path("{cid}/orders/{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200 404
    public Response returnOrderByOid(@PathParam("cid") String cid, @PathParam("oid") String oid) {
        Order o = os.getOrder(oid, cid);
        if (o == null) return Response.status(Response.Status.NOT_FOUND).build();

        OrderInfo oi = new OrderInfo(o);
        return Response.ok().entity(gson.toJson(oi)).build();
    }
}
