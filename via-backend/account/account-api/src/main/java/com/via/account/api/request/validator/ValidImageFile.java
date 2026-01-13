package com.via.account.api.request.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// TODO: Need to migrate to API Support module (including Swagger related features)
@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageFile {

    String message() default "Invalid image file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowedTypes() default {"image/jpeg", "image/jpg", "image/png", "image/gif"};

    long maxSizeBytes() default 10 * 1024 * 1024;
}
