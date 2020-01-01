package com.easter.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String id;

    private String userId;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(insertable = false, updatable = false)
    private LocalDateTime lastModifiedDate;

    public Token(String userId){
        this.userId = userId;
    }
}
