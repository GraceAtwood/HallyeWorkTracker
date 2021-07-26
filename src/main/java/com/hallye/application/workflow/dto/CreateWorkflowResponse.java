package com.hallye.application.workflow.dto;

import com.hallye.application.workflow.model.WorkflowType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkflowResponse {

    private UUID id;
    private String packageNumber;
    private String strain;
    private WorkflowType workflowType;
}
