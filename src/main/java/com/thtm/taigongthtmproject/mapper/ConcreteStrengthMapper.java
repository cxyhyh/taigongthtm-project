package com.thtm.taigongthtmproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thtm.taigongthtmproject.model.entity.ConcreteStrength;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author hanyuhao
 * @PackageName com.thtm.taigongthtmproject.mapper
 * @Class ConcreteStrengthMapper
 * @Date 2021/9/30 11:33
 */
@Repository
@Mapper
public interface ConcreteStrengthMapper extends BaseMapper<ConcreteStrength> {
    List<ConcreteStrength> selectAll();

    List<ConcreteStrength> selectR7AndR28();

    Page<ConcreteStrength> pageConcreteStrength(Page<ConcreteStrength> page,  @Param("param") ConcreteStrength concreteStrength);
}
