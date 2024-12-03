package com.notifyme.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.*;

public class PageUtils {

    public static List<Order> createOrder(String[] sort) {
        List<Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        return orders;
    }

    public static Map<String, Object> createResponse(Object data, Page page) {
        Map<String, Object> response = new HashMap<>();
        response.put("content", data);
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("sort", page.getSort().toString().replace(":", ","));

        return response;

    }

    private static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")){
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")){
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
