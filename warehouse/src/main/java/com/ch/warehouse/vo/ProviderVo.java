package com.ch.warehouse.vo;

import com.ch.warehouse.entity.Customer;
import com.ch.warehouse.entity.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author procedure sail
 * @date 2021/7/17 9:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends Provider {

    private static final long serialVersionUID=1L;

    private Integer page=1;
    private Integer limit=10;

}
