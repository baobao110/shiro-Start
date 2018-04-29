import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

//自定义Realm: IniRealm
public class IniRealmTest {

    @Test
    public void test() {
        IniRealm realm=new IniRealm("classpath:usr.ini");
        //1 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //2 主体提交认证请求
        Subject subject= SecurityUtils.getSubject();
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
