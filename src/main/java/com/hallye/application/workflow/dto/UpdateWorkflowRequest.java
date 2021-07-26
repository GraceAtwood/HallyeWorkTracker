package com.hallye.application.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWorkflowRequest {

    private Double startWeight;
    private Double startingHumidity;
    private Boolean isComplete;
}
