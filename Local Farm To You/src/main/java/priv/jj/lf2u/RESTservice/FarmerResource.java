package priv.jj.lf2u.RESTservice;
import com.google.gson.Gson;
import priv.jj.lf2u.dataFormatting.FarmerData;
import priv.jj.lf2u.dataFormatting.FarmerReportOrderFormat;
import priv.jj.lf2u.dataFormatting.ProductGETdata;
import priv.jj.lf2u.dataFormatting.ProductPOSTdata;
import priv.jj.lf2u.entity.FarmStoreProduct;
import priv.jj.lf2u.entity.Farmer;
import priv.jj.lf2u.entity.Order;
import priv.jj.lf2u.persistence.FarmerReport3Format;
import priv.jj.lf2u.system.FarmerSystem;
import priv.jj.lf2u.system.OrderSystem;
import priv.jj.lf2u.system.ProductSystem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Created by adrianoob on 10/28/16.
 */
@Path("/farmers")
public class FarmerResource {
    private static final Gson gson = new Gson();
    private static final FarmerSystem fs = FarmerSystem.INSTANCE;
    private static final ProductSystem ps = ProductSystem.INSTANCE;
    private static final OrderSystem os = OrderSystem.INSTANCE;

    /* farmer related */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    // 201created 400bad_request if required data is missing
    public Response addFarmer(String str) {
        // FIXME: 11/21/16 consider when required data is missing
        FarmerData f = gson.fromJson(str, FarmerData.class);
        Hashtable<String, Object> data = new Hashtable<>();
        data.put("personName", f.getPersonName());
        data.put("personEmail", f.getPersonEmail());
        data.put("personPhone", f.getPersonPhone());
        data.put("farmName", f.getFarmName());
        data.put("farmAddress", f.getFarmAddress());
        data.put("farmPhone", f.getFarmPhone());
        data.put("farmWeb", f.getFarmWeb());
        data.put("deliversTo", f.getDelivers_to());
        String fid = fs.addFarmer(f.getPersonName(), f.getPersonEmail(), f.getPersonPhone(),
                f.getFarmName(), f.getFarmAddress(), f.getFarmPhone(), f.getFarmWeb(), f.getDelivers_to());
        URI uri = UriBuilder.fromUri("http://localhost:8080/lf2u/farmers/{fid}").resolveTemplate("fid", fid).build();
        Hashtable<String, String> res = new Hashtable<>();
        res.put("fid", fid);
        return Response.created(uri).entity(gson.toJson(res)).build();
    }

