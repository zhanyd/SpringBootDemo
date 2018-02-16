package com.zhanyd.common.interceptor;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.zhanyd.common.ApiResult;
import com.zhanyd.common.CodeStatus;
import com.zhanyd.common.util.JwtUtils;
import com.zhanyd.common.util.StringHelp;


/**
 * Created by zhanyd@sina.com on 2018/2/16 0016.
 */
public class PermissionInterceptor implements HandlerInterceptor {
    /**
     * 判断是否登录
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        System.out.println("in preHandle");
        String token = request.getHeader("Authorization");
        if(StringHelp.isEmpty(token)){
            responseMessage(response,CodeStatus.TOKEN_ERROR,"token不能为空");
            return false;
        }

        String result = JwtUtils.verifyJWT(token);
        if(result == null){
            responseMessage(response,CodeStatus.TOKEN_ERROR,"token无效或已过期");
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


    /**
     * 发送消息给客户端
     * @param response
     * @param code
     * @param msg
     * @throws IOException
     */
    private void responseMessage(HttpServletResponse response,String code,String msg) throws IOException
    {
        ApiResult apiResult = new ApiResult();

        String message = JSON.toJSONString(apiResult.fail(code, msg));
        response.setHeader("Content-type", "application/json");
        OutputStream stream = response.getOutputStream();
        stream.write(message.getBytes("UTF-8"));
        stream.flush();
        stream.close();
    }


}
