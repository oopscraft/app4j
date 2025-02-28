package org.chomookun.arch4j.core.menu.model;

import jakarta.persistence.Converter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.chomookun.arch4j.core.common.data.converter.AbstractEnumConverter;
import org.chomookun.arch4j.core.menu.entity.MenuRoleEntity;
import org.chomookun.arch4j.core.role.model.Role;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuRole extends Role {

    private String menuId;

    private Type type;

    public enum Type { VIEW, LINK }

    /**
     * menu role factory method
     * @param menuRoleEntity menu role entity
     * @return menu role
     */
    public static MenuRole from(MenuRoleEntity menuRoleEntity) {
        // role
        Role role = Role.from(menuRoleEntity.getRoleEntity());
        // returns
        return MenuRole.builder()
                .menuId(menuRoleEntity.getId().getMenuId())
                .roleId(menuRoleEntity.getId().getRoleId())
                .type(menuRoleEntity.getId().getType())
                .systemRequired(menuRoleEntity.isSystemRequired())
                .systemUpdatedAt(menuRoleEntity.getSystemUpdatedAt())
                .systemUpdatedBy(menuRoleEntity.getSystemUpdatedBy())
                .name(role.getName())
                .anonymous(role.isAnonymous())
                .authenticated(role.isAuthenticated())
                .note(role.getNote())
                .authorities(role.getAuthorities())
                .build();
    }

}
