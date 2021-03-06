package online.test.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainUtils {
	
	public String TokenName = "XSRF-TOKEN";

	/**
	 * isAjax  --> checks request....
	 * 
	 * @param request http servlet request
	 * @return True if is ajax, False if isnt ajax
	 * */
	public boolean isAjax( HttpServletRequest request ){
		return "XMLHttpRequest".equals( request.getHeader("X-Requested-With")) ? true : false;
	}
	
	
	public String MD5( String string ){

		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(string.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return sb.toString();
		}
		catch (java.security.NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	
	public void showThis( String string ){
		System.out.println( "AccentureOnlineTest:showThis(): " + string );
	}
	
	public String getIp( HttpServletRequest request ){
		return request.getHeader( "X-FORWARDED-FOR" ) == null ? request.getRemoteAddr() : null;
	}
	
	public HttpServletResponse redirect( HttpServletResponse response, String url ){
		response.setHeader( "Location", url );
		return response;
	}

}