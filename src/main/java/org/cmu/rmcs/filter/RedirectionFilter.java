package org.cmu.rmcs.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cmu.rmcs.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class RedirectionFilter implements Filter{
    private String mainPageUriString="main";
    private static Logger LOGGER = LoggerFactory.getLogger(RedirectionFilter.class);
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request=(HttpServletRequest)(req);
        HttpServletResponse response=(HttpServletResponse)(res);
        HttpSession session=request.getSession();
        String uri=request.getRequestURI();
        User user=(User) session.getAttribute("user");
        System.out.println("uri:"+uri);
        if(uri.endsWith(".jsp")){
            //不能访问,重定向到根目录
            if(!response.isCommitted())response.sendRedirect(mainPageUriString);
            return ;
            
        }
        if( uri.startsWith("/css") ||
            uri.startsWith("/fonts") ||
            uri.startsWith("/js") || 
            uri.startsWith("/img") 
                ){
            
            chain.doFilter(req, res);
            
        }else if(user !=null){
            //未登录
            if(uri.endsWith("login") || uri.equals("/")){
                if(!response.isCommitted())response.sendRedirect(mainPageUriString);
                return ;
                
            }else {
                chain.doFilter(req, res);
            }
        }else {
            // 已登录
            if(uri.endsWith("login") || uri.equals("/")){
                //这两个不拦截
                chain.doFilter(req, res);
                
                
            }else {
               //其他的都定向的根目录
                if(!response.isCommitted())response.sendRedirect("/");//根目录
                return ;
                
            }
            
        }
        
        
    }

    public void destroy() {
        // TODO Auto-generated method stub
        
    }


}
