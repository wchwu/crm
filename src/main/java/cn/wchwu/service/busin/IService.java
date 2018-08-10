package cn.wchwu.service.busin;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @description:基础接口
 * @reason:
 * @author Chaowu.Wang
 * @date 2018年8月9日 下午6:22:35
 * @since JDK 1.6
 */
@Service
public interface IService<T> {
    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * 
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * 
     * @param key
     * @return
     */
    int delete(Object key);

    /**
     * 根据主键更新实体全部字段，null值会被更新
     * 
     * @param entity
     * @return
     */
    int updateAll(T entity);

    /**
     * 根据主键更新属性不为null的值
     * 
     * @param entity
     * @return
     */
    int updateNotNull(T entity);

    /**
     * 根据Example条件进行查询
     * 
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

    /**
     * 查询单个对象
     * 
     * @param key
     * @return
     */
    public T selectByPrimaryKey(Object key);

    /**
     * 查询全部结果
     * 
     * @return
     */
    List<T> selectAll();

    // TODO 其他...
}
