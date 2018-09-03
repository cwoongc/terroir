package com.elevenst.common.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages="com.elevenst.terroir.product.registration", sqlSessionFactoryRef = "tmallSessionFactory")
public class DataBaseConfig {
    @Value("${service.conf.tamll-config-location}")
    private Resource resource;
    @Value("${service.conf.tmall-mapper-location}")
    private Resource[] mappers;

    @Bean(name="tmallDataSource")
    @Primary
    @ConfigurationProperties(prefix = "eleven.tmall")
    public DataSource getTmallDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tmallTransactionManager")
    public DataSourceTransactionManager tmallTransactionManager(){
        return new DataSourceTransactionManager(getTmallDataSource());
    }

    @Bean(name = "tmallSessionFactory")
    public SqlSessionFactory tmallSessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setDataSource(getTmallDataSource());
        sqlSessionFactoryBean.setConfigLocation(resource);
        sqlSessionFactoryBean.setMapperLocations(mappers);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.elevenst.terroir.product.registration");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="tmallSession", destroyMethod = "clearCache")
    public SqlSessionTemplate tmallSessionTemplate() throws Exception{
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(tmallSessionFactory(), ExecutorType.BATCH);
        return sqlSessionTemplate;
    }
}
