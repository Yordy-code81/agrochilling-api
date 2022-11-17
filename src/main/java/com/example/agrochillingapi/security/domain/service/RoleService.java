package com.example.agrochillingapi.security.domain.service;

import com.example.agrochillingapi.security.domain.model.entity.Role;

import java.util.List;

public interface RoleService {
    void seed();
    List<Role> getAll();
}
