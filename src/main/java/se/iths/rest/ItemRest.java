package se.iths.rest;

import se.iths.entity.Item;
import se.iths.entity.User;
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
    @PUT //input a complete json-object for the item in body, no id in the url. e.g. url: http://localhost:8080/javaee-loppis/items and in body {	"category": "Möbler", "createdAt": "2022-03-30", "name": "Soffa","price": 500.0,"quantity": 155}
    public Response updateItem(Item item) {
        itemService.updateItem(item);
        return Response.ok(item).build();
    }

    @Path("{id}")
    @GET
    public Response getItemById(@PathParam("id") Long id) {
        Item foundItem = itemService.findItemById(id);
        if (foundItem == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Item with ID" + id + " was not found in database.")
                    .type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return Response.ok(foundItem).build();
    }

    @Path("")
    @GET
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @Path("{id}")
    @DELETE
    public Response deleteItem(@PathParam("id") Long id) {
        itemService.deleteItem(id);  //Gör validering att itemet finns i serviceklassen istället för här
        return Response.ok().build();
    }


    @Path("{id}")
    @PATCH  //input the field that is to be updated in body e.g. {"name": "Soffa"}, need id in the url. url: http://localhost:8080/javaee-loppis/items/8
    public Response updateName(@PathParam("id") Long id, Item item) {
        Item updatedItem = itemService.updateName(id, item.getName());
        return Response.ok(updatedItem).build();
    }

    //JPQL queries for demo

    // Dynamiq query
    @Path("getbycategory-dq")
    @GET
    public List<Item> getByCategoryDQ(@QueryParam("category") String category) {
        return itemService.getByCategoryDynamicQuery(category);
    }

    // Named parameters
    @Path("getbycategory-np")
    @GET
    public List<Item> getByCategoryNamedParameters(@QueryParam("category") String category) {
        return itemService.getByCategoryNamedParameters(category);
    }

    // Named parameters
    @Path("getbycategory-pp")
    @GET
    public List<Item> getByCategoryPositionalParameters(@QueryParam("category") String category) {
        return itemService.getByCategoryPositionalParameters(category);
    }

    // Where between
    @Path("getbetweenprices")
    @GET
    public List<Item> getBetweenPrices(@QueryParam("minPrice") double minPrice,
                                            @QueryParam("maxPrice") double maxPrice) {
        return itemService.getItemsBetweenPrices(minPrice, maxPrice);
    }

    // Order by (alphabetically)
    @Path("getallorderedbycategory")
    @GET
    public List<Item> getAllItemsOrderedByCategory() {
        return itemService.getAllItemsOrderedByCategory();
    }

    // Named query - get all
    @Path("getall-nq")
    @GET
    public List<Item> getAllItemsNamedQuery() {
        return itemService.getAllItemsNamedQuery();
    }

    // Typed query
    @Path("getmaxprice")
    @GET
    public Double getMaxPrice() {
        return itemService.getHighestPrice();
    }

    // Criteria query -gett all sorted y category
    @Path("getallitems-cq")
    @GET
    public List<Item> getAllItemsCriteriaQuery() {
        return itemService.getAllCriteria();
    }

    @Path("getallitemssortedbycategory-cq")
    @GET
//    public List<Item> getAllItemsSortedByCategoryCriteria() {
//        return itemService.getAllItemsSortedByCategoryCriteria();
//    }
    public Response getAllItemsSortedByCategoryCriteria() {
        List<Item> items = itemService.getAllItems();
        return Response.ok(items).build();

    }




}

