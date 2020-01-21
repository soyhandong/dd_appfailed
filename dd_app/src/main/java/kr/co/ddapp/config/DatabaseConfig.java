package kr.co.ddapp.config;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Value( "#{ prop_database['DB_USER']}") String DB_USER;
    @Value( "#{ prop_database['DB_PASS']}") String DB_PASS;
    @Value( "#{ prop_database['DB_HOST']}") String DB_HOST;
    @Value( "#{ prop_database['DB_PORT']}") String DB_PORT;
    @Value( "#{ prop_database['DB_NAME']}") String DB_NAME;

    @Bean
    public DataSource dataSourceSpied(){
        BasicDataSource ds_dbcp = new BasicDataSource();
        ds_dbcp.setDriverClassName("com.mysql.jdbc.Driver");
        ds_dbcp.setUsername(DB_USER);
        ds_dbcp.setPassword(DB_PASS);
        ds_dbcp.setUrl("jdbc:mysql://"
                + DB_HOST
                + ":"+DB_PORT
                + "/"+DB_NAME
                +"?zeroDateTimeBehavior=convertToNull" //'0000-00-00 00:00:00' can not be represented as java.sql.Timestamp 에러처리
                +"&amp;allowMultiQueries=true" //마이바티스에서 멀티쿼리 처리용
        );
        return ds_dbcp;
    }
    @Bean
    public PlatformTransactionManager transactionManager(){
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceSpied());
        return transactionManager;
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,
                                                       ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/configuration.xml"));
        Resource[] resources = ArrayUtils.addAll(applicationContext.getResources("classpath:mybatis/mappers/**/*.xml"));
        factoryBean.setMapperLocations(resources);
        return factoryBean;
    }
    /**
     * sqlSession 대신에 sqlSessionTemplate를 사용하면
     * close관리를 하지 않아도 된다. (mybatis-spring)
     *          , 단. transaction은 스프링에서 구현해야한다.
     *          @Transactional 어노태이션 혹은 PlatformTransactionManager 으로 프로그램적으로 구현
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name="mainSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    /**
     * JDBC 템플릿
     * @param dataSource
     * @return
     */
    @Bean(name="mainJdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }






}
