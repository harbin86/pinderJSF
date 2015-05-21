package sg.com.pinder.pojo;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Roles for specific users to be retrieved from the database 
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
@Entity(value = "role", noClassnameStored = true)
public class Role {

    @Id
    private ObjectId id;
    private String name;
    private Set<Permission> permissions = new HashSet<>();

    /**
     * @param name
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Default role
     */
    public Role() {
    }

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the permissions
	 */
	public Set<Permission> getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

} 