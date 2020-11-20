package com.reqhu.testtask2;

import com.reqhu.testtask2.domain.Role;
import com.reqhu.testtask2.dto.UserWithRoles;

import java.util.HashSet;
import java.util.Set;

public class UserTestData {

    public static final String VASYA_LOGIN = "Vasya";

    public static final String NOT_EXISTING_LOGIN = "NotExistingLogin";

    public static UserWithRoles getNew() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Админ"));
        roles.add(new Role("Разработчик"));
        return new UserWithRoles("Jenia", "Женя", "Jenia1", roles);
    }

    public static UserWithRoles getNewWithDuplicateLogin() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Админ"));
        roles.add(new Role("Разработчик"));
        return new UserWithRoles("Vasya", "Новый Вася", "NewVasya1", roles);
    }

    public static UserWithRoles getNewWithInvalidLogin(String invalidLogin) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Админ"));
        roles.add(new Role("Разработчик"));
        return new UserWithRoles(invalidLogin, "Женя", "Jenia1", roles);
    }

    public static UserWithRoles getNewWithInvalidName(String invalidName) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Админ"));
        roles.add(new Role("Разработчик"));
        return new UserWithRoles("Jenia", invalidName, "Jenia1", roles);
    }

    public static UserWithRoles getNewWithInvalidPassword(String invalidPassword) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Админ"));
        roles.add(new Role("Разработчик"));
        return new UserWithRoles("Jenia", "Женя", invalidPassword, roles);
    }

    public static UserWithRoles getNewWithoutRoles() {
        return new UserWithRoles("Jenia", "Женя", "Jenia1", null);
    }

    public static UserWithRoles getUpdatedWithoutRoles() {
        return new UserWithRoles("Васян", "Vasyan1");
    }

    public static UserWithRoles getUpdatedWithRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Бог"));
        return new UserWithRoles("Васян", "Vasyan1", roles);
    }

    public static UserWithRoles getUpdatedWithInvalidName(String invalidName) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Бог"));
        return new UserWithRoles(invalidName, "Vasyan1", roles);
    }

    public static UserWithRoles getUpdatedWithInvalidPassword(String invalidPassword) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Бог"));
        return new UserWithRoles("Васян", invalidPassword, roles);
    }

}
