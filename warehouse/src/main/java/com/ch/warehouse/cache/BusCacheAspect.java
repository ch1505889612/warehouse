package com.ch.warehouse.cache;

import com.ch.warehouse.entity.Customer;
import com.ch.warehouse.entity.Goods;
import com.ch.warehouse.entity.Provider;
import com.ch.warehouse.vo.CustomerVo;
import com.ch.warehouse.vo.GoodsVo;
import com.ch.warehouse.vo.ProviderVo;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author procedure sail
 * @date 2021/7/22 19:49
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class BusCacheAspect {

    /**
     * 日志处理
     */
    private Log log= LogFactory.getLog(BusCacheAspect.class);
          //  LogFactory.getLog(CacheAspect.class);

    //声明一个缓冲容器
    private Map<String,Object> CACHE_CONTAINER=new HashMap<>();

    //声明切面表达式
    private static final String POINTCUT_CUSTOMER_ADD="execution(* com.ch.warehouse.service.Impl.CustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_UPDATE="execution(* com.ch.warehouse.service.Impl.CustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_GET="execution(* com.ch.warehouse.service.Impl.CustomerServiceImpl.getId(..))";
    private static final String POINTCUT_CUSTOMER_DELETE="execution(* com.ch.warehouse.service.Impl.CustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_BATCHDELETE="execution(* com.ch.warehouse.service.Impl.CustomerServiceImpl.removeByIds(..))";

    private static final String CACHE_CUSTOMER_RPOFIX="customer:";


    /**
     * 客户添加切入
     */
    @Around(value = POINTCUT_CUSTOMER_ADD)
    public Object cacheCustomerADD(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Customer object= (Customer) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_CUSTOMER_RPOFIX+object.getId(),object);
        }
        return res;
    }


    /**
     * 查询切入
     */
    @Around(value = POINTCUT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
     Integer object= (Integer) joinPoint.getArgs()[0];
     //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_RPOFIX + object);
        if (res1!=null){
            log.info("已从缓存里面找到客户对象");
            return res1;
        }else {
            log.info("未从缓存里面找到客户对象,去数据库里面查询");
            Customer res2 = (Customer) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_CUSTOMER_RPOFIX+res2.getId(),res2);
            return res2;
        }

    }


    /**
     * 修改切入
     */
    @Around(value = POINTCUT_CUSTOMER_UPDATE)
    public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        CustomerVo customerVo= (CustomerVo) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            Customer customer = (Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_RPOFIX + customerVo.getId());
              if (customer==null){
                  customer=new Customer();
                  BeanUtils.copyProperties(customerVo,customer);
                  log.info("客户对象缓存已更新"+CACHE_CUSTOMER_RPOFIX+customer.getId());
                  CACHE_CONTAINER.put(CACHE_CUSTOMER_RPOFIX+customer.getId(),customer);
              }
        }
      return isSuccess;
    }

    /**
     * 删除切入
     */
    @Around(value = POINTCUT_CUSTOMER_DELETE)
    public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_CUSTOMER_RPOFIX+id);
        }
        return isSuccess;
    }


    /**
     * 批量删除切入
     */
    @Around(value = POINTCUT_CUSTOMER_BATCHDELETE)
    public Object cacheCustomerBatchDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        @SuppressWarnings("unchecked")
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
          for (Serializable id:idList){
              //删除缓存
              CACHE_CONTAINER.remove(CACHE_CUSTOMER_RPOFIX+ idList);
              log.info("客户对象已删除"+CACHE_CUSTOMER_RPOFIX+ idList);
          }
        }
        return isSuccess;
    }



    //声明切面表达式
    private static final String POINTCUT_PRIVODER_ADD="execution(* com.ch.warehouse.service.Impl.ProviderServiceImpl.save(..))";
    private static final String POINTCUT_PRIVODER_UPDATE="execution(* com.ch.warehouse.service.Impl.ProviderServiceImpl.updateById(..))";
    private static final String POINTCUT_PRIVODER_GET="execution(* com.ch.warehouse.service.Impl.ProviderServiceImpl.getId(..))";
    private static final String POINTCUT_PRIVODER_DELETE="execution(* com.ch.warehouse.service.Impl.ProviderServiceImpl.removeById(..))";
    private static final String POINTCUT_PRIVODER_BATCHDELETE="execution(* com.ch.warehouse.service.Impl.ProviderServiceImpl.removeByIds(..))";

    private static final String CACHE_PRIVODER_RPOFIX="provider:";


    /**
     * 客户添加切入
     */
    @Around(value = POINTCUT_PRIVODER_ADD)
    public Object cacheProviderADD(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Provider object= (Provider) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_PRIVODER_RPOFIX+object.getId(),object);
        }
        return res;
    }


    /**
     * 查询切入
     */
    @Around(value = POINTCUT_PRIVODER_GET)
    public Object cacheProviderGet(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer object= (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_PRIVODER_RPOFIX + object);
        if (res1!=null){
            log.info("已从缓存里面找到客户对象");
            return res1;
        }else {
            log.info("未从缓存里面找到客户对象,去数据库里面查询");
            Provider res2 = (Provider) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_PRIVODER_RPOFIX+res2.getId(),res2);
            return res2;
        }

    }


    /**
     * 修改切入
     */
    @Around(value = POINTCUT_PRIVODER_UPDATE)
    public Object cacheProviderUpdate(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        ProviderVo providerVo= (ProviderVo) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            Provider provider = (Provider) CACHE_CONTAINER.get(CACHE_PRIVODER_RPOFIX + providerVo.getId());
            if (provider==null){
                provider=new Provider();
                BeanUtils.copyProperties(providerVo,provider);
                log.info("客户对象缓存已更新"+CACHE_PRIVODER_RPOFIX+provider.getId());
                CACHE_CONTAINER.put(CACHE_PRIVODER_RPOFIX+provider.getId(),provider);
            }
        }
        return isSuccess;
    }

    /**
     * 删除切入
     */
    @Around(value = POINTCUT_PRIVODER_DELETE)
    public Object cacheProviderDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_PRIVODER_RPOFIX+id);
        }
        return isSuccess;
    }


    /**
     * 批量删除切入
     */
    @Around(value = POINTCUT_PRIVODER_BATCHDELETE)
    public Object cacheProviderBatchDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        @SuppressWarnings("unchecked")
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            for (Serializable id:idList){
                //删除缓存
                CACHE_CONTAINER.remove(CACHE_PRIVODER_RPOFIX+ idList);
                log.info("客户对象已删除"+CACHE_PRIVODER_RPOFIX+ idList);
            }
        }
        return isSuccess;
    }



    //声明切面表达式
    private static final String POINTCUT_GOODS_ADD="execution(* com.ch.warehouse.service.Impl.GoodsServiceImpl.save(..))";
    private static final String POINTCUT_GOODS_UPDATE="execution(* com.ch.warehouse.service.Impl.GoodsServiceImpl.updateById(..))";
    private static final String POINTCUT_GOODS_GET="execution(* com.ch.warehouse.service.Impl.GoodsServiceImpl.getId(..))";
    private static final String POINTCUT_GOODS_DELETE="execution(* com.ch.warehouse.service.Impl.GoodsServiceImpl.removeById(..))";
    
    private static final String CACHE_GOODS_RPOFIX="goods:";


    /**
     * 客户添加切入
     */
    @Around(value = POINTCUT_GOODS_ADD)
    public Object cacheGoodsADD(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Goods object= (Goods) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_GOODS_RPOFIX+object.getId(),object);
        }
        return res;
    }


    /**
     * 查询切入
     */
    @Around(value = POINTCUT_GOODS_GET)
    public Object cacheGoodsGet(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer object= (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_GOODS_RPOFIX + object);
        if (res1!=null){
            log.info("已从缓存里面找到客户对象");
            return res1;
        }else {
            log.info("未从缓存里面找到客户对象,去数据库里面查询");
            Goods res2 = (Goods) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_GOODS_RPOFIX+res2.getId(),res2);
            return res2;
        }

    }


    /**
     * 修改切入
     */
    @Around(value = POINTCUT_GOODS_UPDATE)
    public Object cacheGoodsUpdate(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        GoodsVo goodsVo= (GoodsVo) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            Goods goods = (Goods) CACHE_CONTAINER.get(CACHE_GOODS_RPOFIX + goodsVo.getId());
            if (goods==null){
                goods=new Goods();
                BeanUtils.copyProperties(goodsVo,goods);
                log.info("客户对象缓存已更新"+CACHE_GOODS_RPOFIX+goods.getId());
                CACHE_CONTAINER.put(CACHE_GOODS_RPOFIX+goods.getId(),goods);
            }
        }
        return isSuccess;
    }

    /**
     * 删除切入
     */
    @Around(value = POINTCUT_GOODS_DELETE)
    public Object cacheGoodsDelete(ProceedingJoinPoint joinPoint)throws Throwable{
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess= (Boolean) joinPoint.proceed();
        if (isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_GOODS_RPOFIX+id);
        }
        return isSuccess;
    }


}
