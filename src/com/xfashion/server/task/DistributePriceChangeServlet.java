package com.xfashion.server.task;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.server.ArticleTypeServiceImpl;
import com.xfashion.server.user.UserServiceImpl;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class DistributePriceChangeServlet extends HttpServlet  {

	// http://localhost:8888/task/distributepricechange?priceChangeKey=agl4LWZhc2hpb25yJQsSDFByaWNlQ2hhbmdlcximBQwLEgtQcmljZUNoYW5nZRi2BQw
	
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
	public void service(HttpServletRequest request, HttpServletResponse response) {
		List<UserDTO> users = userService.readUsers();
		String priceChangeKey = request.getParameter(PARAM_PRICE_CHANGE_KEY);
		
		PriceChangeDTO priceChange = articleTypeService.readPriceChange(priceChangeKey);
		
		for (UserDTO user : users) {
			if (hasPriceChangedForUser(priceChange, user)) {
				PriceChangeDTO shopPriceChange = priceChange.clone();
				userService.createPriceChangeForShop(user.getShop().getKeyString(), shopPriceChange);
			}
		}
		
		articleTypeService.deletePriceChange(priceChange);
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
