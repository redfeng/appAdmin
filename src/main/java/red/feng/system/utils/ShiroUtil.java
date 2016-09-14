package red.feng.system.utils;

import org.apache.shiro.SecurityUtils;

import red.feng.myworking.SysConstant;
import red.feng.system.model.SysUser;

public class ShiroUtil {
	public static SysUser getCurrentUser(){
		return (SysUser) SecurityUtils.getSubject().getSession().getAttribute(SysConstant.SESSION_USER);
	}
}
