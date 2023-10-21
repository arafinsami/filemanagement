package com.filemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serial;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "APP_USER")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class AppUser extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "USER_EMAIL")
    private String email;

    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @Column(name = "APPROVED")
    private Boolean approved;

    @Column(name = "IS_TEMPORARY_PASSWORD")
    private Boolean isTemporaryPassword;

    @Column(name = "PASSWORD_RECOVERY_CODE")
    private String passwordRecoveryCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_GROUP",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID")
    )
    private Set<Group> groups;

}