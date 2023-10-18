package com.filemanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "APP_PERMISSION")
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERMISSION_ID")
    private Long id;

    @Column(name = "PERMISSION_LEVEL")
    private String permissionLevel;
}