package com.codingbetter.domain.shared.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the PageUtils class.
 */
class PageUtilsTest {

    @Test
    void testEmptyPage() {
        // Act
        Page<String> page = PageUtils.empty();
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertTrue(page.getContent().isEmpty(), "The page content should be empty");
        assertEquals(0, page.getTotalElements(), "The total elements should be 0");
        assertEquals(0, page.getNumber(), "The page number should be 0");
        assertEquals(0, page.getSize(), "The page size should be 0");
        assertEquals(1, page.getTotalPages(), "The total pages should be 1");
        assertTrue(page.isFirst(), "The page should be the first");
        assertTrue(page.isLast(), "The page should be the last");
        assertFalse(page.hasNext(), "The page should not have next");
        assertFalse(page.hasPrevious(), "The page should not have previous");
    }
    
    @Test
    void testCreatePage() {
        // Arrange
        List<String> content = Arrays.asList("Item 1", "Item 2", "Item 3");
        
        // Act
        Page<String> page = PageUtils.of(content, 10, 1, 3);
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertEquals(content, page.getContent(), "The page content should match the provided content");
        assertEquals(10, page.getTotalElements(), "The total elements should be 10");
        assertEquals(1, page.getNumber(), "The page number should be 1");
        assertEquals(3, page.getSize(), "The page size should be 3");
        assertEquals(4, page.getTotalPages(), "The total pages should be 4");
        assertFalse(page.isFirst(), "The page should not be the first");
        assertFalse(page.isLast(), "The page should not be the last");
        assertTrue(page.hasNext(), "The page should have next");
        assertTrue(page.hasPrevious(), "The page should have previous");
    }
    
    @Test
    void testMapPage() {
        // Arrange
        List<String> content = Arrays.asList("1", "2", "3");
        Page<String> page = PageUtils.of(content, 10, 1, 3);
        Function<String, Integer> converter = Integer::valueOf;
        
        // Act
        Page<Integer> mappedPage = PageUtils.map(page, converter);
        
        // Assert
        assertNotNull(mappedPage, "The mapped page should not be null");
        assertEquals(Arrays.asList(1, 2, 3), mappedPage.getContent(), "The page content should be correctly mapped");
        assertEquals(10, mappedPage.getTotalElements(), "The total elements should be preserved");
        assertEquals(1, mappedPage.getNumber(), "The page number should be preserved");
        assertEquals(3, mappedPage.getSize(), "The page size should be preserved");
    }
    
    @Test
    void testMapNullPage() {
        // Act
        Page<Integer> mappedPage = PageUtils.map(null, String::length);
        
        // Assert
        assertNotNull(mappedPage, "The mapped page should not be null");
        assertTrue(mappedPage.getContent().isEmpty(), "The page content should be empty");
    }
    
    @Test
    void testPaginateList() {
        // Arrange
        List<String> list = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");
        
        // Act
        Page<String> page = PageUtils.paginate(list, 1, 2);
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertEquals(Arrays.asList("Item 3", "Item 4"), page.getContent(), "The page content should contain the correct items");
        assertEquals(5, page.getTotalElements(), "The total elements should be 5");
        assertEquals(1, page.getNumber(), "The page number should be 1");
        assertEquals(2, page.getSize(), "The page size should be 2");
        assertEquals(3, page.getTotalPages(), "The total pages should be 3");
    }
    
    @Test
    void testPaginateEmptyList() {
        // Act
        Page<String> page = PageUtils.paginate(Collections.emptyList(), 0, 10);
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertTrue(page.getContent().isEmpty(), "The page content should be empty");
        assertEquals(0, page.getTotalElements(), "The total elements should be 0");
    }
    
    @Test
    void testPaginateNullList() {
        // Act
        Page<String> page = PageUtils.paginate(null, 0, 10);
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertTrue(page.getContent().isEmpty(), "The page content should be empty");
        assertEquals(0, page.getTotalElements(), "The total elements should be 0");
    }
    
    @Test
    void testPaginateWithInvalidPageSize() {
        // Arrange
        List<String> list = Arrays.asList("Item 1", "Item 2", "Item 3");
        
        // Act
        Page<String> page = PageUtils.paginate(list, 0, 0);
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertEquals(list, page.getContent(), "The page content should contain all items");
        assertEquals(3, page.getTotalElements(), "The total elements should be 3");
        assertEquals(0, page.getNumber(), "The page number should be 0");
        assertEquals(3, page.getSize(), "The page size should be equal to the list size");
        assertEquals(1, page.getTotalPages(), "The total pages should be 1");
    }
    
    @Test
    void testPaginateWithPageOutOfRange() {
        // Arrange
        List<String> list = Arrays.asList("Item 1", "Item 2", "Item 3");
        
        // Act
        Page<String> page = PageUtils.paginate(list, 2, 2);
        
        // Assert
        assertNotNull(page, "The page should not be null");
        assertTrue(page.getContent().isEmpty(), "The page content should be empty");
        assertEquals(3, page.getTotalElements(), "The total elements should be 3");
        assertEquals(2, page.getNumber(), "The page number should be 2");
        assertEquals(2, page.getSize(), "The page size should be 2");
        assertEquals(2, page.getTotalPages(), "The total pages should be 2");
    }
} 