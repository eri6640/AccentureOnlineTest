package online.test.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * An entity User composed by three fields (id, email, name). The Entity
 * annotation indicates that this class is a JPA entity. The Table annotation
 * specifies the name for the table in the db.
 *
 * @author 
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7981827772789032283L;

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------

	// An autogenerated id (unique for each user in the db)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 11)
	private long id;

	@NotNull
	private String email;

	@NotNull
	private String password_hash;

	@NotNull
	private String ip;

	@NotNull
	private String token;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	private Boolean admin_status;

	@NotNull
	private long last_activity;

	// ------------------------
	// PUBLIC METHODS
	// ------------------------

	public User() {}

	public User( long id ) {
		this.id = id;
	}

	public User( String email, String password_hash ) {
		this.email = email;
		this.password_hash = password_hash;
	}

	// Getter and setter methods

	public long getId() {
		return id;
	}

	public void setId( long value ) {
		this.id = value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String value ) {
		this.email = value;
	}

	public String getPasswordHash() {
		return password_hash;
	}

	public void setPasswordHash( String password_hash ) {
		this.password_hash = password_hash;
	}
	public String getIp() {
		return ip;
	}

	public void setIp( String ip ) {
		this.ip = ip;
	}

	public String getToken() {
		return token;
	}

	public void setToken( String token ) {
		this.token = token;
	}
	
	public void setAuth( String ip, String token_hash ){
		setIp( ip );
		setToken( token_hash );
		updateActivity();
	}
	
	/**
	 * 
	 * removes user ip and token
	 * 
	 */
	public void unSetAuth() {
		setIp( null );
		setToken( null );
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname( String surname ) {
		this.surname = surname;
	}

	public Boolean isAdmin() {
		return admin_status;
	}

	public void setAdminStatus( Boolean admin_status ) {
		this.admin_status = admin_status;
	}
	
	public long getActivity(){
		return last_activity;
	}
	
	public void updateActivity(){
		this.last_activity = System.currentTimeMillis();
	}

} // class User