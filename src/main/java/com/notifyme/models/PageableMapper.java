package com.notifyme.models;

import com.notifyme.model.PageableResult;
import com.notifyme.model.SortResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableMapper {

    public static PageableResult buildPageableDTO(Pageable pageable) {
        final PageableResult pageableDTO = new PageableResult();
        pageableDTO.setPageNumber(pageable.getPageNumber());
        pageableDTO.setPageSize(pageable.getPageSize());
        pageableDTO.setUnpaged(pageable.isUnpaged());
        pageableDTO.setPaged(pageable.isPaged());
        return pageableDTO;
    }

    public static SortResult buildSortDTO(Sort sort) {
        final SortResult sortDTO = new SortResult();
        sortDTO.setEmpty(sort.isEmpty());
        sortDTO.setUnsorted(sort.isUnsorted());
        sortDTO.setSorted(sort.isSorted());

        return sortDTO;
    }
}