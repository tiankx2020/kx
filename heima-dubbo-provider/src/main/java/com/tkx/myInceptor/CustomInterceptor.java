package com.tkx.myInceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Author tkx
 * @Date 2025 04 15 23 36
 **/
@Component
public class CustomInterceptor  implements HandlerInterceptor {
    // 在请求处理之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("CustomInterceptor: preHandle - 请求开始处理");

        // 可以在这里添加自定义逻辑，比如权限校验
        String token = request.getHeader("Authorization");
        if (token == null || !token.equals("valid-token")) {
            response.sendRedirect("https://www.baidu.com"); // 设置外部重定向路径
            return false; // 返回 false 表示中断后续处理
        }

        return true; // 返回 true 表示继续执行后续的拦截器或处理器
    }

    // 在请求处理之后，视图渲染之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("CustomInterceptor: postHandle - 请求处理完成，准备渲染视图");
    }

    // 在请求完全完成后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("CustomInterceptor: afterCompletion - 请求完成，资源清理");
    }
    //
    public long countFairPairs(int[] nums, int lower, int upper) {
        long result =  0;
        Arrays.sort(nums);
        int k=0,i=k+1,j=nums.length-1;
        for(; k <nums.length-1; k++){
            // 第一步往前走
            if(i==k+1){
                for(;i<=j;i++){
                    if(nums[k]+nums[i]>=lower) break;
                }
            }else { // 之后往后走
                for(;i>k&&i<=j;i--){
                    if(nums[k]+nums[i]>=lower) break;
                }
            }

            for(;j>=i&&j>k;j--){
                if(nums[k]+nums[j]<=upper) break;
            }
            result+=j-i+1;
        }
        return result;
    }
}
