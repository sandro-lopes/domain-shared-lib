package com.codingbetter.domain.shared.model;

import java.util.List;
import java.util.function.Function;

/**
 * Interface that defines a page of results.
 * Used for pagination of results in queries.
 *
 * @param <T> The type of elements in the page
 */
public interface Page<T> {
    
    /**
     * Returns the content of the page.
     * @return List with the elements of the page
     */
    List<T> getContent();
    
    /**
     * Returns the total number of elements.
     * @return The total number of elements
     */
    long getTotalElements();
    
    /**
     * Returns the total number of pages.
     * @return The total number of pages
     */
    int getTotalPages();
    
    /**
     * Returns the current page number (zero-based).
     * @return The current page number
     */
    int getNumber();
    
    /**
     * Returns the page size.
     * @return The page size
     */
    int getSize();
    
    /**
     * Checks if the page has content.
     * @return true if the page has content, false otherwise
     */
    boolean hasContent();
    
    /**
     * Checks if this is the first page.
     * @return true if this is the first page, false otherwise
     */
    boolean isFirst();
    
    /**
     * Checks if this is the last page.
     * @return true if this is the last page, false otherwise
     */
    boolean isLast();
    
    /**
     * Checks if there is a next page.
     * @return true if there is a next page, false otherwise
     */
    boolean hasNext();
    
    /**
     * Checks if there is a previous page.
     * @return true if there is a previous page, false otherwise
     */
    boolean hasPrevious();
    
    /**
     * Maps the elements of the page to a new type.
     * @param converter The conversion function
     * @param <U> The target type
     * @return A new page with the converted elements
     */
    <U> Page<U> map(Function<? super T, ? extends U> converter);
} 