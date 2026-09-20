package com.manager.shared.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IMapperTest {

    private final IMapper<String, String, String, String> mapper = new IMapper<>() {
        @Override
        public String toResponse(String entity) {
            return entity != null ? entity.toUpperCase() : null;
        }

        @Override
        public String toDomain(String request) {
            return request;
        }
    };

    @Test
    @DisplayName("Should throw UnsupportedOperationException when updateEntity is called by default")
    void updateEntity_shouldThrowUnsupportedOperationException_whenNotImplemented() {
        // Arrange
        Executable executable = () -> mapper.updateEntity("entity", "updateRequest");

        // Act
        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                executable
        );

        // Assert
        assertEquals("Update not implemented for this mapper", exception.getMessage());
    }

    @Test
    @DisplayName("Should return empty list when input list is null")
    void toResponseList_shouldReturnEmptyList_whenNullInput() {
        // Arrange
        List<String> input = null;

        // Act
        List<String> result = mapper.toResponseList(input);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when input list is empty")
    void toResponseList_shouldReturnEmptyList_whenEmptyInputList() {
        // Arrange
        List<String> input = Collections.emptyList();

        // Act
        List<String> result = mapper.toResponseList(input);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should map all elements correctly when input list has valid items")
    void toResponseList_shouldMapAllItems_whenValidInputList() {
        // Arrange
        List<String> input = List.of("patient a", "patient b");

        // Act
        List<String> result = mapper.toResponseList(input);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(List.of("PATIENT A", "PATIENT B"), result);
    }
}