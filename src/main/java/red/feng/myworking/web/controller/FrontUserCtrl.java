package red.feng.myworking.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import red.feng.system.dao.UserDao;
import red.feng.system.model.SysUser;

@Controller
public class FrontUserCtrl {
	@Autowired
	private UserDao userDao;
	@RequestMapping("user/{id}")
	@ResponseBody
	public SysUser getUser(@PathVariable Integer id){
		SysUser sysuser = userDao.getByUserId(id);
		return sysuser;
	}
}
