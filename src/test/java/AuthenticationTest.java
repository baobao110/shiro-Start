import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("Jack","123456","admin");
    }
    @Test
    public void test(){
        //1 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //2 主体提交认证请求
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("Jack","123456");
        subject.login(usernamePasswordToken);
        //认证
        System.out.println(subject.isAuthenticated());
//        //退出
//        subject.logout();
//        System.out.println(subject.isAuthenticated());
        //授权
        subject.checkRole("admin");
    }
}
