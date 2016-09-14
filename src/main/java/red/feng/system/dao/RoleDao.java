package red.feng.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zqxx.common.dao.hibernate4.HibernateBaseDaoImpl;

import red.feng.system.model.Role;

@Repository
public class RoleDao extends HibernateBaseDaoImpl<Role, Integer>{

	public List<String> getResUrls(String roleId){
		return null;
	}
}
