package com.example.model.user;

import java.util.List;

import com.example.auth.IdentityStoreConfig;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@RequestScoped
public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
    	List<String> roles = IdentityStoreConfig.getAllRoles();
    	if (roles.contains(role)) return true;
    	return false;
    }
}