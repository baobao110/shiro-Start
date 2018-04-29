import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

//自定义的Realm测试
public class CustomRealTest {
    @Test
    public void test(){
        CustomRealm customRealm=new CustomRealm();
        //1 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //shiro加密
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        //设置加密算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置加密次数
        hashedCredentialsMatcher.setHashIterations(1);
        //自定义的Realm设置加密
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
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
//        subject.checkRole("admin");
        subject.checkRole("admin");
        //查看update权限
        subject.checkPermission("user:update");
    }
}
