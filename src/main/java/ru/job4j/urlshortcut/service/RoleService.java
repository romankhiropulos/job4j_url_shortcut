package ru.job4j.urlshortcut.service;

import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Role;
import ru.job4j.urlshortcut.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByRole(String roleUser) {
        return roleRepository.findByRole(roleUser);
    }
}
