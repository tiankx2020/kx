package com.tkx.shirotest;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @Author tkx
 * @Date 2025 01 13 22 05
 **/
@Slf4j
public class ShiroRun {


    public static void main(String[] args) {
        // 1.初始化获取SecurityManager
        //创建工厂，写入配置文件 shiro.ini的路径
        DefaultSecurityManager factory = new DefaultSecurityManager();
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        factory.setRealm(iniRealm);
        //通过工厂获取相关实例，即factory.getInstance()，然后获取securityManager对象
        //通过工具将securityManager塞入
        SecurityUtils.setSecurityManager(factory);
       //2.通过工具，获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 3.创建token对象，web应用用户名密码从页面传递
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("admin", "admin");
        // 登录
        try {
            subject.login(usernamePasswordToken);
            log.info("登录成功");
        }catch (UnknownAccountException e){
            log.info("用户不存在");
        }catch (IncorrectCredentialsException e){
            log.info("密码错误");
        }
    }
}
