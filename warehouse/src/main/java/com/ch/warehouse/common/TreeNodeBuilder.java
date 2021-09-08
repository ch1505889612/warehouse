package com.ch.warehouse.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author procedure sail
 * @date 2021/7/17 14:24
 * 一级菜单,二级菜单
 */
public class TreeNodeBuilder {
    public static List<TreeNode> build(List<TreeNode> treeNodes,Integer topId){
        List<TreeNode> nodes=new ArrayList<>();
        for (TreeNode n1:treeNodes) {
            if (n1.getPid()==topId){
                nodes.add(n1);
            }
            for (TreeNode n2:treeNodes){
                if (n1.getId()==n2.getPid()){
                    n1.getChildren().add(n2);
                }
            }

        }
        return nodes;
    }
}
