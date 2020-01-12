package com.easter.finace.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Finance {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    private String userId;
    private String EmergencyAbility;
    private String shortTermDebt;
    private String LongTermDebt;
    private String monthlyDebt;
    private String debtRate;
    private String comment;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(insertable = false, updatable = false)
    private LocalDateTime lastModifiedDate;
}
