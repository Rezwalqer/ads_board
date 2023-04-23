package ru.skypro.avito.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Regexp {
    public static final String EMAIL_REGEXP = ".+@.+[.]..+";
    public static final String PHONE_REGEXP = "\\+7\\d{10}";
}
