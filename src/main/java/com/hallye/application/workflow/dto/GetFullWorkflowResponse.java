package com.hallye.application.workflow.dto;

import com.hallye.application.workflow.model.WorkflowType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetFullWorkflowResponse {

    private UUID id;
    private double startWeight;
    private String packageNumber;
    private String strain;
    private double startingHumidity;
    private WorkflowType workflowType;
}
