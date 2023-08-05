package org.oopscraft.arch4j.core.role;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.oopscraft.arch4j.core.test.CoreTestSupport;
import org.oopscraft.arch4j.core.role.dao.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class RoleServiceTest extends CoreTestSupport {

    private final RoleService roleService;

    private Role getTestRole() {
        Role testRole = Role.builder()
                .roleId("role_id")
                .roleName("role_name")
                .build();
        testRole.getAuthorities()
                .add(Authority.builder()
                        .authorityId("authority_id")
                        .build());
        return testRole;
    }

    private Role createTestRole() {
        Role testRole = getTestRole();
        roleService.saveRole(testRole);
        return testRole;
    }

    @Test
    @Order(1)
    void saveRole() {
        // given
        Role testRole = getTestRole();

        // when
        roleService.saveRole(testRole);

        // then
        assertNotNull(entityManager.find(RoleEntity.class, testRole.getRoleId()));
    }

    @Test
    @Order(2)
    void getRole() {
        // given
        Role testRole = createTestRole();

        // when
        Role role = roleService.getRole(testRole.getRoleId()).orElseThrow();

        // then
        assertNotNull(role);
    }

    @Test
    @Order(4)
    void getRoles() {
        // given
        Role testRole = createTestRole();

        // when
        RoleSearch roleSearch = RoleSearch.builder()
                .roleName(testRole.getRoleName())
                .build();
        Page<Role> rolePage = roleService.getRoles(roleSearch, PageRequest.of(0,10));

        // then
        assertTrue(rolePage.getContent().stream()
                .anyMatch(e -> e.getRoleName().contains(roleSearch.getRoleName())));
    }


    @Test
    @Order(3)
    void deleteRole() {
        // given
        Role testRole = createTestRole();

        // when
        roleService.deleteRole(testRole.getRoleId());

        // then
        RoleEntity roleEntity = entityManager.find(RoleEntity.class, testRole.getRoleId());
        assertNull(roleEntity);
    }


}