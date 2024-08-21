package com.dain_review.global.model;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private Long totalElements;
    private int totalPages;
}
