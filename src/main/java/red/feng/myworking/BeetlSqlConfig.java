package red.feng.myworking;

import javax.sql.DataSource;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.PostgresStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class BeetlSqlConfig {
	@Bean
	public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer( ){
		
		BeetlSqlScannerConfigurer beetlScanner=new BeetlSqlScannerConfigurer();
		beetlScanner.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
		beetlScanner.setBasePackage("red.feng.dao.beetlsql");
		beetlScanner.setDaoSuffix("Mapper");
		return beetlScanner;
	}
	
	@Bean(name="sqlManagerFactoryBean") 
	@Autowired
	public SqlManagerFactoryBean sqlManagerFactoryBean(DataSource dataSource){
		SqlManagerFactoryBean factorybean=new SqlManagerFactoryBean();
		factorybean.setDbStyle(new PostgresStyle());
		BeetlSqlDataSource beetlSqlDataSource=	new BeetlSqlDataSource();
		beetlSqlDataSource.setMasterSource(dataSource);
		factorybean.setCs(beetlSqlDataSource);
		factorybean.setNc(new UnderlinedNameConversion());
		factorybean.setInterceptors(new Interceptor[]{new DebugInterceptor()});
		ClasspathLoader sqlLoader=new ClasspathLoader("/sql");
		factorybean.setSqlLoader(sqlLoader);
		return factorybean;
	}
}
