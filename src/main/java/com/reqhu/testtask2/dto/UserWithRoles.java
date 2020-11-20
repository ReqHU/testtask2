package com.reqhu.testtask2.dto;

import com.reqhu.testtask2.domain.Role;
import com.reqhu.testtask2.util.ValidationGroup;
import com.reqhu.testtask2.util.constraint.Password;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;

@Value
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserWithRoles {

    @NotBlank(groups = ValidationGroup.Create.class)
    @Null(groups = ValidationGroup.Update.class)
    String login;

    @NotBlank
    String name;

    @Password
    String password;

    @NotNull(groups = ValidationGroup.Create.class)
    Set<Role> roles;

    public UserWithRoles(String name, String password) {
        this.login = null;
        this.name = name;
        this.password = password;
        this.roles = null;
    }

    public UserWithRoles(String name, String password, Set<Role> roles) {
        this.login = null;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

}
