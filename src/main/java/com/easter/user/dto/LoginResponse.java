package com.easter.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("登陆信息")
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 4778370218295217929L;

    @Id
    @JsonView(RegisterResponse.ViewAll.class)
    private String tokenId;

    @JsonView(RegisterResponse.ViewAll.class)
    private String userId;

    //@formatter:off
    public interface ViewAll {
    }
}
