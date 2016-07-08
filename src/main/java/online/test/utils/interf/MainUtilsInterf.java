package online.test.utils.interf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MainUtilsInterf {
	
	public boolean isAjax( HttpServletRequest request );
	
	public String MD5( String string );
	
	public void showThis( String string );
	
	public String getIp( HttpServletRequest request );
	
	public HttpServletResponse redirect( HttpServletResponse response, String url );
}