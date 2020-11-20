package com.reqhu.testtask2.service;

import com.reqhu.testtask2.domain.Role;
import com.reqhu.testtask2.domain.User;
import com.reqhu.testtask2.dto.UserWithRoles;
import com.reqhu.testtask2.dto.UserWithoutRoles;
import com.reqhu.testtask2.repository.RoleRepository;
import com.reqhu.testtask2.repository.UserRepository;
import com.reqhu.testtask2.util.exception.UserAlreadyExistException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    ModelMapper mapper;

    UserRepository userRepository;

    RoleRepository roleRepository;

    public List<UserWithoutRoles> getAllWithoutRoles() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserWithoutRoles.class))
                .collect(Collectors.toList());
    }

    public UserWithRoles getWithRoles(String login) {
        return mapper.map(userRepository.findWithRoles(login).orElseThrow(), UserWithRoles.class);
    }

    @Transactional
    public void update(String login, UserWithRoles userWithRoles) {
        User user = userRepository.findById(login).orElseThrow();
        user.setName(userWithRoles.getName());
        user.setPassword(userWithRoles.getPassword());
        if (userWithRoles.getRoles() != null) {
            Set<Role> newRoles = userWithRoles.getRoles();
            newRoles.forEach(role ->
                    roleRepository.findByName(role.getName()).ifPresent(value -> role.setId(value.getId()))
            );
            user.setRoles(Set.copyOf(roleRepository.saveAll(newRoles)));
        }
    }

    @Transactional
    public void create(UserWithRoles userWithRoles) {
        userRepository.findById(userWithRoles.getLogin()).ifPresent(dbUser -> {
            throw new UserAlreadyExistException();
        });
        userWithRoles.getRoles().forEach(role ->
                roleRepository.findByName(role.getName()).ifPresent(value -> role.setId(value.getId()))
        );
        userRepository.save(mapper.map(userWithRoles, User.class));
    }

    public void delete(String login) {
        userRepository.deleteById(login);
    }

}
