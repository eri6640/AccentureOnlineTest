package online.test.post.classes;

public class AuthObj {
	
	private String email;
	private String password;
	
	AuthObj() {}

	public AuthObj(String email, String password) {
		this.email = email;
		this.password = password;
	}


	public void setEmail( String email ){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}
	
	public void setPassword( String password ){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}

}
