package org.cmu.rmcs.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cmu.rmcs.pojo.CommonRes;

import org.cmu.rmcs.pojo.User;
import org.cmu.rmcs.service.UserService;
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
    public String mainPage(){
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
    
    
    
}
