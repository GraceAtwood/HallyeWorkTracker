package com.hallye.application.workflow;

import com.google.inject.Inject;
import com.hallye.application.contribution.model.Contribution;
import com.hallye.application.workflow.dto.AbbreviatedWorkflow;
import com.hallye.application.workflow.model.Workflow;
import com.hallye.application.workflow.model.WorkflowType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkflowService {

    private final WorkflowRepository workflowRepository;

    @Inject
    public WorkflowService(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public List<AbbreviatedWorkflow> loadAbbreviatedWorkflows() {
        var workflows = workflowRepository.loadAllWorkflows();

        var abbreviatedWorkflows = workflows.stream()
                .map(workflow -> AbbreviatedWorkflow.builder()
                        .id(workflow.getId())
                        .workflowType(workflow.getWorkflowType())
                        .packageNumber(workflow.getPackageNumber())
                        .strain(workflow.getStrain())
                        .inProgressWorkflowSteps(workflow.getContributions().stream()
                                .filter(contribution -> contribution.getEndTime() == null)
                                .map(Contribution::getWorkflowStep)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return abbreviatedWorkflows;
    }

    public Workflow loadWorkflow(String workflowId) {

        var workflow = workflowRepository.loadWorkflow(workflowId);

        return workflow;
    }

    public Workflow createWorkflow(String packageNumber, String strain, WorkflowType workflowType) {

        var workflow = workflowRepository.insertWorkflow(packageNumber, strain, workflowType);

        return workflow;
    }

    public void deleteWorkflow(String workflowId) {
        workflowRepository.deleteWorkflow(workflowId);
    }

    public Workflow updateWorkflow(Double startingHumidity, Boolean isComplete, Double startWeight) {
        return workflowRepository.updateWorkflow(startingHumidity, isComplete, startWeight);
    }
}
