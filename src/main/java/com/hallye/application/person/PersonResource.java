package com.hallye.application.person;

import com.google.inject.Inject;
import com.hallye.application.person.dto.CreatePersonRequest;
import com.hallye.application.person.dto.GetPersonsResponse;
import com.hallye.application.person.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("person")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Person", consumes = "application/json", produces = "application/json")
public class PersonResource {

    private final PersonService personService;

    @Inject
    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GET
    @ApiOperation(value = "Loads all persons", response = GetPersonsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully loaded all persons", response = GetPersonsResponse.class)
    })
    public Response get() {
        var people = personService.loadAllPersons();

        return Response.ok(people).build();
    }

    @POST
    @ApiOperation(value = "Creates new person", response = Person.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created the person", response = Person.class)
    })
    public Response post(CreatePersonRequest createPersonRequest) {

        var person = personService.createNewPerson(createPersonRequest.getName());

        return Response.ok(person).build();
    }
}
