package com.zjl.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zjl.utils.JWTUtils;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 此处拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * 拦截器和过滤器的区别
     * 1.拦截器针对访问控制器进行拦截
     * 及 @RequestMapping(value = {"/test"})
     * 简而言说就是访问方法的url
     * 应用：可以作为权限的判断，
     * 2.过滤器则是针对全局的请求
     * 包括：css/js/html/jpg/png/git/...
     * 及静态文件
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        String token = request.getParameter("sessionValue");
        //Cookie cookie=getCookieByName(request,"_COOKIE_NAME");
        //如果已经登录，不拦截
        if (null != token) {
            //验证token是否正确
            boolean result = JWTUtils.verify(token);
            System.out.println("是否通过拦截器:" + result);
            if (!result) {
                return false;
            }
            return true;
        }
        //如果没有登录，则跳转到登录界面
        else {
            //重定向 第一种 调用控制器 方法
            response.sendRedirect(request.getContextPath() + "/login");
            //重定向 第二种 重定向方法
            //            request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
            //            System.out.println(request.getContextPath());
            return false;
            /**
             * 以下是为了登录成功后返回到刚刚的操作，不跳到主界面
             * 实现：通过将请求URL保存到session的beforePath中，然后在登录时判断beforePath是否为空
             */
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 返回信息给客户端
     *
     * @param response
     * @param out
     * @param apiResponse
     */
    private void responseMessage(HttpServletRequest request, HttpServletResponse response, PrintWriter out, ApiResponse apiResponse) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        out.print(JSONObject.toJSONString(apiResponse));
        out.flush();
        out.close();
    }
}
