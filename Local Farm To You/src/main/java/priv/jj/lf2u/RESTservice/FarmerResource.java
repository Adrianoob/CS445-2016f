package priv.jj.lf2u.RESTservice;
import com.google.gson.Gson;
import priv.jj.lf2u.dataFormatting.FarmerData;
import priv.jj.lf2u.entity.Farmer;
import priv.jj.lf2u.system.FarmerSystem;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by adrianoob on 10/28/16.
 */
@Path("/farmers")
public class FarmerResource {
    private static final FarmerSystem fs = FarmerSystem.INSTANCE;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFarmer(String str) {
        FarmerData f = new Gson().fromJson(str, FarmerData.class);
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
        return Response.created(uri).entity(fid).build();
    }

    @GET@Path("/{fid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFarmerOfFid(@PathParam("fid") String fid) {
        Farmer farmer = fs.farmOfFid(fid);
        if (farmer == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        FarmerData fd = new FarmerData(farmer);
        return Response.ok().entity(new Gson().toJson(fd)).build();
    }

    @PUT@Path("/{fid}")
    public Response putFarmerOfFid(@PathParam("fid") String fid, String str) {
        FarmerData f = new Gson().fromJson(str, FarmerData.class);
        boolean success = fs.setFarmer(fid, f.getPersonName(), f.getPersonEmail(), f.getPersonPhone(), f.getFarmName(),
                f.getFarmAddress(), f.getFarmPhone(), f.getFarmWeb(), f.getDelivers_to());

        if (success)
            return Response.ok().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFarmersOfZip(@QueryParam("zip") String zip) {
        Farmer [] farmers = fs.farmersOfZip(zip);
        ArrayList<Hashtable>list = new ArrayList<Hashtable>();
        for (Farmer farmer : farmers) {
            Hashtable<String, String> table = new Hashtable<String, String>();
            table.put("fid", farmer.getFid());
            table.put("name", farmer.getName());
            list.add(table);
        }
        return Response.ok().entity(new Gson().toJson(list, ArrayList.class)).build();
    }

}
