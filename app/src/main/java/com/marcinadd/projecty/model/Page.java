package com.marcinadd.projecty.model;

import java.util.List;

public class Page<T> {

    private List<T> content;
    private Long totalElements;
    private Long totalPages;
    private boolean last;
    private boolean first;
    private Long number;
    private Long numberOfElements;
    private Long size;
    private boolean empty;

    public List<T> getContent() {
        return content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isFirst() {
        return first;
    }

    public Long getNumber() {
        return number;
    }

    public Long getNumberOfElements() {
        return numberOfElements;
    }

    public Long getSize() {
        return size;
    }

    public boolean isEmpty() {
        return empty;
    }
}
