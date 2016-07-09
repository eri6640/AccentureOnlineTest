package online.test.post.classes;

public class TestObj {
	
	private long id = 0;
	private long user_id;
	private String title;
	private String description;
	private long created;
	
	public TestObj() {}
	
	public TestObj( long id, long user_id, String title, String description, long created ) {
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.created = created;
	}
	
	public long getId(){
		return id;
	}
	public long getUserId(){
		return user_id;
	}
	public String getTitle(){
		return title;
	}
	public String getDescription(){
		return description;
	}
	public long getCreated(){
		return created;
	}
	
	public void setId( long id ){
		this.id = id;
	}
	public void setUserId( long user_id ){
		this.user_id = user_id;
	}
	public void setTitle( String title ){
		this.title = title;
	}
	public void setDescription( String description ){
		this.description = description;
	}
	public void setCreated( long created ){
		this.created = created;
	}

}
