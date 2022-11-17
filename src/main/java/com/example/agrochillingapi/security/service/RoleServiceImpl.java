package com.example.agrochillingapi.security.service;

import com.example.agrochillingapi.security.domain.model.entity.Role;
import com.example.agrochillingapi.security.domain.model.enumeration.Roles;
import com.example.agrochillingapi.security.domain.persistence.RoleRepository;
import com.example.agrochillingapi.security.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    private static String[] DEFAULT_ROLES = {"ROLE_SELLER", "ROLE_CLIENT"};

    @Override
    public void seed() {
        Arrays.stream(DEFAULT_ROLES).forEach(name -> {
            Roles roleName = Roles.valueOf(name);
            if(!roleRepository.existsByName(roleName)) {
                roleRepository.save((new Role()).withName(roleName));
            }
        } );

    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
