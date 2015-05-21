package sg.com.pinder.shiro.realm;

import java.util.Collection;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;

public class FacebookAuthorizationInfo implements AuthorizationInfo {

	@Override
	public Collection<String> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getStringPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Permission> getObjectPermissions() {
		// TODO Auto-generated method stub
		return null;
	}

}
