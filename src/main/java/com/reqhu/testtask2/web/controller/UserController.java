package com.reqhu.testtask2.web.controller;

import com.reqhu.testtask2.dto.UserWithRoles;
import com.reqhu.testtask2.dto.UserWithoutRoles;
import com.reqhu.testtask2.service.UserService;
import com.reqhu.testtask2.util.ValidationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserWithoutRoles> getAllWithoutRoles() {
        return userService.getAllWithoutRoles();
    }

    @GetMapping("/{login}")
    public UserWithRoles getWithRoles(@PathVariable String login) {
        return userService.getWithRoles(login);
    }

    @PutMapping("/{login}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String login,
            @RequestBody @Validated(ValidationGroup.Update.class) UserWithRoles userWithRoles,
            BindingResult result
    ) {
        boolean hasErrors = result.hasErrors();
        HttpStatus httpStatus = hasErrors ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.NO_CONTENT;
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", !hasErrors);
        if (hasErrors) {
            responseBody.put("errors", result.getAllErrors());
        } else {
            userService.update(login, userWithRoles);
        }
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody @Validated(ValidationGroup.Create.class) UserWithRoles userWithRoles,
            BindingResult result
    ) {
        boolean hasErrors = result.hasErrors();
        HttpStatus httpStatus = hasErrors ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.CREATED;
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", !hasErrors);
        if (hasErrors) {
            responseBody.put("errors", result.getAllErrors());
        } else {
            userService.create(userWithRoles);
        }
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String login) {
        userService.delete(login);
    }

}
