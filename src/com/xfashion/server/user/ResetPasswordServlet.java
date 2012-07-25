package com.xfashion.server.user;

import java.io.IOException;
import java.util.Date;

import javax.jdo.JDOObjectNotFoundException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.user.UserService;
import com.xfashion.shared.ResetPasswordDTO;
import com.xfashion.shared.UserDTO;

public class ResetPasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String HEADER = "Passwort setzen";
	private static final String SUCCESS_1 = "Passwort wurde gespeichert";
	private static final String SUCCESS_2 = "<a href=\"/\">Click hier um zur XFashion Datenbank zu gelangen.</a>";
	private static final String LABEL_USER = "User";
	private static final String LABEL_PASSWORD = "Passwort";
	private static final String LABEL_REPEAT_PASSWORD = "Password Wiederholen";
	
	private static final String PARAM_ID = "id";
	private static final String PARAM_USERNAME = "username";
	private static final String PARAM_PASSWORD_1 = "pwd1";
	private static final String PARAM_PASSWORD_2 = "pwd2";

	
	private static final String ERROR_GENERAL = "Password konnte nicht gesetzt werden.";
	private static final String ERROR_NO_USERNAME = "User muss angegeben werden.";
	private static final String ERROR_PASSWORD_MISMATCH = "Das Passwort muss zweimal identisch eingegeben werden.";
	private static final String ERROR_NO_PASSWORD = "Ein neues Passwort muss angegeben werden.";
	private static final String ERROR_NOT_FOUND = "Der Benutzer oder der Reset-Password Link ist nicht gültig.";
	
	@Override 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletOutputStream out = res.getOutputStream();
		String id = req.getPathInfo().substring(1);
		UserService userService = new UserServiceImpl();
		ResetPasswordDTO resetDTO = userService.readResetPassword(id);
		renderResetForm(out, resetDTO, null);
	}
	
	private void renderResetForm(ServletOutputStream out, ResetPasswordDTO resetDTO, String error) throws IOException {
		out.println("<html>");
		out.println("<body>");

		out.println("<h2>");
		out.println(HEADER);
		out.println("</h2>");
		
		out.println("<form action=\"/resetpassword\" method=\"POST\">");
		out.println("<input type=\"hidden\" name=\"" + PARAM_ID + "\" value=\"" + resetDTO.getKey() + "\" />");
		out.println("<table>");
		out.println(generateFormField(LABEL_USER, PARAM_USERNAME, resetDTO.getUsername(), false));
		out.println(generateFormField(LABEL_PASSWORD, PARAM_PASSWORD_1, null, true));
		out.println(generateFormField(LABEL_REPEAT_PASSWORD, PARAM_PASSWORD_2, null, true));
		out.println("</table>");
		if (error != null) {
			out.println("<p style=\"text-color: red;\">" + error + "</p>");
		}
		out.println("<input type=\"submit\" />");
		out.println("</form>");
		
		out.println("</body>");
		out.println("</html>");
	}
	
	private String generateFormField(String label, String name, String value, boolean password) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr><td>");
		sb.append(label);
		sb.append(":");
		sb.append("</td><td>");
		sb.append("<input type=\"");
		if (password) {
			sb.append("password");
		} else {
			sb.append("text");
		}
		sb.append("\" name=\"").append(name).append("\"");
		if (value != null) {
			sb.append(" value=\"").append(value).append("\"");
		}
		sb.append(" />");
		sb.append("</td></tr>");
		return sb.toString();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletOutputStream out = res.getOutputStream();
		
		String id = req.getParameter(PARAM_ID);

		UserService userService = new UserServiceImpl();
		ResetPasswordDTO resetDTO = userService.readResetPassword(id);
		
		String username = req.getParameter(PARAM_USERNAME);
		String password1 = req.getParameter(PARAM_PASSWORD_1);
		String password2 = req.getParameter(PARAM_PASSWORD_2);
		
		if (id == null || id.length() == 0) {
			out.println(ERROR_GENERAL);
			return;
		}
		if (username == null || username.length() == 0) {
			renderResetForm(out, resetDTO, ERROR_NO_USERNAME);
			return;
		}
		if (password1 == null || password1.length() == 0) {
			renderResetForm(out, resetDTO, ERROR_NO_PASSWORD);
			return;
		}
		if (!password1.equals(password2)) {
			renderResetForm(out, resetDTO, ERROR_PASSWORD_MISMATCH);
			return;
		}
		
		UserDTO user;
		try {
			long oneDay = 24 * 60 * 60 * 1000;
			long now = new Date().getTime();
			if (resetDTO.getCreationTimestamp().getTime() + oneDay < now) {
				renderResetForm(out, resetDTO, ERROR_NOT_FOUND);
				return;
			}
			user = userService.readUserByUsername(username);
			if (!user.getEnabled()) {
				renderResetForm(out, resetDTO, ERROR_NOT_FOUND);
				return;
			}
			if (!user.getUsername().equals(resetDTO.getUsername())) {
				renderResetForm(out, resetDTO, ERROR_NOT_FOUND);
				return;
			}
		} catch (JDOObjectNotFoundException e) {
			renderResetForm(out, resetDTO, ERROR_NOT_FOUND);
			return;
		}
		userService.updatePassword(user, password1);
		userService.deleteResetPassword(resetDTO);
		
		renderSuccessForm(req, res);
	}
	
	private void renderSuccessForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletOutputStream out = res.getOutputStream();
		out.println("<html>");
		out.println("<body>");
	
		out.println("<p>" + SUCCESS_1 + "<br />");
		out.println(SUCCESS_2 + "</p>");

		out.println("</body>");
		out.println("</html>");
	}
}
