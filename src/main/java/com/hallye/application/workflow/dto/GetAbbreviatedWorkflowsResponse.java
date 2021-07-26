package com.hallye.application.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAbbreviatedWorkflowsResponse {

    private List<AbbreviatedWorkflow> workflows;
    private int totalResults;
}
