package priv.jj.lf2u.RESTservice;

import priv.jj.lf2u.entity.FarmStoreProduct;
import priv.jj.lf2u.entity.Manager;
import priv.jj.lf2u.system.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by adrianoob on 11/21/16.
 */
@Path("system")
public class SystemResource {
    @GET @Path("delete_local_data")
    // 200
    public Response deleteAllData() {
        CatalogSystem.INSTANCE.clearStoredDate();
        CustomerSystem.INSTANCE.clearStoredData();
        FarmerSystem.INSTANCE.clearStoredData();
        OrderSystem.INSTANCE.clearStoredData();
        ProductSystem.INSTANCE.clearStoredData();
        return Response.ok().build();
    }

    @GET @Path("delete_product_data")
    // 200
    public Response deleteProductDate() {
        ProductSystem.INSTANCE.clearStoredData();
        return Response.ok().build();
    }
}
