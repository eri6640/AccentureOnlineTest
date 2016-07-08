package online.test.controllers;

import online.test.models.User;
import online.test.models.dao.UserDao;
import online.test.utils.AdminUtils;
import online.test.utils.LoginUtils;
import online.test.utils.MainUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 */
@Controller
public class UserController {

	// ------------------------
	// PUBLIC METHODS
	// ------------------------
	  
	/**
	 * /create  --> Create a new user and save it in the database.
	 * 
	 * @param email User's email
	 * @param name User's name
	 * @return A string describing if the user is succesfully created or not.
	 * */
	@RequestMapping("/data/user/create")
	@ResponseBody
	public Boolean create(String email,  String name, String surname, Boolean admin_status) {
		//User user = null;
		try {
			String password_string=adminUtils.randomString(10);
			String password_hash=mainUtils.MD5(password_string);
			User user = new User(email,password_hash,name,surname,admin_status);
			userDao.save(user);
			adminUtils.send(email, password_string);
		}
		catch (Exception ex) {
			System.out.println("Error creating the user: " + ex.toString());
			return false;
		}
		return true;
	}
	
	@RequestMapping("/data/user/reset")
	@ResponseBody
	public Boolean resetMail(long id) {
		//User user = null;
		try {
			String password_string=adminUtils.randomString(10);
			String password_hash=mainUtils.MD5(password_string);
			User newUser = userDao.findById(id);
			newUser.setPasswordHash(password_hash);
			
			adminUtils.send((String)newUser.getEmail(), password_string);
			
			userDao.save(newUser);
		}
		catch (Exception ex) {
			System.out.println("Error resetting the mail: " + ex.toString());
			return false;
		}
		return true;
	}
	 
/***************/
  
	/**
	 * /delete  --> Delete the user having the passed id.
	 * 
	 * @param id The id of the user to delete
	 * @return A string describing if the user is succesfully deleted or not.
	*/
	@RequestMapping("/data/user/delete")
	@ResponseBody
	public Boolean delete(long id) {
		try {
			User user = new User(id);
			userDao.delete(user);
		}
		catch (Exception ex) {
			return false;
			//return "Error deleting the user: " + ex.toString();
		}
			return true;
	}
  
	/**
	 * /get-by-email  --> Return the id for the user having the passed email.
	 * 
	 * @param email The email to search in the database.
	 * @return The user id or a message error if the user is not found.
	*/
	@RequestMapping("/data/user/get-by-email")
	@ResponseBody
	public String getByEmail(String email) {
		String userId;
	    try {
	    	User user = userDao.findByEmail(email);
	    	userId = String.valueOf(user.getId());
	    }
	    catch ( Exception ex ) {
	    	return "";
	    }
	    	return userId;
	}
  
	/**
	 * /update  --> Update the email and the name for the user in the database 
	 * having the passed id.
	 * 
	 * @param id The id for the user to update.
	 * @param email The new email.
	 * @param name The new name.
	 * @return A string describing if the user is succesfully updated or not.
	*/
	@RequestMapping("/data/user/update")
	@ResponseBody
	public String updateUser(long id, String email, String name) {
		try {
			User user = userDao.findOne(id);
			user.setEmail(email);
			userDao.save(user);
		}
	    catch (Exception ex) {
	    	return "Error updating the user: " + ex.toString();
	    }
	    return "User succesfully updated!";
	}

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	AdminUtils adminUtils = new AdminUtils();
	
	//@Autowired
	MainUtils mainUtils = new MainUtils();
  
} // class UserController