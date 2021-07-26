package com.hallye.application.workflow.model;

import lombok.Getter;

public enum WorkflowType {
    PREMIUM_1_GRAM("Premium 1 Gram"),
    PACK_12("12 Pack");

    @Getter
    private String name;

    WorkflowType(String name) {

        this.name = name;
    }
}
