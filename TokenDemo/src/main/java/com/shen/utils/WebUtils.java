package com.shen.utils;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Shen
 * @date 2022/12/18 10:44
 */
public class WebUtils {
    /**
     * 将字符串渲染到客户端（浏览器）
     *
     * @param response 渲染对象
     * @param str 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String str){
        try{
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
            response.getWriter().print(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
