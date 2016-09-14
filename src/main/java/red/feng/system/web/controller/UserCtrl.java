package red.feng.system.web.controller;

import java.sql.Timestamp;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import red.feng.system.dao.UserDao;
import red.feng.system.model.SysUser;
import red.feng.system.utils.EndecryptUtils;

/**
 * 用户管理
 * 
 * @author jinxx
 *
 */
@Controller
@RequestMapping("/system/user")
public class UserCtrl {
	@Autowired
	private UserDao userDao;

	@RequestMapping("/list")
	public String userlist(SysUser sysUser, @RequestParam(defaultValue = "1") int p,
			@RequestParam(defaultValue = "10") int size, ModelMap model) {
		PageQuery pageQuery = userDao.query(sysUser, p, size);
		model.addAttribute("page", pageQuery);
		model.addAttribute(sysUser);
		model.addAttribute(p);
		return "/system/user/userlist";
	}

	/**
	 * 添加用户初始化页面 去增加
	 */
	@RequestMapping("/add")
	public String add() {
		return "system/user/add";
	}

	/**
	 * 保存用户 增加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String doadd(SysUser sysUser) {
		if (sysUser.getUserId() == null) {
			sysUser.setIsfreeze(0);
			sysUser.setIslock(0);
			sysUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
			sysUser.setPasswd("000000");
			EndecryptUtils.md5Password(sysUser);
			userDao.addUser(sysUser);
		} else {
			userDao.updateUser(sysUser);
		}
		return "redirect:/system/user/list";
	}

	/**
	 * 
	 * 去修改
	 */
	@RequestMapping("/edit/{userId}")
	public String edit(@PathVariable Integer userId, ModelMap model) {
		SysUser sysuser = userDao.getByUserId(userId);
		model.addAttribute("user", sysuser);
		return "system/user/edit";
	}

	/**
	 * 
	 *  重置密码
	 */
	@RequestMapping("/resetpwd/{userId}")
	@ResponseBody
	public int resetpwd(@PathVariable Integer userId) {
		SysUser sysUser = userDao.getByUserId(userId);
		sysUser.setPasswd("000000");
		EndecryptUtils.md5Password(sysUser);
		userDao.updateUser(sysUser);
		return 1;
	}
}
