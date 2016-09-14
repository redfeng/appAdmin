package red.feng.system.dao;

import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import red.feng.system.model.SysUser;

@Repository
public class UserDao  {
	@Autowired
	private SQLManager sqlManager;

	public SysUser findByUsername(String username) {
		SysUser user = sqlManager.selectSingle("user.findByUsername", username, SysUser.class);
		return user;
	}

	public List<String> getRolesName(Integer userId) {

		return null;
	}
	public PageQuery query(SysUser user,int page,int pagesize){
		PageQuery pageQuery=new PageQuery();
		pageQuery.setPageNumber(page);
		pageQuery.setPageSize(pagesize);
		pageQuery.setParas(user);
		sqlManager.pageQuery("user.pageQuery", SysUser.class, pageQuery);
		return pageQuery;
	}
	public void addUser(SysUser user){
		sqlManager.insert(user);
	}
	public SysUser getByUserId(Integer userId){
		return  sqlManager.selectSingle("user.findByUserId", userId, SysUser.class);
	}
	public void updateUser(SysUser user){
		sqlManager.update("user.updateById", user);
	}
}
