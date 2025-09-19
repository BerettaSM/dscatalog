package com.devsuperior.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dscatalog.controllers.exceptions.ValidationError;
import com.devsuperior.dscatalog.domain.dto.UserDTO;
import com.devsuperior.dscatalog.domain.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidConstraintValidator implements ConstraintValidator<UserInsertValid, UserDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO dto, ConstraintValidatorContext context) {
        List<ValidationError.Field> list = new ArrayList<>();

        Optional<User> user = userRepository.findByEmail(dto.getEmail());

        if (user.isPresent()) {
            list.add(
                new ValidationError.Field(
                    "email",
                    Set.of("Email already exists")));
        }
        
        for (var field : list) {
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate(
                    field.messages()
                        .stream()
                        .findFirst()
                        .orElse("Something went wrong"))
                .addPropertyNode(field.fieldName())
                .addConstraintViolation();
        }

        return list.isEmpty();
    }
    
}
