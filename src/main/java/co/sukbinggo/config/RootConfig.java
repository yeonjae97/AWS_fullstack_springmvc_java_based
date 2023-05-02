package co.sukbinggo.config;


import java.util.Properties;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j;


@Configuration
@ComponentScan({"co.sukbinggo.service", "co.sukbinggo.domain", "co.sukbinggo.task"})
@MapperScan("co.sukbinggo.mapper")
@PropertySource("classpath:/jdbc.properties")
@Log4j
public class RootConfig {

//	@Autowired
//	private Properties properties;
	
	@Value("${db.classname}")
	private String driverClassName;
	@Value("${db.url}")
	private String jdbcUrl;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	
	@Bean
	public DataSource dataSource(){
		
		HikariConfig config = new HikariConfig();
		
		Properties properties = new Properties();
//		properties.load(new ClassPathResource("jdbc.properties").getInputStream());
//		new ClassPathResource("jdbc.properties");
//		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
//		config.setJdbcUrl("jdbc:log4jdbc:mariadb://np.sukbinggo.co.kr:3306/spring_db");
//		config.setUsername("SAMPLE");
//		config.setPassword("1234");
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
//		log.warn(config);
//		config.setDataSourceProperties(properties);
//		Properties properties.setProperty("location", "classpath:/jdbc.properties");
//		properties.get
		return new HikariDataSource(config);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean bean =  new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setTypeAliasesPackage("co.sukbinggo.domain");
		return (SqlSessionFactory) bean.getObject();
		
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager(){
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public Gson gson(){	
		return new Gson();
	}
}
