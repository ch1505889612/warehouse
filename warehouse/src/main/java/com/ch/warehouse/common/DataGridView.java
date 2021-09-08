package com.ch.warehouse.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author procedure sail
 * @date 2021/7/17 9:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView<T> {
    private Integer code=0;
    private String msg;
    private Long count;
    private T data;

    public DataGridView(String msg) {
        this.msg = msg;
    }

    public DataGridView(Long count, T data) {
        this.count = count;
        this.data = data;
    }

    public DataGridView(T data) {
        this.data = data;
    }

}
