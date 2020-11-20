package com.reqhu.testtask2.dto;


import com.reqhu.testtask2.util.ValidationGroup;
import com.reqhu.testtask2.util.constraint.Password;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Value
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserWithoutRoles {

    @NotBlank(groups = ValidationGroup.Create.class)
    @Null(groups = ValidationGroup.Update.class)
    String login;

    @NotBlank
    String name;

    @Password
    String password;

}
