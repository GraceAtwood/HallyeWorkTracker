package com.hallye.application.contribution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContributionsResponse {

    private List<ContributionDTO> contributions;
    private int count;
}
