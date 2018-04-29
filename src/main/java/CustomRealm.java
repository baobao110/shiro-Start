import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//自定义Realm类
public class CustomRealm extends AuthorizingRealm {

     private Map<String,String>userMap=new HashMap<String,String>(12);
     {
         //Md5加密的密文
        userMap.put("Jack","0f8608b134d1f6b32d57f2a58956f73a");

        super.setName("customRealm");
    };
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username=(String)principalCollection.getPrimaryPrincipal();
        //从数据库或者缓存中获取角色或者权限信息
        Set<String> roles=getRolesByUsername(username);
        Set<String>permission=getPermissionByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permission);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }
    //模拟从数据库获取权限信息
    public Set<String>getPermissionByUsername(String username){
        Set<String>permission=new HashSet<>();
        permission.add("user:update");
        permission.add("user:delete");
        return permission;
    }
    //模拟从数据库获取角色信息
    public Set<String> getRolesByUsername(String username){
        Set<String> roles=new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       //从主体信息中传过来的信息中获取用户名
        String username=(String)authenticationToken.getPrincipal();

        //2 通过用户名从数据库中获取凭证
        String password=getPasswordByUsername(username);
        if(null==password){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo("Mark",password,"customRealm");
        //密码加盐
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Jack"));
        return simpleAuthenticationInfo;
    }

    //模拟数据库
    public String getPasswordByUsername(String username){
            return userMap.get(username);
    }

    public static void main(String[] args) {
        Md5Hash hash=new Md5Hash("123456","Jack");
        System.out.println(hash.toString());
    }
}
