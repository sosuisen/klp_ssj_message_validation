package com.example.model.validator;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@RequestScoped
public class WordValidator implements ConstraintValidator<ValidWord, String> {
	String[] ngWord = { "りんご", "バナナ", "すいか" };

	@Override
    public boolean isValid(String message, ConstraintValidatorContext context) {
		// messageがngWordをどれか１つでも含んでいたらfalseを返す
		for (String word : ngWord) {
			if (message.contains(word)) return false;
		}
		return true;
    }
}