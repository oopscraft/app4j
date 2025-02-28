package org.chomookun.arch4j.core.role.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.chomookun.arch4j.core.common.data.BaseEntity;
import org.chomookun.arch4j.core.common.data.converter.BooleanConverter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.chomookun.arch4j.core.user.entity.UserRoleEntity;
import org.hibernate.annotations.Comment;

import java.util.*;

@Entity
@Table(name = "core_role")
@Data
@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleEntity extends BaseEntity {

    @Id
    @Column(name = "role_id", length = 32)
    @Comment("Role ID")
    private String roleId;

    @NotNull
    @Column(name = "name")
    @Comment("Name")
    private String name;

    @Column(name = "anonymous", length = 1)
    @Convert(converter = BooleanConverter.class)
    @Comment("Anonymous or not")
    private boolean anonymous;

    @Column(name = "authenticated", length = 1)
    @Convert(converter = BooleanConverter.class)
    @Comment("Authenticated or not")
    private boolean authenticated;

    @Column(name = "note", length = 4000)
    @Lob
    @Comment("Note")
    private String note;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "role_id", updatable = false)
    @Builder.Default
    private List<RoleAuthorityEntity> roleAuthorities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "role_id", updatable = false)
    @Builder.Default
    private List<UserRoleEntity> userRoles = new ArrayList<>();

}
