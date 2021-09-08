package com.ch.warehouse.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单实体类
 * @author procedure sail
 * @date 2021/7/17 14:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode implements Serializable {
    private static final long serialVersionUID=1L;
    private Integer id;
    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private String checkArr="0";//0代表不选中,1代表选中
    private List<TreeNode> children=new ArrayList<>();

    /**
     * 首页左边导航树的构造器
     */

    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    /**
     * 部门管理
     */

    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }

    /**
     *dtree的复选树构造器
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }

}
