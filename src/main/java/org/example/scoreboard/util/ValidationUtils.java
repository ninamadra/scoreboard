package org.example.scoreboard.util;

import java.util.Objects;

/**
 * Utility class for common validation operations.
 */
public class ValidationUtils {
    /**
     * Ensures that all provided objects are not null.
     *
     * @param objects the objects to be validated as non-null.
     * @throws NullPointerException if any object is null.
     */
    public static void requireNonNull(final Object... objects) {
        for (final Object obj : objects) {
            Objects.requireNonNull(obj, "One or more arguments are null");
        }
    }

    /**
     * Ensures that all provided numbers are non-negative.
     *
     * @param numbers the numbers to be validated as non-negative.
     * @throws IllegalArgumentException if any number is negative.
     */
    public static void requireNonNegative(final int... numbers) {
        for (final int num : numbers) {
            if (num < 0) {
                throw new IllegalArgumentException("One or more arguments are negative");
            }
        }
    }

    /**
     * Ensures that all provided strings are not blank.
     *
     * @param strings the strings to be validated as non-blank.
     * @throws IllegalArgumentException if any string is blank.
     */
    public static void requireNonBlank(final String... strings) {
        for (final String str : strings) {
            if (str == null || str.isBlank()) {
                throw new IllegalArgumentException("One or more arguments are blank");
            }
        }
    }
}
