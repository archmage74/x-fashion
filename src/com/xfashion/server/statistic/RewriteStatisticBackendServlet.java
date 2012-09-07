package com.xfashion.server.statistic;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xfashion.server.task.DistributePriceChangeServlet;
import com.xfashion.server.task.UpdateSellStatisticServlet;

public class RewriteStatisticBackendServlet extends HttpServlet {

	private static final long serialVersionUID = -4310701256638886115L;
	private static final Logger log = Logger.getLogger(DistributePriceChangeServlet.class.getName());
	private static final String sourceClass = UpdateSellStatisticServlet.class.getName();

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
	
	public void doServe(HttpServletRequest request, HttpServletResponse response) {
		for (int i = 0; i<10; i++) {
			log.info("i=" + i);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
