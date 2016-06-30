package online.test;

import javax.servlet.http.HttpServletRequest;

public class Functions {
	

	/**
	 * isAjax  --> checks request....
	 * 
	 * @param request http servlet request
	 * @return True if is ajax, False if isnt ajax
	 * */
	public static boolean isAjax( HttpServletRequest request ){
		return "XMLHttpRequest".equals( request.getHeader("X-Requested-With")) ? true : false;
	}

}
