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
	private static final String SUCCESS = "Passwort wurde neu gesetzt.";
	
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
		renderResetForm(out, id, null);
	}
	
	private void renderResetForm(ServletOutputStream out, String id, String error) throws IOException {
		out.println("<html>");
		out.println("<body>");

		out.println("<h2>");
		out.println(HEADER);
		out.println("</h2>");
		
		out.println("<form action=\"/resetpassword\" method=\"POST\">");
		out.println("<input type=\"hidden\" name=\"" + PARAM_ID + "\" value=\"" + id + "\" />");
		out.println("<table>");
		out.println(generateFormField("User", PARAM_USERNAME, false));
		out.println(generateFormField("Password 1", PARAM_PASSWORD_1, true));
		out.println(generateFormField("Password 2", PARAM_PASSWORD_2, true));
		out.println("</table>");
		if (error != null) {
			out.println("<p style=\"text-color: red;\">" + error + "</p>");
		}
		out.println("<input type=\"submit\" />");
		out.println("</form>");
		
		out.println("</body>");
		out.println("</html>");
	}
	
	private String generateFormField(String label, String name, boolean password) {
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
		sb.append(" />");
		sb.append("</td></tr>");
		return sb.toString();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletOutputStream out = res.getOutputStream();
		
		String id = req.getParameter(PARAM_ID);
		String username = req.getParameter(PARAM_USERNAME);
		String password1 = req.getParameter(PARAM_PASSWORD_1);
		String password2 = req.getParameter(PARAM_PASSWORD_2);
		
		if (id == null || id.length() == 0) {
			out.println(ERROR_GENERAL);
			return;
		}
		if (username == null || username.length() == 0) {
			renderResetForm(out, id, ERROR_NO_USERNAME);
			return;
		}
		if (password1 == null || password1.length() == 0) {
			renderResetForm(out, id, ERROR_NO_PASSWORD);
			return;
		}
		if (!password1.equals(password2)) {
			renderResetForm(out, id, ERROR_PASSWORD_MISMATCH);
			return;
		}
		
		UserService userService = new UserServiceImpl();
		UserDTO user;
		ResetPasswordDTO reset;
		try {
			Long resetId = Long.parseLong(id);
			reset = userService.readResetPassword(resetId);
			long oneDay = 24 * 60 * 60 * 1000;
			long now = new Date().getTime();
			if (reset.getCreationTimestamp().getTime() + oneDay < now) {
				renderResetForm(out, id, ERROR_NOT_FOUND);
				return;
			}
			user = userService.readUserByUsername(username);
			if (!user.getEnabled()) {
				renderResetForm(out, id, ERROR_NOT_FOUND);
				return;
			}
			if (!user.getUsername().equals(reset.getUsername())) {
				renderResetForm(out, id, ERROR_NOT_FOUND);
				return;
			}
		} catch (JDOObjectNotFoundException e) {
			renderResetForm(out, id, ERROR_NOT_FOUND);
			return;
		}
		userService.updatePassword(user, password1);
		userService.deleteResetPassword(reset);
		
		renderSuccessForm(req, res);
	}
	
	private void renderSuccessForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletOutputStream out = res.getOutputStream();
		out.println("<html>");
		out.println("<body>");
	
		out.println("<p>" + SUCCESS + "</p>");
		String path = req.getRequestURL().toString();
		String link = path.substring(0, path.lastIndexOf("/")); 
		out.println("<a href=\"" + link + "\">X-Fashion</a>");

		out.println("</body>");
		out.println("</html>");
	}
}
