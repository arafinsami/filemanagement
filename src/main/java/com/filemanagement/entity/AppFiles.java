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
@Table(name = "APP_FILES")
@EqualsAndHashCode(callSuper = false)
public class AppFiles extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILES_ID")
    private Long id;

    @Lob
    @Column(name = "FILES_BINARY")
    private byte[] binary;

    @OneToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private AppItem appItem;
}
