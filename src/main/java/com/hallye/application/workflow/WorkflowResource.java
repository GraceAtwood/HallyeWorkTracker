package com.hallye.application.workflow;

import com.google.inject.Inject;
import com.hallye.application.contribution.ContributionService;
import com.hallye.application.contribution.dto.ContributionDTO;
import com.hallye.application.contribution.model.Contribution;
import com.hallye.application.workflow.dto.CreateContributionRequest;
import com.hallye.application.contribution.dto.GetContributionsResponse;
import com.hallye.application.workflow.dto.UpdateContributionRequest;
import com.hallye.application.workflow.dto.*;
import com.hallye.application.workflow.model.Workflow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.NotImplementedException;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Path("workflows")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Workflow", consumes = "application/json", produces = "application/json")
public class WorkflowResource {

    private final WorkflowService workflowService;
    private final ContributionService contributionService;

    @Inject
    public WorkflowResource(WorkflowService workflowService, ContributionService contributionService) {
        this.workflowService = workflowService;
        this.contributionService = contributionService;
    }

    @GET
    @ApiOperation(value = "Loads an abbreviated form of all workflows", response = GetAbbreviatedWorkflowsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully loaded all workflows", response = GetAbbreviatedWorkflowsResponse.class)
    })
    public Response get() {

        var abbreviatedWorkflows = workflowService.loadAbbreviatedWorkflows();

        return Response.ok(abbreviatedWorkflows).build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Loads a given workflow entirely", response = GetFullWorkflowResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully loaded workflow", response = GetFullWorkflowResponse.class),
            @ApiResponse(code = 404, message = "A workflow with that id does not exist")
    })
    public Response get(@PathParam("id") final String workflowId) {

        var workflow = workflowService.loadWorkflow(workflowId);

        var response = GetFullWorkflowResponse.builder()
                .id(workflow.getId())
                .packageNumber(workflow.getPackageNumber())
                .startingHumidity(workflow.getStartingHumidity())
                .workflowType(workflow.getWorkflowType())
                .startWeight(workflow.getStartWeight())
                .strain(workflow.getStrain())
                .build();

        return Response.ok(response).build();
    }

    @POST
    @ApiOperation(value = "Creates a new workflow", response = CreateWorkflowResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The workflow that was created, including its generated id", response = CreateWorkflowResponse.class),
            @ApiResponse(code = 400, message = "The workflow could not be created due to a validation exception.  See the response body for why the deletion failed.")
    })
    public Response post(CreateWorkflowRequest createWorkflowRequest) {
        var workflow = workflowService.createWorkflow(createWorkflowRequest.getPackageNumber(), createWorkflowRequest.getStrain(), createWorkflowRequest.getWorkflowType());

        var response = CreateWorkflowResponse.builder()
                .id(workflow.getId())
                .packageNumber(workflow.getPackageNumber())
                .strain(workflow.getStrain())
                .workflowType(workflow.getWorkflowType())
                .build();

        return Response.ok(response).build();
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Deletes a given workflow")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The workflow was successfully deleted"),
            @ApiResponse(code = 404, message = "A workflow with that id does not exist"),
            @ApiResponse(code = 400, message = "The workflow could not be deleted due to a validation exception.  See the response body for why the deletion failed.", response = String.class)
    })
    public Response delete(@PathParam("id") final String workflowId) {
        workflowService.deleteWorkflow(workflowId);

        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Updates the given workflow")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The workflow was successfully updated"),
            @ApiResponse(code = 404, message = "A workflow with that id does not exist"),
            @ApiResponse(code = 400, message = "The workflow could not be updated due to a validation exception.  See the response body for why the deletion failed.", response = String.class)
    })
    public Response put(@BeanParam UpdateWorkflowRequest updateWorkflowRequest) {
        var workflow = workflowService.updateWorkflow(updateWorkflowRequest.getStartingHumidity(), updateWorkflowRequest.getIsComplete(), updateWorkflowRequest.getStartWeight());

        return Response.ok(workflow).build();
    }

    @GET
    @Path("{workflowId}/contributions")
    @ApiOperation(value = "Loads all contributions for a given workflow and optionally allows searching within a given workflow", response = GetContributionsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully loaded all workflows", response = GetContributionsResponse.class)
    })
    public Response get(
            @PathParam("workflowId") final String workflowId,
            @QueryParam("workflowType") final String workflowType,
            @QueryParam("person") final String personId) {

        var workflowContributions = contributionService.getWorkflowContributions(workflowId, workflowType, personId);

        return Response.ok(workflowContributions).build();
    }

    @GET
    @Path("{workflowId}/contributions/{contributionId}")
    @ApiOperation(value = "Loads a given contribution for a given workflow", response = ContributionDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully loaded all workflows", response = ContributionDTO.class)
    })
    public Response get(
            @PathParam("workflowId") final String workflowId,
            @PathParam("contributionId") final String contributionId) {

        throw new NotImplementedException();
    }

    @POST
    @Path("{workflowId}/contributions")
    @ApiOperation(value = "Creates a new contribution entry for a workflow", response = ContributionDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created the contribution with an autogenerated id", response = ContributionDTO.class)
    })
    public Response post(CreateContributionRequest createContributionRequest, @PathParam("workflowId") final String workflowId) {
        var contribution = contributionService.createNewContribution(workflowId, createContributionRequest.getWorkflowStep(), createContributionRequest.getPersonId(), LocalDateTime.now(Clock.systemUTC()));

        return Response.ok(ContributionDTO.builder()
                .workflowStep(contribution.getWorkflowStep())
                .endTime(contribution.getEndTime())
                .startTime(contribution.getStartTime())
                .id(contribution.getId())
                .person(contribution.getPerson())
                .workflowId(contribution.getWorkflow().getId().toString())
                .build()).build();
    }

    @DELETE
    @Path("{workflowId}/contributions/{contributionId}")
    @ApiOperation(value = "Deletes a given contribution belonging to a given workflow")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the contribution"),
            @ApiResponse(code = 404, message = "A contribution with that id does not exist"),
            @ApiResponse(code = 400, message = "Failed to create a contribution due to a validation exception.  See the response body for a reason why it failed.")
    })
    public Response delete(
            @PathParam("workflowId") final String workflowId,
            @PathParam("contributionId") final String contributionId) {

        throw new NotImplementedException();
    }

    @PUT
    @Path("{workflowId}/contributions/{contributionId}")
    @ApiOperation(value = "Updates a given contribution belonging to a given workflow")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the contribution", response = ContributionDTO.class),
            @ApiResponse(code = 404, message = "A contribution with that id does not exist"),
            @ApiResponse(code = 400, message = "Failed to update a contribution due to a validation exception.  See the response body for a reason why it failed.")
    })
    public Response put(
            @PathParam("workflowId") final String workflowId,
            @PathParam("contributionId") final String contributionId,
            @BeanParam UpdateContributionRequest updateContributionRequest) {

        throw new NotImplementedException();
    }
}
