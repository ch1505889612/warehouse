package com.ch.warehouse.cache;

import com.ch.warehouse.entity.Dept;
import com.ch.warehouse.entity.User;
import com.ch.warehouse.vo.DeptVo;
import com.ch.warehouse.vo.UserVo;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author procedure sail
 * @date 2021/7/22 19:49
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    /**
     * 日志处理
     */
    private Log log= LogFactory.getLog(CacheAspect.class);
          //  LogFactory.getLog(CacheAspect.class);

    //声明一个缓冲容器
    private Map<String,Object> CACHE_CONTAINER=new HashMap<>();

    //声明切面表达式
    private static final String POINTCUT_DEPT_ADD="execution(* com.ch.warehouse.service.Impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_UPDATE="execution(* com.ch.warehouse.service.Impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET="execution(* com.ch.warehouse.service.Impl.DeptServiceImpl.getId(..))";
    private static final String POINTCUT_DEPT_DELETE="execution(* com.ch.warehouse.service.Impl.DeptServiceImpl.removeById(..))";

    private static final String CACHE_DEPT_RPOFIX="dept:";


    /**
     * 部门添加切入
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptADD(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Dept object= (Dept) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_DEPT_RPOFIX+object.getId(),object);
        }
        return res;
    }


    /**
     * 查询切入
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
     Integer object= (Integer) joinPoint.getArgs()[0];
     //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_RPOFIX + object);
        if (res1!=null){
            log.info("已从缓存里面找到部门对象");
            return res1;
        }else {
            log.info("未从缓存里面找到部门对象,去数据库里面查询");
            Dept res2 = (Dept) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_RPOFIX+res2.getId(),res2);
            return res2;
        }

    }


    /**
     * 修改切入
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        DeptVo deptVo= (DeptVo) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_RPOFIX + deptVo.getId());
              if (dept==null){
                  dept=new Dept();
                  BeanUtils.copyProperties(deptVo,dept);
                  log.info("部门对象缓存已更新"+CACHE_DEPT_RPOFIX+dept.getId());
                  CACHE_CONTAINER.put(CACHE_DEPT_RPOFIX+dept.getId(),dept);
              }
        }
      return isSuccess;
    }

    /**
     * 删除切入
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_DEPT_RPOFIX+id);
        }
        return isSuccess;
    }

    /**
     * 用户切面
     */
    private static final String POINTCUT_USER_ADD="execution(* com.ch.warehouse.service.Impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_UPDATE="execution(* com.ch.warehouse.service.Impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GET="execution(* com.ch.warehouse.service.Impl.UserServiceImpl.getId(..))";
    private static final String POINTCUT_USER_DELETE="execution(* com.ch.warehouse.service.Impl.UserServiceImpl.removeById(..))";

    private static final String CACHE_USER_RPOFIX="user:";

    /**
     * 用户添加切入
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserADD(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        User user= (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_USER_RPOFIX+user.getId(),user);
        }
        return res;
    }


    /**
     * 查询切入
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer object= (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_USER_RPOFIX + object);
        if (res1!=null){
            log.info("已从缓存里面找到用户对象");
            return res1;
        }else {
            log.info("未从缓存里面找到用户对象,去数据库里面查询");
            User res2 = (User) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_USER_RPOFIX+res2.getId(),res2);
            return res2;
        }

    }


    /**
     * 修改切入
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        User userVo= (User) joinPoint.getArgs()[0];
        System.out.println(userVo+"--------------------");
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        System.out.println(CACHE_USER_RPOFIX+userVo.getId()+"CACHE_USER_RPOFIX+userVo.getId()");
        if (isSuccess){
            User user = (User) CACHE_CONTAINER.get(CACHE_USER_RPOFIX + userVo.getId());
            System.out.println("users"+user);
            if (user==null){
                user=new User();
                BeanUtils.copyProperties(userVo,user);
                log.info("用户对象缓存已更新"+CACHE_USER_RPOFIX+userVo.getId());
                CACHE_CONTAINER.put(CACHE_USER_RPOFIX+userVo.getId(),user);
           }
        }
        System.out.println(isSuccess+"isSuccess+--------------------");
        return isSuccess;
    }

    /**
     * 删除切入
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_USER_RPOFIX+id);
        }
        return isSuccess;
    }
}
