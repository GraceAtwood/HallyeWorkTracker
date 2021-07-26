package com.hallye.application.workflow.dto;

import com.hallye.application.workflow.model.WorkflowStep;
import com.hallye.application.workflow.model.WorkflowType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbbreviatedWorkflow {

    private UUID id;
    private String packageNumber;
    private String strain;
    private WorkflowType workflowType;
    private List<WorkflowStep> inProgressWorkflowSteps;
}