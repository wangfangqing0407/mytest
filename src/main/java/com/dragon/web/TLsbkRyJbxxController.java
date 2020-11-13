package com.dragon.web;

import com.dragon.po.TLsbkRyJbxx;
import com.dragon.po.TestPo;
import com.dragon.service.TLsbkRyJbxxService;
import com.dragon.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/14.
 */
@Controller
@RequestMapping(value = "/lsbk")
public class TLsbkRyJbxxController {

    @Autowired
    private TLsbkRyJbxxService tLsbkRyJbxxService;

    @RequestMapping("/ryjbxx")
    public ResponseEntity<JsonResult> fetchAll() {
        JsonResult jr = new JsonResult();
        try {
            List<TLsbkRyJbxx> jbxxList = tLsbkRyJbxxService.fetchAll();
            jr.setResult(jbxxList);
            jr.setStatus("ok");
        } catch (Exception e) {
            jr.setResult(e.getClass().getName() + ":" + e.getMessage());
            jr.setStatus("error");
            e.printStackTrace();
        }
        return ResponseEntity.ok(jr);
        //第一个参数为返回的数据，第二个参数为返回的状态
        //return new ResponseEntity<Map<String,Object>>(jbxxList, HttpStatus.OK);
    }

    /**
     * 向前台传回MODEL
     * @param model
     * @return
     */
    @RequestMapping("/ryjbxxToModel")
    public String fetchRyjbxxAll(Model model) {
        List<TLsbkRyJbxx> jbxxList = tLsbkRyJbxxService.fetchAll();
        model.addAttribute("jbxx", jbxxList);//将数据放入model中返回页面
        return "ryjbxx";//返回ryjbxx.jsp页面
    }

    /**
     * mybatis使用pagehelper插件
     * 进行分页查询
     * @param model
     * @param start
     * @param size
     * @return
     */
    /*@RequestMapping("/ryjbxxToPage")
    public String fetchRyjbxxAllToPage(Model model,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) {
        List<TLsbkRyJbxx> jbxxList = tLsbkRyJbxxService.fetchAll();
        PageInfo<TLsbkRyJbxx> page = new PageInfo<>(jbxxList);
        model.addAttribute("page", page);
        return "ryjbxxPage";//返回ryjbxx.jsp页面
    }*/

    /**
     * 前台传入一个对象
     * @param po
     * @return
     */
    @RequestMapping(value = "/insert")
    public @ResponseBody Map<String, Object> insert(@RequestBody TestPo po) {
        TestPo a = po;
        Map<String, Object> result = new HashMap<>();
        result.put("succes", "1");
        return result;
    }

}
