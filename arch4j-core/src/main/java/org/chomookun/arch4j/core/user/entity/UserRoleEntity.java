package org.chomookun.arch4j.core.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.chomookun.arch4j.core.common.data.BaseEntity;

import jakarta.persistence.*;
import org.chomookun.arch4j.core.role.entity.RoleEntity;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@Entity
@Table(name = "core_user_role")
@IdClass(UserRoleEntity.Pk.class)
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRoleEntity extends BaseEntity {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Pk implements Serializable {
        private String userId;
        private String roleId;
    }

    @Id
    @Column(name = "user_id", length = 32)
    @Comment("User ID")
    private String userId;

    @Id
    @Column(name = "role_id", length = 32)
    @Comment("Role ID")
    private String roleId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private RoleEntity role;

}
