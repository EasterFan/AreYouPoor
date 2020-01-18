package com.easter.finace.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ReporterResponse implements Serializable {

    private static final long serialVersionUID = -6268136269090872884L;

    @JsonView(ExamResponse.ViewAll.class)
    private Float emergencyAbility;

    @JsonView(ExamResponse.ViewAll.class)
    private Float savingAbility;

    @JsonView(ExamResponse.ViewAll.class)
    private Float assetGrowthAbility;

    @JsonView(ExamResponse.ViewAll.class)
    private String totalAbility;

    @JsonView(ExamResponse.ViewAll.class)
    private String comment;

    //@formatter:off
    public interface ViewAll {
    }
}
