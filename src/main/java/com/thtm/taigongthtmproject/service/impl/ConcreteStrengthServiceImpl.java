package com.thtm.taigongthtmproject.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thtm.taigongthtmproject.mapper.ConcreteStrengthMapper;
import com.thtm.taigongthtmproject.model.dto.ConcreteStrengthDTO;
import com.thtm.taigongthtmproject.model.entity.ConcreteStrength;
import com.thtm.taigongthtmproject.service.ConcreteStrengthService;
import com.thtm.taigongthtmproject.util.DateTimeUtils;
import com.thtm.taigongthtmproject.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author hanyuhao
 * @PackageName com.thtm.taigongthtmproject.service.impl
 * @Class ConcreteStrengthServiceImpl
 * @Date 2021/9/30 13:29
 */
@Service
@Slf4j
public class ConcreteStrengthServiceImpl extends ServiceImpl<ConcreteStrengthMapper, ConcreteStrength> implements ConcreteStrengthService {

    @Autowired
    private ConcreteStrengthMapper concreteStrengthMapper;

    @Override
    public List<ConcreteStrength> selectAll() {
        return concreteStrengthMapper.selectAll();
    }

    @Override
    public Map<String, Object> piliangimport(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        //校验数据
        List<String> error = Lists.newArrayList();
        StringBuffer message = null;
        for (int i = 0; i < list.size(); i++) {
            message = new StringBuffer();
            message.append(i + "#");
            Map<String, Object> param = list.get(i);
            log.info("第{}条,内容：{}", i + 1, JSONObject.toJSONString(param));
            message = ValidUtils.judgeKongParam(message, param, "R27", "R27");

            if (message.toString().contains("@")) {
                error.add(message.toString());
            }
        }
            if (!error.isEmpty()) {
                map.put("code", 400);
                map.put("status", "fail");
                map.put("message", error);
                return map;
            }
            List<ConcreteStrength> resultList = JSON.parseArray(JSON.toJSONString(list), ConcreteStrength.class);
            return piliangimports(resultList);

    }

    @Override
    public   List<List<Double>> selectR7AndR28() {

        List<ConcreteStrength> list = concreteStrengthMapper.selectR7AndR28();

        Map<Object,Object> hashMap = new HashMap<>();
        List<List<Double>> list1 = new ArrayList<>();
        for (ConcreteStrength concreteStrength:list) {
            List<Double> alist = new ArrayList<>();
            alist.add(0, concreteStrength.getR7());
            alist.add(1,concreteStrength.getR28());
            list1.add(alist);
        }

        return list1;
    }

    @Override
    public Page<ConcreteStrength> pageConcreteStrength(Page<ConcreteStrength> page, ConcreteStrength concreteStrength) {
        return concreteStrengthMapper.pageConcreteStrength(page,concreteStrength);
    }


    private Map<String, Object> piliangimports(List<ConcreteStrength> list) {

        Map<String, Object> retMap = Maps.newHashMap();
        StringBuffer ret = new StringBuffer("站室批量入库情况：");
        try {
            for (ConcreteStrength concreteStrength : list) {

                Date date = new Date();
                Double R28 = 1.0514 * (concreteStrength.getR7()) + 8.72;
                concreteStrength.setR7(concreteStrength.getR7());
                concreteStrength.setR28(R28);
                concreteStrength.setCreateTime(date);

                boolean save = this.save(concreteStrength);
                if (save) {
                    retMap.put("result", list);
                    retMap.put("code", 200);
                    retMap.put("status", "success");
                } else {
                    retMap.put("result", save);
                    retMap.put("code", 400);
                    retMap.put("status", "fail");
                    retMap.put("message", "批量入库失败");
                }
            }
            return retMap;
        } catch (Exception e) {
            log.error("砼抗压强度推算分析信息导入错误:{}", e);
            ret.append("砼抗压强度推算分析信息导入错误;");
            retMap.put("status", "fail");
            retMap.put("message", ret.toString());
            return retMap;
        }
    }


}