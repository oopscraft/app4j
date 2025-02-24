package org.chomookun.arch4j.core.security.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.chomookun.arch4j.core.common.data.BaseModel;
import org.chomookun.arch4j.core.security.entity.RoleAuthorityEntity;
import org.chomookun.arch4j.core.security.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Role extends BaseModel {

    private String roleId;

    private String name;

    private boolean anonymous;

    private boolean authenticated;

    private String note;

    @Builder.Default
	private List<Authority> authorities = new ArrayList<>();

    /**
     * role factory method
     * @param roleEntity role entity
     * @return role
     */
    public static Role from(RoleEntity roleEntity) {
        // authorities
        List<Authority> authorities = roleEntity.getRoleAuthorities().stream()
                .map(RoleAuthorityEntity::getAuthorityEntity)
                .filter(Objects::nonNull)
                .map(Authority::from)
                .toList();
        // returns
        return Role.builder()
                .systemRequired(roleEntity.isSystemRequired())
                .systemUpdatedAt(roleEntity.getSystemUpdatedAt())
                .systemUpdatedBy(roleEntity.getSystemUpdatedBy())
                .roleId(roleEntity.getRoleId())
                .name(roleEntity.getName())
                .anonymous(roleEntity.isAnonymous())
                .authenticated(roleEntity.isAuthenticated())
                .note(roleEntity.getNote())
                .authorities(authorities)
                .build();
    }

}
