/**
 * 
 */
package org.chandran.soft.messanger.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.chandran.soft.messanger.util.InternetReader;
import org.chandran.soft.messanger.util.MyMailClient;

/**
 * @author Sanjith
 *
 */

public class MessangerServiceImpl implements MessangerService{
	
	private static Logger logger = Logger.getLogger(MessangerServiceImpl.class);

	/**
	 * @param null
	 * @return String
	 */
	@Override
	public String getCurrentINRvalue() {
		String currentINRvalue="0.0";
		double inr=0;		
		try {
			inr=InternetReader.getINRRate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}		
		currentINRvalue="Current date and time " +new Date() + " Rate - USD to INR : "+inr;
		logger.info("getCurrentINRvalue::INR"+currentINRvalue);
		MyMailClient.sentMessage("Good Morning", currentINRvalue);
		return currentINRvalue;
	}

	/* (non-Javadoc)
	 * @see org.chandran.soft.messanger.service.MessangerService#getCurrentWeather()
	 */
	@Override
	public String getCurrentWeather() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentHolding() {
		double currentHolding=0;
		try {
			currentHolding = InternetReader.getScriptPrice("CTSH");
		} catch (Exception e) {			
			e.printStackTrace();
			logger.error(e);
		}
		return "Current CTSH Holding value : "+currentHolding*354;
	}

}
