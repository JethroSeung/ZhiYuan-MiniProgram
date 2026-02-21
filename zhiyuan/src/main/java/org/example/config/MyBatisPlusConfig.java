package org.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration // 必须加，标识这是配置类
public class MyBatisPlusConfig {
    private static final Logger log = LoggerFactory.getLogger(MyBatisPlusConfig.class);

    private final DataSource dataSource;

    // 构造器注入数据源（Spring Boot自动配置的数据源）
    public MyBatisPlusConfig(DataSource dataSource) {
        this.dataSource = dataSource;
        log.info("✅ 数据源注入成功：{}", dataSource.getClass().getName());
    }

    // 核心：创建名为sqlSessionFactory的Bean（必须严格匹配名称）
    @Bean(name = "sqlSessionFactory") // 名称必须是sqlSessionFactory，和MapperScan中的引用一致
    public SqlSessionFactory sqlSessionFactory() {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        try {
            // 1. 设置数据源（核心）
            sessionFactory.setDataSource(dataSource);

            // 2. 设置Mapper XML路径（如果没有XML文件，注释这行，避免找不到文件报错）
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));

            // 3. 设置实体类别名包
            sessionFactory.setTypeAliasesPackage("org.example.entity");

            // 4. 添加MP分页插件
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            sessionFactory.setPlugins(interceptor);

            // 5. 生成SqlSessionFactory
            SqlSessionFactory factory = sessionFactory.getObject();
            log.info("✅ sqlSessionFactory 创建成功！");
            return factory;
        } catch (Exception e) {
            log.error("❌ 创建sqlSessionFactory失败：", e);
            throw new RuntimeException("创建sqlSessionFactory失败", e); // 抛出异常，让启动失败并显示原因
        }
    }

    // 创建SqlSessionTemplate（可选，但建议加，避免后续报错）
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}