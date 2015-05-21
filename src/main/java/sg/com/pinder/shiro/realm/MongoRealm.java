package sg.com.pinder.shiro.realm;


import com.google.gson.Gson;
import com.mongodb.MongoClient;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.pojo.Role;
import sg.com.pinder.pojo.UserData;
import sg.com.pinder.web.WebServiceInternal;

public class MongoRealm extends AuthorizingRealm {

	Logger logger = Logger.getLogger(MongoRealm.class);
	
    private static final MongoDatabase<UserData> userDB = WebServiceInternal.getUserDB();
    
    public MongoRealm() {
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        Set<String> roles = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();
        for (Object tmp : pc.fromRealm(getClass().getName())) {
        	UserData user = userDB.searchOneDB(tmp.toString(), "email");
        	logger.fatal("First");
            if (user != null) {
                roles.addAll(user.getRoles());
                for (String temp : roles) { // in a multi-tenant environment different tenants may use the same role names, therefore we have to reduce the list of roles to those for the specific tenant
//                    Query<Role> q = ds.createQuery(Role.class);
//                    q.field("name").equal(temp);
//                    q.field("tenantId.identifier").equal(user.getTenantId().getIdentifier());
//                    Role role = q.get();
//                    if (role != null) {
//                        permissions.addAll(role.getPermissions());
//                    }
                }
            }
        }
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setObjectPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
    	logger.fatal("Second");
    	//Logger.getLogger(MongoRealm.class.getName()).log(Level.SEVERE, null, ds.find(UserData.class).field("name").equal(at.getPrincipal().toString()));
    	logger.fatal(at.getPrincipal().toString());
    	logger.fatal(new Gson().toJson(userDB.searchOneDB(at.getPrincipal().toString(), "email")));
    	UserData user = userDB.searchOneDB(at.getPrincipal().toString(), "email");
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getEmail(), user.getPassword()+"$"+user.getId().toHexString()+"$"+user.getAlgoMode(), getClass().getName());
        }
        throw new AuthenticationException();
    }
} 