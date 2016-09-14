package red.feng.myworking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import red.feng.system.shiro.LimitRetryHashedMatcher;
import red.feng.system.shiro.ShiroDbRealm;
import red.feng.system.shiro.filter.SimpleFormAuthenticationFilter;


@Configuration
public class ShiroConfiguration {
		
	    private static Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();  
	    AuthorizingRealm realm;
	    @Bean(name = "ShiroRealmImpl") 
	    @Autowired
	    public AuthorizingRealm getShiroRealm(ShiroDbRealm shiroDbRealm) {  
	        return shiroDbRealm;  
	    }  
	  
	    @Bean(name = "shiroEhcacheManager")  
	    public EhCacheManager getEhCacheManager() {  
	        EhCacheManager em = new EhCacheManager();  
	        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");  
	        return em;  
	    }  
	  
	    @Bean(name = "lifecycleBeanPostProcessor")  
	    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {  
	        return new LifecycleBeanPostProcessor();  
	    }  
	  
	    @Bean  
	    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {  
	        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();  
	        daap.setProxyTargetClass(true);  
	        return daap;  
	    }  
	  
	    @Bean(name = "securityManager")  
	    @Autowired
	    public DefaultWebSecurityManager getDefaultWebSecurityManager(ShiroDbRealm shiroDbRealm) {  
	        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();  
	        LimitRetryHashedMatcher limitRetryHashedMatcher=new LimitRetryHashedMatcher();
	        limitRetryHashedMatcher.setHashAlgorithmName("md5");
	        limitRetryHashedMatcher.setHashIterations(2);
	        limitRetryHashedMatcher.setStoredCredentialsHexEncoded(true);
	        shiroDbRealm.setCredentialsMatcher(limitRetryHashedMatcher);//设置密码服务
	        dwsm.setRealm(shiroDbRealm);  
	        dwsm.setCacheManager(getEhCacheManager());  
	        return dwsm;  
	    }  
	  
	    @Bean  
	    @Autowired
	    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {  
	        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();  
	        aasa.setSecurityManager(defaultWebSecurityManager);  
	        return new AuthorizationAttributeSourceAdvisor();  
	    }  
	  
	    @Bean(name = "shiroFilter")  
	    @Autowired
	    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {  
	        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
	        shiroFilterFactoryBean  
	                .setSecurityManager(defaultWebSecurityManager);  
	        Map<String, Filter> filters=new HashMap<String, Filter>();
	        filters.put("authc", new SimpleFormAuthenticationFilter());
	        shiroFilterFactoryBean.setLoginUrl("/auth/login");  
	        shiroFilterFactoryBean.setSuccessUrl("/"); 
	        filterChainDefinitionMap.put("/resources/**", "anon"); 
	        filterChainDefinitionMap.put("/auth/login", "anon"); 
	        filterChainDefinitionMap.put("/auth/ajaxLogin", "anon"); 
	        filterChainDefinitionMap.put("/admin/**", "authc");
	        filterChainDefinitionMap.put("/**", "authc");  
	        
	        shiroFilterFactoryBean  
	                .setFilterChainDefinitionMap(filterChainDefinitionMap); 
	        shiroFilterFactoryBean.setFilters(filters);
	        return shiroFilterFactoryBean;  
	    }  
	  
	} 
 
