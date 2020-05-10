package com.zxh.realm;

import com.zxh.pojo.BlogUser;
import com.zxh.service.BloguserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
/*
* 只定义realm，realm是用于密码比较的
* */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private BloguserService bloguserService;

    /**
     *  验证权限，判断用户能否进行这个功能的操作
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     *  验证身份，如登录，判断是否为管理员。
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //使用authenticationToken获取当前用户名
        String username = (String) authenticationToken.getPrincipal();
        //通过用户名查询出用户信息。
        BlogUser blogUser = bloguserService.findBlogUser(username);
        //不为空，表示存在此用户名。
        if(blogUser!=null)
        {
            //创建验证身份的对象，进行用户名和密码的比较。
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogUser.getUsername(),blogUser.getPassword(),"");
            return  authenticationInfo;
        }

        //登录失败
        return null;
    }
}
