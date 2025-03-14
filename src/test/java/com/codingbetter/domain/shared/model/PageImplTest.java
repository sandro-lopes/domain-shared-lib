package com.codingbetter.domain.shared.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class PageImplTest {

    @Test
    void shouldCreateEmptyPage() {
        // When
        PageImpl<String> page = new PageImpl<>();

        // Then
        assertTrue(page.getContent().isEmpty());
        assertEquals(0, page.getTotalElements());
        assertEquals(0, page.getNumber());
        assertEquals(0, page.getSize());
        assertEquals(1, page.getTotalPages());
        assertFalse(page.hasContent());
        assertTrue(page.isFirst());
        assertTrue(page.isLast());
        assertFalse(page.hasNext());
        assertFalse(page.hasPrevious());
    }

    @Test
    void shouldCreatePageWithContent() {
        // Given
        List<String> content = Arrays.asList("Item 1", "Item 2", "Item 3");
        long totalElements = 10;
        int number = 1;
        int size = 3;

        // When
        PageImpl<String> page = new PageImpl<>(content, totalElements, number, size);

        // Then
        assertEquals(content, page.getContent());
        assertEquals(totalElements, page.getTotalElements());
        assertEquals(number, page.getNumber());
        assertEquals(size, page.getSize());
        assertEquals(4, page.getTotalPages()); // 10 elements / 3 per page = 4 pages (rounded up)
        assertTrue(page.hasContent());
        assertFalse(page.isFirst());
        assertFalse(page.isLast());
        assertTrue(page.hasNext());
        assertTrue(page.hasPrevious());
    }

    @Test
    void shouldHandleNullContent() {
        // When
        PageImpl<String> page = new PageImpl<>(null, 0, 0, 0);

        // Then
        assertNotNull(page.getContent());
        assertTrue(page.getContent().isEmpty());
    }

    @Test
    void shouldCalculateTotalPagesCorrectly() {
        // Test cases: (totalElements, size) -> expectedTotalPages
        assertTotalPages(0, 10, 1);  // Empty page
        assertTotalPages(10, 10, 1); // Exactly one page
        assertTotalPages(11, 10, 2); // Slightly more than one page
        assertTotalPages(20, 10, 2); // Exactly two pages
        assertTotalPages(21, 10, 3); // Slightly more than two pages
        assertTotalPages(10, 0, 1);  // Zero size (special case)
    }

    @Test
    void shouldMapContentCorrectly() {
        // Given
        List<String> content = Arrays.asList("1", "2", "3");
        PageImpl<String> page = new PageImpl<>(content, 10, 0, 3);
        Function<String, Integer> mapper = Integer::valueOf;

        // When
        Page<Integer> mappedPage = page.map(mapper);

        // Then
        assertEquals(Arrays.asList(1, 2, 3), mappedPage.getContent());
        assertEquals(page.getTotalElements(), mappedPage.getTotalElements());
        assertEquals(page.getNumber(), mappedPage.getNumber());
        assertEquals(page.getSize(), mappedPage.getSize());
    }

    @Test
    void shouldReturnUnmodifiableList() {
        // Given
        List<String> content = Arrays.asList("Item 1", "Item 2");
        PageImpl<String> page = new PageImpl<>(content, 2, 0, 2);

        // When
        List<String> pageContent = page.getContent();

        // Then
        assertThrows(UnsupportedOperationException.class, () -> pageContent.add("Item 3"));
    }

    private void assertTotalPages(long totalElements, int size, int expectedTotalPages) {
        PageImpl<Object> page = new PageImpl<>(Collections.emptyList(), totalElements, 0, size);
        assertEquals(expectedTotalPages, page.getTotalPages(),
                String.format("Total pages for %d elements with size %d should be %d", 
                        totalElements, size, expectedTotalPages));
    }
} 