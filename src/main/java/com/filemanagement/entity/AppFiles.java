package com.filemanagement.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "APP_FILES")
public class AppFiles implements Serializable {

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
