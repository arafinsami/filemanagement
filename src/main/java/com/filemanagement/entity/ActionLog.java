package com.filemanagement.entity;

import com.filemanagement.model.Action;
import com.filemanagement.model.ModuleName;
import com.filemanagement.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "ACTION_LOGS")
@EqualsAndHashCode(callSuper = false)
public class ActionLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_LOGS_ID")
    private Long id;

    private Date created;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Action action;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ModuleName moduleName;

    private String userName;

    private Long userId;

    @Column(length = 1000)
    private String authId;

    private String documentId;

    private String comments;

    private String ipAddress;

    public String getCreatedStr() {
        return DateUtils.format(this.created, DateUtils.DATE_TIME_FORMAT);
    }
}
