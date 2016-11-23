package priv.jj.lf2u.RESTservice;
import com.google.gson.Gson;
import priv.jj.lf2u.entity.Manager;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.system.CatalogSystem;
import priv.jj.lf2u.system.ManagerSystem;
import priv.jj.lf2u.system.OrderSystem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by adrianoob on 11/21/16.
 */
@Path("managers")
public class ManagerResource {
    private final static Gson gson = new Gson();
    private final static ManagerSystem ms = ManagerSystem.INSTANCE;
    private final static CatalogSystem ca = CatalogSystem.INSTANCE;
    private final static OrderSystem os = OrderSystem.INSTANCE;

    @GET @Path("accounts")
    @Produces(MediaType.APPLICATION_JSON)
    // 200
    public Response returnAllManagerAccounts() {
        Manager [] managers = ms.getManagers();
        LinkedHashMap<String, String> []  formattedManagers = new LinkedHashMap[managers.length];
        for (int i = 0; i < managers.length; i++) {
            formattedManagers[i] = format(managers[i]);
        }
        return Response.ok().entity(gson.toJson(formattedManagers)).build();
    }

    @GET @Path("accounts/{mid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200 404
    public Response returnManagerAccountByMid(@PathParam("mid") String mid) {
        Manager m = ms.managerOfMid(mid);
        if (m == null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok().entity(gson.toJson(format(m))).build();
    }

    @GET @Path("catalog")
    @Produces(MediaType.APPLICATION_JSON)
    // 200
    public Response returnAllCatalogs() {
        Map<String, String> [] catalogs = ca.getEntireCatalog();
        return Response.ok().entity(gson.toJson(catalogs)).build();
    }

    @POST @Path("catalog")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 201created+content
    public Response createCatalog(String str) {
        Hashtable<String, String> data = gson.fromJson(str, Hashtable.class);
        String name = data.get("name");
        String id = ca.addCatalog(name);
        data.clear();
        data.put("gcpid", id);
        return Response.created(null).entity(gson.toJson(data)).build();
    }

    @POST @Path("catalog/{gcpid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response updateCatalog(@PathParam("gcpid") String gcpid, String str) {
        Hashtable<String, String> data = gson.fromJson(str, Hashtable.class);
        String name = data.get("name");
        if (ca.changeCatalog(gcpid, name))
            return Response.ok().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET @Path("reports")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok
    public Response returnAvailableReports() {
        LinkedHashMap<String, String> [] reports = new LinkedHashMap[5];
        reports[0] = new LinkedHashMap<>();
        reports[1] = new LinkedHashMap<>();
        reports[2] = new LinkedHashMap<>();
        reports[3] = new LinkedHashMap<>();
        reports[4] = new LinkedHashMap<>();
        reports[0].put("mrid", "1");
        reports[0].put("name", "Orders placed today");
        reports[1].put("mrid", "2");
        reports[1].put("name", "Orders placed yesterday");
        reports[2].put("mrid", "3");
        reports[2].put("name", "Revenue for previous month");
        reports[3].put("mrid", "4");
        reports[3].put("name", "Revenue yesterday");
        reports[4].put("mrid", "5");
        reports[4].put("name", "Revenue yesterday by zip code");
        return Response.ok().entity(gson.toJson(reports)).build();
    }

    @GET @Path("reports/{mrid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200 404
    public Response generateReport(@PathParam("mrid") String mrid,
                                   @QueryParam("start_date") String start, @QueryParam("end_date") String end) {
        if (mrid.equals("1")) {
            Order[] orders = os.allOrdersPlacedToday();
            int order_placed = orders.length;
            int delivered = 0, cancelled = 0, open = 0;
            for (Order o : orders) {
                if (o.getStatus() == Order.OPEN) open++;
                else if (o.getStatus() == Order.CANCELLED) cancelled++;
                else if (o.getStatus() == Order.DELIVERED) delivered++;
            }
            LinkedHashMap<String, Object> report = new LinkedHashMap<>();
            report.put("mrid", "1");
            report.put("name", "Orders placed today");
            report.put("orders_placed", new Integer(order_placed));
            report.put("orders_delivered", new Integer(delivered));
            report.put("orders_open", new Integer(open));
            report.put("orders_cancelled", new Integer(cancelled));
            return Response.ok().entity(gson.toJson(report)).build();
        }
        else if (mrid.equals("2")) {
            Order[] orders = os.allOrdersPlacedYesterday();
            int order_placed = orders.length;
            int delivered = 0, cancelled = 0, open = 0;
            for (Order o : orders) {
                if (o.getStatus() == Order.OPEN) open++;
                else if (o.getStatus() == Order.CANCELLED) cancelled++;
                else if (o.getStatus() == Order.DELIVERED) delivered++;
            }
            LinkedHashMap<String, Object> report = new LinkedHashMap<>();
            report.put("mrid", "2");
            report.put("name", "Orders placed yesterday");
            report.put("orders_placed", new Integer(order_placed));
            report.put("orders_delivered", new Integer(delivered));
            report.put("orders_open", new Integer(open));
            report.put("orders_cancelled", new Integer(cancelled));
            return Response.ok().entity(gson.toJson(report)).build();

        }
        else if (mrid.equals("3")) {
            Order[] orders = os.allOrdersLastMonth();

            int order_placed = orders.length;
            int delivered = 0, cancelled = 0, open = 0;
            double total_revenue = 0, total_product_revenue = 0, total_delivery_revenue = 0;
            for (Order o : orders) {
                if (o.getStatus() == Order.OPEN) open++;
                else if (o.getStatus() == Order.CANCELLED) cancelled++;
                else if (o.getStatus() == Order.DELIVERED) {
                    delivered++;
                    total_product_revenue += o.productTotal();
                    total_delivery_revenue += o.getDeliveryCharge();
                }
            }
            total_revenue = total_product_revenue + total_delivery_revenue;

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 1); calendar.roll(Calendar.DATE, -1);
            String e = (format.format(calendar.getTime()));
            calendar.set(Calendar.DATE, 1);
            String s = (format.format(calendar.getTime()));

            LinkedHashMap<String, Object> report = new LinkedHashMap<>();
            report.put("mrid", "3");
            report.put("name", "Revenue for last month");
            report.put("start_date", s);
            report.put("end_date", e);
            report.put("orders_placed", new Integer(order_placed));
            report.put("orders_delivered", new Integer(delivered));
            report.put("orders_open", new Integer(open));
            report.put("orders_cancelled", new Integer(cancelled));
            report.put("total_revenue", new Double(total_revenue));
            report.put("total_products_revenue", new Double(total_product_revenue));
            report.put("total_delivery_revenue", new Double(total_delivery_revenue));
            report.put("lf2u_management_fee", new Double(0.03));
            report.put("total_lftu_fees", new Double(total_revenue * 0.03));
            report.put("total_payable_to_farms", new Double(total_revenue * 0.97));

            return Response.ok().entity(gson.toJson(report)).build();
        }
        else if (mrid.equals("4")) {
            Order[] orders = os.allOrdersPlacedYesterday();

            int order_placed = orders.length;
            int delivered = 0, cancelled = 0, open = 0;
            double total_revenue = 0, total_product_revenue = 0, total_delivery_revenue = 0;
            for (Order o : orders) {
                if (o.getStatus() == Order.OPEN) open++;
                else if (o.getStatus() == Order.CANCELLED) cancelled++;
                else if (o.getStatus() == Order.DELIVERED) {
                    delivered++;
                    total_product_revenue += o.productTotal();
                    total_delivery_revenue += o.getDeliveryCharge();
                }
            }
            total_revenue = total_product_revenue + total_delivery_revenue;

            LinkedHashMap<String, Object> report = new LinkedHashMap<>();
            report.put("mrid", "4");
            report.put("name", "Revenue yesterday");
            report.put("orders_placed", new Integer(order_placed));
            report.put("orders_delivered", new Integer(delivered));
            report.put("orders_open", new Integer(open));
            report.put("orders_cancelled", new Integer(cancelled));
            report.put("total_revenue", new Double(total_revenue));
            report.put("total_products_revenue", new Double(total_product_revenue));
            report.put("total_delivery_revenue", new Double(total_delivery_revenue));
            report.put("lf2u_management_fee", new Double(0.03));
            report.put("total_lftu_fees", new Double(total_revenue * 0.03));
            report.put("total_payable_to_farms", new Double(total_revenue * 0.97));

            return Response.ok().entity(gson.toJson(report)).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private LinkedHashMap<String, String> format(Manager m) {
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put("mid", m.getMid());
        data.put("name", m.getName());
        data.put("created_by", m.getCreatedBy());
        data.put("created_date", m.getCreatedDate());
        data.put("phone", m.getPhone());
        data.put("email", m.getEmail());
        return data;
    }
}
