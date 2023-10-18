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
@Table(name = "APP_ITEM")
@EqualsAndHashCode(callSuper = false)
public class AppItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private Group group;
}
