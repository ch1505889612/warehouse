package com.ch.warehouse.common;



import com.ch.warehouse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author procedure sail
 * @date 2021/7/10 8:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {

    private User user;
    private List<String> roles;
    private List<String> permissions;
}
