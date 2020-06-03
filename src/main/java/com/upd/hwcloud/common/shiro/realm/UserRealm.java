package com.upd.hwcloud.common.shiro.realm;


import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserRealm extends AuthorizingRealm{

	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userId=(String)principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.findRolesByUserId(userId));
		authorizationInfo.setStringPermissions(userService.findPermissionsByUserId(userId));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userId=(String)token.getPrincipal();

		User user=userService.findUserByIdcard(userId);
		if(user==null){
			throw new UnknownAccountException();
		}
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(
				user.getIdcard(),
				user.getName(),
				getName());
		return authenticationInfo;
	}

}
