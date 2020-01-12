package com.easter.finace.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExamResponse implements Serializable {
    private static final long serialVersionUID = -452775559057328883L;

    @JsonView(ViewAll.class)
    private String percent;

    @JsonView(ViewAll.class)
    private String comment;

    //@formatter:off
    public interface ViewAll {
    }
}
