package com.easter.finace.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DebtAbilityRequest {
    private String debtType;

    private Integer liquidAsset;

    private Integer shortTermLiability;

    private Float debtRate;

    private String tokenId;
}
