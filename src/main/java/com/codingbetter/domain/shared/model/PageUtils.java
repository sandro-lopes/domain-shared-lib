package com.codingbetter.domain.shared.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Utility class for pagination-related operations.
 * Provides methods to create and manipulate Page instances.
 */
public final class PageUtils {

    private PageUtils() {
        // Utility class should not be instantiated
    }

    /**
     * Creates an empty page.
     *
     * @param <T> The type of elements in the page
     * @return An empty page
     */
    public static <T> Page<T> empty() {
        return new PageImpl<T>();
    }

    /**
     * Creates a page from a list of elements.
     *
     * @param content The elements of the page
     * @param totalElements The total number of elements
     * @param pageNumber The current page number (zero-based)
     * @param pageSize The page size
     * @param <T> The type of elements in the page
     * @return A page with the provided elements
     */
    public static <T> Page<T> of(List<T> content, long totalElements, int pageNumber, int pageSize) {
        return new PageImpl<T>(content, totalElements, pageNumber, pageSize);
    }

    /**
     * Maps a page to a new page with elements of another type.
     *
     * @param page The original page
     * @param converter The conversion function
     * @param <T> The type of elements in the original page
     * @param <U> The type of elements in the new page
     * @return A new page with the converted elements
     */
    public static <T, U> Page<U> map(Page<T> page, Function<? super T, ? extends U> converter) {
        if (page == null) {
            return empty();
        }
        return page.map(converter);
    }

    /**
     * Creates a page from a complete list, applying pagination.
     *
     * @param list The complete list of elements
     * @param pageNumber The desired page number (zero-based)
     * @param pageSize The page size
     * @param <T> The type of elements in the list
     * @return A page with the elements from the list corresponding to the requested page
     */
    public static <T> Page<T> paginate(List<T> list, int pageNumber, int pageSize) {
        if (list == null || list.isEmpty()) {
            return empty();
        }

        int totalElements = list.size();
        
        if (pageSize <= 0) {
            return of(new ArrayList<>(list), totalElements, 0, totalElements);
        }

        int startIndex = pageNumber * pageSize;
        if (startIndex >= totalElements) {
            return of(Collections.emptyList(), totalElements, pageNumber, pageSize);
        }

        int endIndex = Math.min(startIndex + pageSize, totalElements);
        List<T> pageContent = new ArrayList<>(list.subList(startIndex, endIndex));
        
        return of(pageContent, totalElements, pageNumber, pageSize);
    }
} 