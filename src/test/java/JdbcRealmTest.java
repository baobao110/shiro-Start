import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcRealmTest {

    DruidDataSource dataSource=new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }
    @Test
    public void test() {
        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //注意这里的权限开关要开,默认为关
        jdbcRealm.setPermissionsLookupEnabled(true);
        //1 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //2 主体提交认证请求
        Subject subject= SecurityUtils.getSubject();
        //这里要创建数据表users，可以先不建表运行差什么表建什么，或者见 JdbcRealm,插入数据
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("Jack","123456");
        subject.login(usernamePasswordToken);
        //认证
        System.out.println(subject.isAuthenticated());
     /*  //退出
        subject.logout();
       System.out.println(subject.isAuthenticated());*/
        //授权
        subject.checkRole("admin");
        //查看update权限
        subject.checkPermission("user:update");

    }
}
