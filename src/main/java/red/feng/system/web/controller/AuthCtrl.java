package red.feng.system.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import red.feng.myworking.SysConstant;
import red.feng.system.dao.UserDao;
import red.feng.system.model.SysUser;

/**
 * 登入，登出
 * 
 * @author jinxx
 *
 */
@Controller
public class AuthCtrl {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/auth/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "auth/login";
	}
	@RequestMapping(value = "/auth/ajaxLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> ajaxLogin(SysUser user,ModelMap model){
		Map<String,String> result=new HashMap<String,String>();
		String status="error";
		String message="用户名或密码不正确";
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPasswd());
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				// token.setRememberMe(true);
				subject.login(token);
				subject.getSession(true).setAttribute(SysConstant.SESSION_USER,
						userDao.findByUsername(user.getUserName()));
			}
			status="success";
			
		} catch (UnknownAccountException e) {
			log.info("用户不存在：{}", user.getUserName());
		} catch (IncorrectCredentialsException e) {
			log.info("密码不匹配：{}", user.getUserName());
		} catch (LockedAccountException e) {
			log.info("用户已被冻结：{}", user.getUserName());
			message="用户已被冻结";
		} catch (ExcessiveAttemptsException e) {
			log.info("连续登录错误5次以上：{}", user.getUserName());
			message="连续登录错误5次";
		} catch (Exception e) {
			log.info("登陆失败", e);
		}
		result.put("status", status);
		result.put("message", message);
		return result;
	}
	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	public String dologin(SysUser user, ModelMap model, ServletRequest request) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPasswd());
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				// token.setRememberMe(true);
				subject.login(token);
				subject.getSession(true).setAttribute(SysConstant.SESSION_USER,
						userDao.findByUsername(user.getUserName()));
			}
			// String url = WebUtils.getSavedRequest(request).getRequestUrl();

			return "redirect:/";
		} catch (UnknownAccountException e) {
			log.info("用户不存在：{}", user.getUserName());
			model.addAttribute("message", "用户名或密码不存在");
		} catch (IncorrectCredentialsException e) {
			log.info("密码不匹配：{}", user.getUserName());
			model.addAttribute("message", "用户名或密码不存在");
		} catch (LockedAccountException e) {
			log.info("用户已被冻结：{}", user.getUserName());
			model.addAttribute("message", "用户已被冻结，请联系管理员");
		} catch (ExcessiveAttemptsException e) {
			log.info("连续登录错误5次以上：{}", user.getUserName());
			model.addAttribute("message", "连续登录错误5次以上，1小时后重试");
		} catch (Exception e) {
			log.info("登陆失败", e);
			model.addAttribute("message", "用户名或密码不存在");
		}
		return "auth/login";
	}

	@RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> logout(RedirectAttributes redirectAttributes) {
		Map<String,String> result=new HashMap<String,String>();
		String status="success";
		String message="登出成功";
		result.put("status", status);
		result.put("message", message);
		// 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		
		return result;
	}

	@RequestMapping("/403")
	public String unauthorizedRole() {
		return "/403";
	}
}
