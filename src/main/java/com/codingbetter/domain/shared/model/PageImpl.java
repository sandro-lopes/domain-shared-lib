package com.codingbetter.domain.shared.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of the Page interface.
 * Provides basic functionality for result pagination.
 * This class has public visibility, but its constructors are protected to prevent direct instantiation by consumer projects.
 * Use the PageUtils class to create Page instances.
 *
 * @param <T> The type of elements in the page
 */
public class PageImpl<T> implements Page<T> {
    
    private final List<T> content;
    private final long totalElements;
    private final int number;
    private final int size;
    
    /**
     * Constructor to create an empty page.
     * Protected visibility to prevent direct instantiation by consumer projects.
     */
    protected PageImpl() {
        this(new ArrayList<>(), 0, 0, 0);
    }
    
    /**
     * Constructor to create a page with content.
     * Protected visibility to prevent direct instantiation by consumer projects.
     *
     * @param content The content of the page
     * @param totalElements The total number of elements
     * @param number The current page number (zero-based)
     * @param size The page size
     */
    protected PageImpl(List<T> content, long totalElements, int number, int size) {
        this.content = content != null ? content : Collections.emptyList();
        this.totalElements = totalElements;
        this.number = number;
        this.size = size;
    }
    
    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }
    
    @Override
    public long getTotalElements() {
        return totalElements;
    }
    
    @Override
    public int getTotalPages() {
        if (size == 0 || totalElements == 0) {
            return 1;
        }
        return (int) Math.ceil((double) totalElements / (double) size);
    }
    
    @Override
    public int getNumber() {
        return number;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }
    
    @Override
    public boolean isFirst() {
        return number == 0;
    }
    
    @Override
    public boolean isLast() {
        return !hasNext();
    }
    
    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }
    
    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }
    
    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        List<U> convertedContent = new ArrayList<>();
        for (T item : content) {
            convertedContent.add(converter.apply(item));
        }
        return new PageImpl<>(convertedContent, totalElements, number, size);
    }
} 