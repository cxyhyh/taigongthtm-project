package com.thtm.taigongthtmproject.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thtm.taigongthtmproject.model.entity.ConcreteStrength;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author hanyuhao
 * @PackageName com.thtm.taigongthtmproject.service.impl
 * @Class ConcreteStrengthService
 * @Date 2021/9/30 13:27
 */
@Service
public interface ConcreteStrengthService extends IService<ConcreteStrength> {

    List<ConcreteStrength> selectAll();

    Map<String, Object> piliangimport(List<Map<String, Object>> list);

    List<List<Double>> selectR7AndR28();

    Page<ConcreteStrength> pageConcreteStrength(Page<ConcreteStrength> page, ConcreteStrength concreteStrength);

}
