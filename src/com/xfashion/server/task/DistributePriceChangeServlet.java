package com.xfashion.server.task;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.at.ArticleTypeService;
import com.xfashion.server.ArticleTypeServiceImpl;
import com.xfashion.server.user.UserServiceImpl;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class DistributePriceChangeServlet extends HttpServlet  {

	// http://localhost:8888/task/distributepricechange?priceChangeKey=agl4LWZhc2hpb25yJQsSDFByaWNlQ2hhbmdlcximBQwLEgtQcmljZUNoYW5nZRi2BQw
	
	private static final Logger log = Logger.getLogger(DistributePriceChangeServlet.class.getName());
	private static final String sourceClass = DistributePriceChangeServlet.class.getName();
	
	public static final String PARAM_PRICE_CHANGE_KEY = "priceChangeKey";
	
	private static final long serialVersionUID = 4624741913742381193L;

	protected UserServiceImpl userService;
	protected ArticleTypeService articleTypeService;
	
	@Override
	public void init() {
		userService = new UserServiceImpl();
		articleTypeService = new ArticleTypeServiceImpl();
	}
	
	@Override
	public void destroy() {
		userService = null;
		articleTypeService = null;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.entering(sourceClass, "doGet");
		doServe(request, response);
		log.exiting(sourceClass, "doGet");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		log.entering(sourceClass, "doPost");
		doServe(request, response);
		log.exiting(sourceClass, "doPost");
	}
	
	protected void doServe(HttpServletRequest request, HttpServletResponse response) {
		log.entering(sourceClass, "doServe");

		List<UserDTO> users = userService.readUsers();
		String priceChangeKey = request.getParameter(PARAM_PRICE_CHANGE_KEY);
		
		PriceChangeDTO priceChange = articleTypeService.readPriceChange(priceChangeKey);
		
		log.info("distributing price-change on articleTypeId=" + priceChange.getArticleTypeKey() + " to " + users.size() + " users");
		for (UserDTO user : users) {
			if (hasPriceChangedForUser(priceChange, user)) {
				log.info("user " + user.getUsername() + " has this article");
				PriceChangeDTO shopPriceChange = priceChange.clone();
				userService.createPriceChangeForShop(user.getShop().getKeyString(), shopPriceChange);
			} else {
				log.info("user skipped - does not have this article");
			}
		}
		
		articleTypeService.deletePriceChange(priceChange);

		log.exiting(sourceClass, "doServe");
	}

	private boolean hasPriceChangedForUser(PriceChangeDTO priceChange, UserDTO user) {
		boolean changedForUser = false;
		if (Boolean.TRUE.equals(user.getEnabled()) && user.getRole().equals(UserRole.SHOP)) {
			switch (user.getCountry()) {
			case AT:
				changedForUser = priceChange.getSellPriceAt() != null;
				break;
			case DE:
				changedForUser = priceChange.getSellPriceDe() != null;
				break;
			}
		}
		return changedForUser;
	}

}
