package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.*;

import javax.management.Query;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 配置类，它的作用和bean.xml是一致的
 * Spring中的新注解
 *      语法：Configuration
 *          作用：指定当前类是一个配置类
 *      语法：ComponentScan
 *          作用：用于通过注解指定Spring在创建容器时要扫描的包
 *          属性
 *              value：它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包
 *              使用此注解就等同于在xml中配置了
 *              <context:component-scan base-package="jeffrey"></context:component-scan>
 *      语法：Bean
 *          作用：用于把当前方法的返回值作为bean对象存入Spring的IOC容器中
 *          属性
 *              name：用于指定bean的ID，当不写时，默认值为当前方法的名称
 *          细节：当使用注解配置方法时，如果方法有参数，Spring框架就会去容重中查找有没有可用的Bean对象
 *          查找的方式和Autowired注解的作用时一样的
 *      语法：@Import
 *          作用：用于倒入其他的配置类
 *
 */
//@Configuration
@Import(JdbcConfig.class)
@ComponentScan("jeffrey")
@PropertySource("classpath:jdbcConfig.properties")
public class SpringConfiguration {

}
