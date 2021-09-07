package com.sunilOS.ORSProject4.utility;

import java.util.HashMap;

/**
 * @author Anshul
 *
 */
public class EmailBuilder {

	/**
	 * @param map
	 * @return
	 */
	public static String getUserRegistrationMessage(HashMap<String, String> Map) {

		StringBuilder msg = new StringBuilder();

		msg.append("<HTML><BODY>");
		msg.append("Registration is successful for ORS Project");
		msg.append("<h1>Hi! Greeting from SunilOS!</h1>");
		msg.append("<p>Congratulations for registering on ORS!You can now "
				+ "access your ORS account online-anywhere,anytime."
				+ "the flexibility to check the Marksheet Detail.</p>");
		msg.append("<p>As a security measure, we recomended that you change "
				+ "your password after your first log in  </p>");
		msg.append("<p>For any assistance,please feel free to call us at 9876543234 </p> ");
		msg.append("<p>you may also write to us at raystech2021@gmail.com</p>");
		msg.append("<p>We assure you the best service at "
				+ "all times and look forward to a warm and long-standing association with you</p>");
		msg.append("<p> <a href='http://www.raystec.com'>Rays technologies</a></p>");
		msg.append("</body></html>");
		return msg.toString();
	}

	public static String getForgotPasswordMessage(HashMap<String, String> map) {

		StringBuilder msg = new StringBuilder();

		msg.append("<html><body>");
		msg.append("<h1>Hi " + map.get("firstName") + " " + map.get("lastName") + "!!  Please use below credentials to login to your account</h1>");
		msg.append("<p><B>Email Id : " + map.get("email") + "<BR>" + " Password : " + map.get("password") + "</b></p>");
		msg.append("</body></html>");
		return msg.toString();
	}

	public static String getChangePasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html><body>");
		msg.append("<h1>Your Password has been changed successfully !! " + map.get("firstName") + " "
				+ map.get("lastName") + "</h1>");
		msg.append("<p><b>To access account use Email Id : " + map.get("email") + "<br>" + " Password : "
				+ map.get("password") + "</b></p>");
		msg.append("</body></html>");
		return msg.toString();
	}

}

