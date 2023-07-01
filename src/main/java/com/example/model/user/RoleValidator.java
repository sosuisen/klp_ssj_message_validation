package com.example.model.user;

import java.util.List;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import com.example.auth.IdentityStoreConfig;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public boolean isValid(String role, ConstraintValidatorContext context) {
    	List<String> roles = IdentityStoreConfig.getAllRoles();
    	
    	// Jakarta ValidationのConstraintValidatorContext仕様にはない
    	// Hibernate独自の実装の場合、unwrapで取り出す。
    	HibernateConstraintValidatorContext hibernateContext = context.unwrap(
				HibernateConstraintValidatorContext.class
		);
    	hibernateContext.addExpressionVariable("validRoles", String.join(" ", roles));

    	if (roles.contains(role)) return true;
    	return false;
    }
}