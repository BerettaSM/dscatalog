package com.devsuperior.dscatalog.controllers.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.devsuperior.dscatalog.utils.PathUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationError extends CustomError {

    public static record Field(String fieldName, Set<String> messages) {

        public Field {
            messages = new HashSet<>(messages);
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) {
                return false;
            } 
            if (o == this) {
                return true;
            }
            if (getClass() != o.getClass()) {
                return false;
            }
            Field f = (Field) o;
            return Objects.equals(fieldName, f.fieldName);
        }

        @Override
        public final int hashCode() {
            return Objects.hash(fieldName);
        }

    }

    private final List<Field> errors = new ArrayList<>();

    public ValidationError(MethodArgumentNotValidException e) {
        super(
            "Validation error(s)",
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            Instant.now(),
            PathUtils.getCurrentPath());
        errors.addAll(extractErrors(e));
    }

    public static ValidationError from(MethodArgumentNotValidException e) {
        return new ValidationError(e);
    }

    public ValidationError addError(String fieldName, String message) {
        errors.stream()
            .filter(e -> fieldName.equals(e.fieldName()))
            .findFirst()
            .ifPresentOrElse(
                e -> e.messages().add(message),
                () -> errors.add(new Field(fieldName, Set.of(message))));
        return this;
    }

    private static final Set<Field> extractErrors(MethodArgumentNotValidException e) {
        return e.getFieldErrors()
            .stream()
            .collect(
                Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        FieldError::getDefaultMessage,
                        Collectors.toSet())))
            .entrySet()
            .stream()
            .map(entry -> new Field(entry.getKey(), entry.getValue()))
            .collect(Collectors.toSet());
    }

}
