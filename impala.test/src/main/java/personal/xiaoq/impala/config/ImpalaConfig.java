package personal.xiaoq.impala.config;

/**
 * @Author: qiuqiangqiang
 * @Email: johnney_chiu@163.com
 * @Description:
 * @Date: Created on 2020-04-16 16:46
 * @Version: V1.0.0
 */

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.data.mapper.impala", sqlSessionFactoryRef = "impalaSessionFactory")
public class ImpalaConfig {
    @Value("${spring.impala.url}")
    private String url;

    @Value("${spring.impala.driver-class-name}")
    private String driverClass;

    @Value("${spring.impala.maxActive}")
    private Integer maxActive;

    @Value("${spring.impala.initialSize}")
    private Integer initialSize;

    @Value("${spring.impala.minIdle}")
    private Integer minIdle;

    @Value("${spring.impala.maxWait}")
    private Integer maxWait;

    @Value("${mybatis.type-aliases-package}")
    private String PACKAGE;

    @Value("${mybatis.impala-mapper-locations}")
    private String LOCATION;

    @Bean(name = "impalaDataSource")
    public DataSource impalaDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setMaxActive(maxActive);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        return dataSource;
    }

    @Bean(name = "impalaTransactionManager")
    public DataSourceTransactionManager impalaTransactionManager() {
        return new DataSourceTransactionManager(impalaDataSource());
    }

    @Bean(name = "impalaSessionFactory")
    public SqlSessionFactory impalaSessionFactory() throws Exception {
        final SqlSessionFactoryBean SESSIONFACTORY = new SqlSessionFactoryBean();
        SESSIONFACTORY.setDataSource(impalaDataSource());
        SESSIONFACTORY.setTypeAliasesPackage(PACKAGE);
        System.out.println("------------------->"+PACKAGE);
        System.out.println("---------------------->"+LOCATION);
        SESSIONFACTORY.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(LOCATION));
        SESSIONFACTORY.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);//该配置将数据库中下划线自动转成驼峰命名的变量，并且只针对自定义的实体类生效，对map不生效的
        return SESSIONFACTORY.getObject();
    }

}
