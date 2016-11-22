package priv.jj.lf2u.RESTservice;
import com.google.gson.Gson;
import priv.jj.lf2u.entity.Manager;
import priv.jj.lf2u.system.CatalogSystem;
import priv.jj.lf2u.system.ManagerSystem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by adrianoob on 11/21/16.
 */
@Path("managers")
public class ManagerResource {
    private final static ManagerSystem ms = ManagerSystem.INSTANCE;
    private final static CatalogSystem ca = CatalogSystem.INSTANCE;

    @GET @Path("accounts")
    @Produces(MediaType.APPLICATION_JSON)
    // 200
    public Response returnAllManagerAccounts() {
        Manager [] managers = ms.getManagers();
        LinkedHashMap<String, String> []  formattedManagers = new LinkedHashMap[managers.length];
        for (int i = 0; i < managers.length; i++) {
            formattedManagers[i] = format(managers[i]);
        }
        return Response.ok().entity(new Gson().toJson(formattedManagers)).build();
    }

    @GET @Path("accounts/{mid}")
    @Produces(MediaType.APPLICATION_JSON)
    // 200 404
    public Response returnManagerAccountByMid(@PathParam("mid") String mid) {
        Manager m = ms.managerOfMid(mid);
        if (m == null) return Response.status(Response.Status.NOT_FOUND).build();
        else return Response.ok().entity(new Gson().toJson(format(m))).build();
    }

    @GET @Path("catalog")
    @Produces(MediaType.APPLICATION_JSON)
    // 200
    public Response returnAllCatalogs() {
        Map<String, String> [] catalogs = ca.getEntireCatalog();
        return Response.ok().entity(new Gson().toJson(catalogs)).build();
    }

    @POST @Path("catalog")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 201created+content
    public Response createCatalog(String str) {
        Hashtable<String, String> data = new Gson().fromJson(str, Hashtable.class);
        String name = data.get("name");
        String id = ca.addCatalog(name);
        data.clear();
        data.put("gcpid", id);
        return Response.created(null).entity(new Gson().toJson(data)).build();
    }

    @POST @Path("catalog/{gcpid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 200ok 404nf
    public Response updateCatalog(@PathParam("gcpid") String gcpid, String str) {
        Hashtable<String, String> data = new Gson().fromJson(str, Hashtable.class);
        String name = data.get("name");
        if (ca.changeCatalog(gcpid, name))
            return Response.ok().build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
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
