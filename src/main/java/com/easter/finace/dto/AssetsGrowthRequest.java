package com.easter.finace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetsGrowthRequest {
    private Integer investAsset;

    private Integer totalAsset;

    private String tokenId;
}
