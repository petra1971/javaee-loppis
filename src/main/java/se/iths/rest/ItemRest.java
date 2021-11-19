package se.iths.rest;

import se.iths.entity.Item;
import se.iths.service.ItemService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("items")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemRest {

    @Inject
    ItemService itemService;

    @Path("")
    @POST
    public Response createItem(Item item) {
        itemService.createItem(item);
        return Response.ok(item).build();
    }

    @Path("")
    @PUT
    public Response updateItem(Item item) {
        itemService.updateItem(item);
        return Response.ok(item).build();
    }

    @Path("{id}")
    @GET
    public Response getItem(@PathParam("id") Long id) {
        Item foundItem = itemService.findItemById(id);
        if (foundItem == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Item with ID " + id + " was not found in database.").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return Response.ok(foundItem).build();
    }

    @Path("")
    @GET
    public Response getAllItems() {
        List<Item> foundItems = itemService.getAllItems();
        return Response.ok(foundItems).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteItem(@PathParam("id") Long id) {
        itemService.deleteItem(id);
        return Response.ok().build();
    }

    @Path("getallbycategory")
    @GET
    public Response getAllItemsByCategory(@QueryParam("category") String category) {

        // Här finns logik som filtrerar ut alla items efter vald kategori
        String responseString = "Här får du en lista på alla items i kategori: " + category;
        return Response.ok(responseString).build();
    }

    @Path("updatename/{id}")
    @PATCH
    public Response updateName(@PathParam("id") Long id, @QueryParam("name") String name) {
        Item updatedItem = itemService.updateName(id, name);
        return Response.ok(updatedItem).build();
    }


    // JPQL QUERIES FOR DEMONSTRATION PURPOSES

    @Path("getallnames")
    @GET
    public List<Item> getAllNames() {
        return itemService.getAllNames();
    }

    @Path("getallitemssortedbycategory")
    @GET
    public List<Item> getAllItemsSortedByCategory() {
        return itemService.getAllItemsSortedByCategory();
    }

    @Path("selectmaxprice")
    @GET
    public double selectMaxPrice() {
        return itemService.selectMaxPrice();
    }

    @Path("getallwithnamedquery")
    @GET
    public List<Item> getAllWithNamedQuery() {
        return itemService.getAllWithNamedQuery();
    }

    @Path("updateprice")
    @PATCH
    public void updatePrice() {
       itemService.updatePrice();
    }

    @Path("deleteexpensive")
    @DELETE
    public void deleteExpensive() {
        itemService.deleteExpensive();
    }

    @Path("getbyname_dq/{name}")
    @GET
    public List<Item> getByNameDQ(@PathParam("name") String name) {
        return itemService.getByNameDynamicQuery(name);
    }

    @Path("getbyname_np/{name}")
    @GET
    public List<Item> getByNameNP(@PathParam("name") String name) {
        return itemService.getByNameNamedParameters(name);
    }

    @Path("getbyname_pp/{name}")
    @GET
    public List<Item> getByNamePP(@PathParam("name") String name) {
        return itemService.getByNamePositionalParameters(name);
    }

    @Path("getallitemsbetweenprice/{minPrice}/{maxPrice}")
    @GET
    public List<Item> getAllItemsBetweenPrice(@PathParam("minPrice") double minPrice,
                                              @PathParam("maxPrice") double maxPrice) {
        return itemService.getAllItemsBetweenPrice(minPrice, maxPrice);
    }

    @Path("getallitemscriteria")
    @GET
    public List<Item> getAllItemsCriteria() {
        return itemService.getAllItemsCriteria();
    }

    @Path("getallitemssortedbycategorycriteria")
    @GET
    public List<Item> getAllItemsSortedByCategoryCriteria() {
        return itemService.getAllItemsSortedByCategoryCriteria();
    }



}
