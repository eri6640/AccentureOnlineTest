package online.test.post.classes;

public class IdObj {
	
	private String id;
	
	public int getId(){
		int tmp = 0;
		try{
			tmp = Integer.parseInt( id );
		}
		catch( Exception error ){
			tmp = 0;
		}
		return tmp;
	}
	
	public void setId( String id ){
		this.id = id;
	}

}
