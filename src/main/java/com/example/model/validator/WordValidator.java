package com.example.model.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WordValidator implements ConstraintValidator<ValidWord, String> {
	private final String[] ngWord = { "りんご", "バナナ", "すいか" };

	@Override
    public boolean isValid(String message, ConstraintValidatorContext context) {
		if (message == null) return true;

		// messageがngWordをどれか１つでも含んでいたらfalseを返す
		for (String word : ngWord) {
			if (message.contains(word)) return false;
		}
		return true;
    }
}