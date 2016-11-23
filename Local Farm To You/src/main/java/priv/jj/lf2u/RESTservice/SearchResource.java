package priv.jj.lf2u.RESTservice;

import com.google.gson.Gson;
import priv.jj.lf2u.dataFormatting.FarmerData;
import priv.jj.lf2u.dataFormatting.FarmerReportOrderFormat;
import priv.jj.lf2u.dataFormatting.OrderData;
import priv.jj.lf2u.dataFormatting.OrderInfo;
import priv.jj.lf2u.entity.Customer;
import priv.jj.lf2u.entity.Farmer;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.system.CustomerSystem;
import priv.jj.lf2u.system.FarmerSystem;
import priv.jj.lf2u.system.OrderSystem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;

/**
 * Created by adrianoob on 11/22/16.
 */
@Path("search")
public class SearchResource {
    private Gson gson = new Gson();
    private static final CustomerSystem cs = CustomerSystem.INSTANCE;
    private static final FarmerSystem fs = FarmerSystem.INSTANCE;
    private static final OrderSystem os = OrderSystem.INSTANCE;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf if wrong topic keyword
    public Response search(@QueryParam("topic") String topic, @QueryParam("key") String key) {
        if (topic.equals("farm")) {
            Farmer[] farmers = fs.farmersByKeyword(key);
            FarmerData [] data = new FarmerData[farmers.length];
            for (int i = 0; i < farmers.length; i++) {
                data[i] = new FarmerData(farmers[i]);
            }
            return Response.ok().entity(gson.toJson(data)).build();
        }
        else if (topic.equals("customer")) {
            Customer[] customers = cs.customersByKeyword(key);
            LinkedHashMap<String, String> [] data = new LinkedHashMap[customers.length];
            for (int i = 0; i < customers.length; i++) {
                data[i] = customers[i].getFormattedInfo();
            }
            return Response.ok().entity(gson.toJson(data)).build();
        } else if (topic.equals("order")) {
            Order[] orders = os.ordersByKeyword(key);
            OrderInfo[] data = new OrderInfo[orders.length];
            for (int i = 0; i < orders.length; i++) {
                data[i] = new OrderInfo(orders[i]);
            }
            return Response.ok().entity(gson.toJson(data)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
