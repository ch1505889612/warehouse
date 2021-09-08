package com.ch.warehouse.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author procedure sail
 * @date 2021/9/5 20:39
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ECharts {
    String name;
    String type;
    String stack;
    List<Integer> data=new ArrayList<>();
}
