package com.thtm.taigongthtmproject.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.thtm.taigongthtmproject.model.entity.ConcreteStrength;
import com.thtm.taigongthtmproject.service.ConcreteStrengthService;
import com.thtm.taigongthtmproject.util.DateTimeUtils;
import com.thtm.taigongthtmproject.util.ExcelTool;
import com.thtm.taigongthtmproject.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author hanyuhao
 * @PackageName com.thtm.taigongthtmproject.controller
 * @Class ConcreteStrengthController
 * @Date 2021/9/30 11:33
 */
@Slf4j
@RestController
@RequestMapping("/concreteStrength")
@CrossOrigin("http://121.43.158.100:8081")
public class ConcreteStrengthController {

    @Autowired
    private ConcreteStrengthService concreteStrengthService;

    @GetMapping("/jisuan")
    @ApiOperation("计算R28")
    public Result jisuan(Double R7){

        ConcreteStrength concreteStrength = new ConcreteStrength();
        Date date = new Date();
        Double R28 = 1.0514 * R7 + 8.72;
        concreteStrength.setR7(R7);
        concreteStrength.setR28(R28);
        concreteStrength.setCreateTime(date);

        concreteStrengthService.save(concreteStrength);

        return Result.success(concreteStrengthService.selectAll());

    }

    @PostMapping("/piliangjisuan")
    @ApiOperation("批量计算R28")
    public Result piliangjisuan(@RequestBody Map<String, Object> param){

        String logStr = "import";
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            log.info("导入信息：{}", JSONObject.toJSONString(param));
            if (param.containsKey("list")) {
                log.error("{} 开始：", logStr);
                List<Map<String, Object>> list = (List<Map<String, Object>>) param.get("list");
                retMap = concreteStrengthService.piliangimport(list);
                return Result.success(retMap);
            } else {
                return Result.fail("未包含必要的参数");
            }
        } catch (Exception e) {
            log.error("{} 报错：{}", logStr, e);
            return Result.fail("所选excel文件数据不规范！！！");
        }
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        return Result.success(concreteStrengthService.selectAll());
    }

    @GetMapping("/selectR7AndR28")
    public Result selectR7AndR28(){
        return Result.success(concreteStrengthService.selectR7AndR28());
    }

    @ApiOperation("分页查询")
    @GetMapping("/pageConcreteStrength")
    public Result pageConcreteStrength(Page<ConcreteStrength> page , ConcreteStrength concreteStrength){
        return Result.success(concreteStrengthService.pageConcreteStrength(page ,concreteStrength));
    }


    @ApiOperation(value = "下载模板" ,httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/exportDefaultTemplate")
    public void exportDefaultTemplate(HttpServletResponse response, @NotBlank(message = "模板名称不能为空") String moduleNameCn){

        OutputStream out = null;
        InputStream in = null;
        InputStream inNew = null;
        File fi = null;
        try {
            out = response.getOutputStream();
            String fileName = URLEncoder.encode(moduleNameCn, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            try {
                ResourceLoader resourceLoader = new DefaultResourceLoader();
                String realPath = "classpath:templates/excel/" + moduleNameCn;
                in = resourceLoader.getResource(realPath).getInputStream();
                String tmpFileName = "/tmp/files/" + DateTimeUtils.stamp(new Date())
                        + File.separator + fileName.replace("模板-", "");
                log.info("模板导出路径：{}", tmpFileName);
                fi = new File(tmpFileName);
                FileUtils.copyInputStreamToFile(in, fi);
                in.close();
                inNew = new FileInputStream(fi);
            } catch (IOException e) {
                log.error("模板导出文件出错{}", e);
            }
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inNew.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            inNew.close();
        } catch (Exception e) {
            log.error("模板导出操作异常{}", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (inNew != null) {
                    inNew.close();
                }
                if (out != null) {
                    out.close();
                }
                Files.delete(fi.toPath());
                if (fi.exists()) {
                    fi.delete();
                }
            } catch (IOException e) {
                log.error("模板导出关闭IO异常:{}", e);
            }
        }
    }


}
