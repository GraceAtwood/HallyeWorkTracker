package com.hallye.application.contribution;

import com.google.inject.Inject;
import com.hallye.application.contribution.dto.GetContributionsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Contributions", consumes = "application/json", produces = "application/json")
public class ContributionResource {

    private final ContributionService contributionService;

    @Inject
    public ContributionResource(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @GET
    @Path("contributions")
    @ApiOperation(value = "Loads all contributions, regardless of the workflow to which they belong.  Optionally allows searching by person.", response = GetContributionsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all contributions.", response = GetContributionsResponse.class)
    })
    public Response getContributions(@QueryParam("personId") final String personId) {
        throw new NotImplementedException();
    }

}
