package api.tickets.com.service.interfaces.tickets;

import java.util.List;

import api.tickets.com.requests.tickets.RequestTicket;
import api.tickets.com.requests.tickets.RequestUpdateTicket;
import api.tickets.com.response.tickets.ResponseTicket;

public interface TicketsService {
	List<ResponseTicket> getTickets();
	
	ResponseTicket getTicketById(int id);
	
	ResponseTicket createTicket(RequestTicket createTicket);
	
	ResponseTicket updateTicket(RequestUpdateTicket updateTicket);
	
	boolean deleteTicketById(int id);
	
	boolean deleteTicketByUser(String user);
	
	boolean deleteTicket(RequestUpdateTicket deleteTicket);
}