    @GET@Path("/{fid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok+content 404nf
    public Response getFarmerOfFid(@PathParam("fid") String fid) {
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        FarmerData fd = new FarmerData(farmer);
        return Response.ok().entity(gson.toJson(fd)).build();
    }

    @PUT@Path("/{fid}")
    // 200ok 404nf
    public Response putFarmerOfFid(@PathParam("fid") String fid, String str) {
        FarmerData f = gson.fromJson(str, FarmerData.class);
        boolean success = fs.setFarmer(fid, f.getPersonName(), f.getPersonEmail(), f.getPersonPhone(), f.getFarmName(),
                f.getFarmAddress(), f.getFarmPhone(), f.getFarmWeb(), f.getDelivers_to());

        if (success)
            return Response.ok().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok
    public Response getFarmersOfZip(@QueryParam("zip") String zip) {
        Farmer [] farmers = fs.farmersOfZip(zip);
        ArrayList<LinkedHashMap>list = new ArrayList<>();
        for (Farmer farmer : farmers) {
            LinkedHashMap<String, String> table = new LinkedHashMap<>();
            table.put("fid", farmer.getFid());
            table.put("name", farmer.getName());
            list.add(table);
        }
        return Response.ok().entity(gson.toJson(list, ArrayList.class)).build();
    }

    @GET @Path("/{fid}/delivery_charge")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response getDeliveryChargeOfFid(@PathParam("fid") String fid) {
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null) return Response.status(Response.Status.NOT_FOUND).build();

        Hashtable<String, Double> data = new Hashtable<>();
        data.put("delivery_charge", farmer.getDeliveryCharge());
        return Response.ok().entity(gson.toJson(data)).build();
    }

    @POST @Path("/{fid}/delivery_charge")
    // 204no_content 400bad_request for bad data
    public Response updateDeliveryChargeOfFid(@PathParam("fid") String fid, String str) {
        // validate fid
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null) return Response.status(Response.Status.NOT_FOUND).build();

        // validate POST-ed data
        try {
            Hashtable<String, Double> data = gson.fromJson(str, Hashtable.class);
            Double number = data.get("delivery_charge");
            fs.updateDeliveryCharge(fid, number);
            return Response.noContent().build();
        }
        catch (ClassCastException | NullPointerException e) {
            // data is mal-formatted
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /* product related */
    @GET @Path("/{fid}/products")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response returnAllProductsOfFarmerWithFid(@PathParam("fid") String fid) {
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null) return Response.status(Response.Status.NOT_FOUND).build();

        FarmStoreProduct [] prods = ps.getProductsOfFid(fid);
        ProductGETdata [] data = new ProductGETdata[prods.length];
        for (int i = 0; i < prods.length; i++) {
            FarmStoreProduct p = prods[i];
            data[i] = new ProductGETdata(p.getFspid(), p.getName(), p.getNote(), p.getStart_date(), p.getEnd_date(), p.getPrice(), p.getProduct_unit(), p.getImage());
        }
        return Response.ok().entity(gson.toJson(data)).build();
    }

    @GET @Path("/{fid}/products/{fspid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response returnProduct(@PathParam("fid") String fid, @PathParam("fspid") String fspid) {
        FarmStoreProduct p = ps.productOfId_newestVersion(fspid, fid);
        if (p == null) return Response.status(Response.Status.NOT_FOUND).build();

        ProductGETdata data = new ProductGETdata(p.getFspid(), p.getName(), p.getNote(), p.getStart_date(), p.getEnd_date(), p.getPrice(), p.getProduct_unit(), p.getImage());
        return Response.ok().entity(gson.toJson(data)).build();
    }

    @POST @Path("/{fid}/products")
    @Produces(MediaType.APPLICATION_JSON)
    // 201created+content 404nf
    public Response addProductToFid(@PathParam("fid") String fid, String str) {
        ProductPOSTdata d = gson.fromJson(str, ProductPOSTdata.class);
        String id = ps.addProduct(fid, d.getGcpid(), d.getNote(), d.getStart_date(), d.getEnd_date(),d. getPrice(), d.getProduct_unit(), d.getImage());

        if (id == null) // fid or gcpid not found
            return Response.status(Response.Status.NOT_FOUND).build();

        Hashtable<String, String> res = new Hashtable<>();
        res.put("fspid", id);
        URI uri = UriBuilder.fromUri("http://localhost:8080/lf2u/farmers/" + fid + "/products/" + id).build();
        return Response.created(uri).entity(gson.toJson(res)).build();
    }

    @POST @Path("/{fid}/products/{fspid}")
    // 200ok 404nf
    public Response updateProduct(@PathParam("fid") String fid, @PathParam("fspid") String fspid, String str) {
        Hashtable<String, Object> data = gson.fromJson(str, Hashtable.class);
        if (ps.changeProductOfId(fid, fspid, data))
            return Response.ok().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }


    /* reports related */
    @GET @Path("/{fid}/reports")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok (404nf the fid?)
    public Response returnAvailableReports(@PathParam("fid") String fid) {
        LinkedHashMap<String, String> [] data = new LinkedHashMap[4];
        data[0] = new LinkedHashMap<>();
        data[0].put("frid","701"); data[0].put("name", "Orders to deliver today");
        data[1] = new LinkedHashMap<>();
        data[1].put("frid","702"); data[1].put("name", "Orders to deliver tomorrow");
        data[2] = new LinkedHashMap<>();
        data[2].put("frid","703"); data[2].put("name", "Revenue report");
        data[3] = new LinkedHashMap<>();
        data[3].put("frid","704"); data[3].put("name", "Orders delivery report");
        return Response.ok().entity(gson.toJson(data)).build();
    }

    @GET @Path("/{fid}/reports/{frid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response generateReport(@PathParam("fid") String fid, @PathParam("frid") String frid,
                                    @QueryParam("start_date") String start, @QueryParam("end_date") String end) {
        Farmer farmer = fs.farmerOfFid(fid);
        if (farmer == null) return Response.status(Response.Status.NOT_FOUND).build();

        if (frid.equals("701")) {
            Order [] orders = os.ordersTodayByFid(fid);
            FarmerReportOrderFormat data = new FarmerReportOrderFormat("701", "Orders to deliver today", orders);
            return Response.ok().entity(gson.toJson(data)).build();
        }
        else if (frid.equals("702")) {
            Order [] orders = os.ordersTomorrowByFid(fid);
            FarmerReportOrderFormat data = new FarmerReportOrderFormat("702", "Orders to deliver tomorrow", orders);
            return Response.ok().entity(gson.toJson(data)).build();
        }
        else if (frid.equals("703")) {
            if (start == null || end == null) return Response.status(Response.Status.BAD_REQUEST).build();
            Order [] orders = os.ordersByDates(fid, start, end);
            FarmerReport3Format report = new FarmerReport3Format(orders, start, end);
            return Response.ok().entity(gson.toJson(report)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
