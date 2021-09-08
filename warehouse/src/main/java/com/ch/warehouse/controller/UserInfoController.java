package com.ch.warehouse.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ch.warehouse.common.AppFileUtils;
import com.ch.warehouse.common.Constast;
import com.ch.warehouse.common.DataGridView;
import com.ch.warehouse.entity.District;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.entity.Userinfo;
import com.ch.warehouse.service.DistrictService;
import com.ch.warehouse.service.UserService;
import com.ch.warehouse.service.UserinfoService;
import com.ch.warehouse.utils.ServerResponseEnum;
import com.ch.warehouse.utils.ServerResponseVO;
import com.ch.warehouse.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

/**
 * @author procedure sail
 * @date 2021/7/27 9:53
 */
@RequestMapping("userinfo")
@RestController
public class UserInfoController {
    @Autowired
    private UserinfoService userinfoService;

    @Autowired
    private DistrictService districtService;


    /**
     * 用户个人资料
     */
    @PostMapping("queryUserInfo")
    public DataGridView updateUserInfo(){
        try {
            User user = (User) WebUtils.getSession().getAttribute("user");
            String remark = user.getRemark();
            Userinfo users = this.userinfoService.getById(user.getId());
            List<Object> list = new ArrayList<>();
            list.add(remark);
            list.add(users);

            return new DataGridView(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("district")
    public DataGridView district(Integer pid){
        QueryWrapper<District> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(pid!=null,"pid",pid);
        List<District> list = this.districtService.list(queryWrapper);
        return new DataGridView(list);
    }

    @GetMapping("districts")
    public DataGridView districts(Integer[] ids){
        List<Integer> idList=new ArrayList<>();
        for (Integer id:ids){
            idList.add(id);
        }
        List<District> list = this.districtService.listByIds(idList);
        return new DataGridView(list);
    }

    @PostMapping("updateUserInfo")
    public ServerResponseVO updateUserInfo(Userinfo userinfo){
        User user = (User) WebUtils.getSession().getAttribute("user");
        try {
                   //头像地址不为空并且后缀名为_temp的才能进行修改
                  if (userinfo.getHeadimg()!=null && userinfo.getHeadimg().endsWith("_temp")) {
                     //判断是否是原头像
                    if (!(userinfo.getHeadimg().equals(Constast.IMGPATH+user.getImgpath()))){
                        String newName = AppFileUtils.renameFile(userinfo.getHeadimg());
                        userinfo.setHeadimg(newName);
                         //根据老路径删除内存中的图片
                        String imgpath = userinfoService.getById(userinfo.getId()).getHeadimg();
                        AppFileUtils.removeFileByHeadImgPath(imgpath);
                    }
                  }
                 this.userinfoService.updateById(userinfo);
                 return new ServerResponseVO(ServerResponseEnum.UPDATE_SUCCESS);

         }catch (Exception e){
             e.printStackTrace();
            return new ServerResponseVO(ServerResponseEnum.UPDATE_ERROR);
         }

    }

}
