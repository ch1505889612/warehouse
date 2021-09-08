package com.ch.warehouse.vo;

import com.ch.warehouse.entity.Dept;
import com.ch.warehouse.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author procedure sail
 * @date 2021/7/17 9:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {

    private static final long serialVersionUID=1L;

    private Integer page=1;
    private Integer limit=10;

}
