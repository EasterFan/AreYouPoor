package com.easter.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@ApiModel("注册信息")
public class RegisterResponse implements Serializable {
    private static final long serialVersionUID = -568818572238920054L;
    @Id
    @JsonView(ViewAll.class)
    private String tokenId;
    //@formatter:on
    @JsonView(ViewAll.class)
    private String userId;

    //@formatter:off
    public interface ViewAll {
    }

}
