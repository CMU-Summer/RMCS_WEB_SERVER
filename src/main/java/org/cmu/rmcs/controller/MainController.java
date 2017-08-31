package org.cmu.rmcs.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cmu.rmcs.pojo.CommonRes;
import org.cmu.rmcs.pojo.FamilyMapRes;
import org.cmu.rmcs.pojo.FamilyRes;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.User;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.service.UserService;
import org.cmu.rmcs.util.ContantUtil;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;



@Controller
@RequestMapping("/")
public class MainController {

    @Resource
    private UserService userServiceImp;
    @Resource
    private RedisService redisServiceImp ;
    
    
    @RequestMapping(value ="/",method = {
            RequestMethod.POST, RequestMethod.GET })
    public String getWelcomePage(   
            HttpServletRequest req,
            HttpServletResponse res ){
        return "login";
    }
    @RequestMapping(value ="/login",method = {
            RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JSONObject login(   
            
            HttpServletRequest req,
            HttpServletResponse res ){
        String userName=req.getParameter("passport");
        String password=req.getParameter("password");
        CommonRes commonRes = new CommonRes();
        if(StringUtil.isBlank(userName) || StringUtil.isBlank(password)){
            
            commonRes.setSucceed(false);
            commonRes.setDes("userName and password cannot be empty!");
        }else {
            if(userServiceImp.isUser(userName, password)){
                //返回真，然后塞user
                commonRes.setSucceed(true);
                commonRes.setDes("logined");
                User user=userServiceImp.getUser(userName, password);
                req.getSession().setAttribute("user",user);
            }else{
                commonRes.setSucceed(false);
                commonRes.setDes("userName or  password is wrong!");
            }
            
            
        }
        return JSONObject.parseObject(JSONObject.toJSONString(commonRes));
        
        
       
    }
    
    
    @RequestMapping(value ="/main",method = {
            RequestMethod.POST, RequestMethod.GET })
    public String mainPage(
            HttpServletRequest req,
            HttpServletResponse res){
        req.setAttribute("ip_", req.getLocalAddr());
        req.setAttribute("port_", req.getLocalPort());
        return "welcome";

    }
    @RequestMapping(value ="/logOut",method = {
            RequestMethod.POST, RequestMethod.GET })
    public String logOut( HttpServletRequest req,
            HttpServletResponse res){
        //清理session
        req.getSession().removeAttribute("user");
        
        return "login";

    }
    
    //添加某个group，这个先完成，后面两个可以
    @RequestMapping(value ="/addGroup",method = {
            RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JSONObject addGroup(
            @RequestBody
            GroupStruct g
          
            
      ){
        
        CommonRes commonRes=new CommonRes();
        String groupName=g.getName()+ContantUtil.POSTFIX_GROUP_KEY;
         if(redisServiceImp.isGroupInCache(groupName)){
             //如果有,_g后缀在后端加，填写的时候注意不允许他填写_g
             commonRes.setDes("group already exists");
             commonRes.setSucceed(false);
             
         }else {
             //没有
           g.setName(g.getName()+ContantUtil.POSTFIX_GROUP_KEY);
           boolean issucceed=  redisServiceImp.addGroupToCache(g);
           if(issucceed)commonRes.setDes("add group successfully! please wait for group menu reflush!");  
           else   commonRes.setDes("add group failed!");  
           commonRes.setSucceed(issucceed);
         }
        
        
        return JSONObject.parseObject(JSONObject.toJSONString(commonRes));
        
        
    }
    //删除某个group
    @RequestMapping(value ="/deleteGroup",method = {
            RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JSONObject deleteGroup(
   
            HttpServletRequest req
            
      ){
        String groupName = req.getParameter("groupName");
        
        CommonRes commonRes=new CommonRes();
        boolean isSucceed=false;
        if(groupName.endsWith(ContantUtil.POSTFIX_FIXED_GROUP_KEY)){
            //以_fix结尾的，不能删
            isSucceed=false;
            commonRes.setDes("fixed group cannot be deleted!");  
        }else {
            isSucceed = redisServiceImp.deleteGroupIncache(groupName);
            if(isSucceed){
                commonRes.setDes("delete group successfully! please wait for group menu reflush!");  
            }else {
                commonRes.setDes("delete group failed!");  
            }
            
        }
        commonRes.setSucceed(isSucceed);
        return JSONObject.parseObject(JSONObject.toJSONString(commonRes));
        
        
    }
    
  //获取family和它名下的name
    @RequestMapping(value ="/getFamilyAndItNames",method = {
            RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JSONObject getFamilyAndItNames(
            HttpServletRequest req
            
      ){
        
       Map<String, ArrayList<String>> familyMap= redisServiceImp.getFamilyAndItsNames() ;
       FamilyMapRes familyMapRes=new FamilyMapRes();
       
       Iterator<Entry<String, ArrayList<String>>> iterator=familyMap.entrySet().iterator();
       while(iterator.hasNext()){
          Entry<String, ArrayList<String>> entry=iterator.next();
          FamilyRes familyRes=new FamilyRes();
          familyRes.setName(entry.getKey());
          familyRes.setNameList(entry.getValue());
          familyMapRes.getList().add(familyRes);
           
       }
       
        return JSONObject.parseObject(JSONObject.toJSONString(familyMapRes));
        
        
    }
    
    
    
    //查询某个module的历史
    @RequestMapping(value ="/historyOfGroupModule",method = {
            RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JSONObject historyOfGroupModule(
            @RequestBody 
            HttpServletRequest req
            
      ){
        //这是个抽数据库的工作，暂时没有
        return null;
        
        
    }
    
    
}
