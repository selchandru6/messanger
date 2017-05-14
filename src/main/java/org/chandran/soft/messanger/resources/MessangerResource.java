/**
 * 
 */
package org.chandran.soft.messanger.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.chandran.soft.messanger.service.MessangerServiceImpl;

/**
 * @author Sanjith
 *
 */
@Path("/messages")
public class MessangerResource {
	
	MessangerServiceImpl messangerServiceImpl =  new MessangerServiceImpl();
	
	@GET
	@Path("/version")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVersion(){
		return "Current Version of this Software : 1.0";
	}
	
	@GET
	@Path("/inr")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrentINRValue(){
		String inrValue = messangerServiceImpl.getCurrentINRvalue();
		return inrValue;
	}
	
	@GET
	@Path("/weather")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrentWeather(){
		return "Current Weather for the given City is ";
	}
	
	@GET
	@Path("/holding")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCurrentCTSHvalue(){
		String currentHolding = messangerServiceImpl.getCurrentHolding();
		return currentHolding;
	}
	
	@GET
	@Path("/today")
	@Produces(MediaType.APPLICATION_JSON)
	public String getToday(){
		return "Current Weather for the given City is ";
	}
	
	
	
	
}
