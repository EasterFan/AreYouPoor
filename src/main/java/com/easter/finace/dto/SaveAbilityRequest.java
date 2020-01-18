package com.easter.finace.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveAbilityRequest {
    private Integer monthlySalary;

    private Integer monthlySaving;

    private String tokenId;
}
