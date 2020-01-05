package com.easter.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String userId;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(insertable = false, updatable = false)
    private LocalDateTime lastModifiedDate;

    public Token(String userId) {
        this.userId = userId;
    }
}
