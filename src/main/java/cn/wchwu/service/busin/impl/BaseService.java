package cn.wchwu.service.busin.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.wchwu.service.busin.IService;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description:(TODO 请在此添加描述)
 * @reason:
 * @author Chaowu.Wang
 * @date 2018年8月9日 下午6:23:00
 * @since JDK 1.6
 */
@Service
public abstract class BaseService<T> implements IService<T> {

    public abstract Mapper<T> getMapper();

    public int save(T entity) {
        return getMapper().insertSelective(entity);
    }

    public int delete(Object key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public int updateAll(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    public int updateNotNull(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    public List<T> selectByExample(Object example) {
        return getMapper().selectByExample(example);
    }

    public T selectByPrimaryKey(Object key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public List<T> selectAll() {
        return getMapper().selectAll();
    }
    // TODO 其他...
}
