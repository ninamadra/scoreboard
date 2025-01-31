package org.example.scoreboard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilsTest {

    @Test
    @DisplayName("Test requireNonNull with all non-null arguments")
    void testRequireNonNullWithAllNonNull() {
        assertDoesNotThrow(() -> ValidationUtils.requireNonNull("Test", 1, new Object()));
    }


    @Test
    @DisplayName("Test requireNonNull with one null argument")
    void testRequireNonNullWithOneNull() {
        assertThrows(NullPointerException.class, () -> ValidationUtils.requireNonNull("Test", null));
    }


    @Test
    @DisplayName("Test requireNonNull with multiple null arguments")
    void testRequireNonNullWithMultipleNulls() {
        assertThrows(NullPointerException.class, () -> ValidationUtils.requireNonNull(null, null, 1L, "Test", null));
    }


    @Test
    @DisplayName("Test requireNonNegative with all non-negative numbers")
    void testRequireNonNegativeWithAllNonNegative() {
        assertDoesNotThrow(() -> ValidationUtils.requireNonNegative(0, 1, 2, Integer.MAX_VALUE));
    }


    @Test
    @DisplayName("Test requireNonNegative with one negative number")
    void testRequireNonNegativeWithOneNegative() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonNegative(1, 2, -1));
    }


    @Test
    @DisplayName("Test requireNonNegative with multiple negative numbers")
    void testRequireNonNegativeWithMultipleNegatives() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonNegative(1, -123456789, -2, 3));
    }


    @Test
    @DisplayName("Test requireNonNegative with edge case zero")
    void testRequireNonNegativeWithEdgeCaseZero() {
        assertDoesNotThrow(() -> ValidationUtils.requireNonNegative(0, 0, 0));
    }

    @Test
    @DisplayName("Test requireNonNegative with edge case minimum value")
    void testRequireNonNegativeWithEdgeCaseMinValue() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonNegative(Integer.MIN_VALUE));
    }

    @Test
    @DisplayName("Test requireNonBlank with all non-blank strings")
    void testRequireNonBlankWithAllNonBlank() {
        assertDoesNotThrow(() -> ValidationUtils.requireNonBlank("Test", "Test2", "Test3"));
    }

    @Test
    @DisplayName("Test requireNonBlank with one empty string")
    void testRequireNonBlankWithOneEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonBlank("Test", "Test2", ""));
    }

    @Test
    @DisplayName("Test requireNonBlank with one newline string")
    void testRequireNonBlankWithOneNewline() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonBlank("Test", "Test2", "\n"));
    }


    @Test
    @DisplayName("Test requireNonBlank with one null string")
    void testRequireNonBlankWithOneNull() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonBlank("Test", "Test2", null));
    }

    @Test
    @DisplayName("Test requireNonBlank with multiple empty and null strings")
    void testRequireNonBlankWithMultipleEmptyAndNull() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.requireNonBlank("   ", "\n", "", "Test", null, "Test2", "", null));
    }

}