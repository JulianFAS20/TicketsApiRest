package api.tickets.com.service.impl.tickets;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.tickets.com.dao.tickets.TicketsDao;
import api.tickets.com.domain.tickets.Ticket;
import api.tickets.com.requests.tickets.RequestTicket;
import api.tickets.com.requests.tickets.RequestUpdateTicket;
import api.tickets.com.response.tickets.ResponseTicket;
import api.tickets.com.service.interfaces.tickets.TicketsService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketsImplement implements TicketsService {

	@Autowired
	TicketsDao ticketsDao;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	@Transactional(readOnly = true)
	public List<ResponseTicket>  getTickets() {
		try {
			List<Ticket> tickets = new ArrayList<>();
			List<ResponseTicket> response = new ArrayList<>();
			tickets.addAll(ticketsDao.findAll());
			if (!tickets.isEmpty()) {
				for (Ticket ticket : tickets) {
					ResponseTicket rTicketAux = convertTicketToResponseTicket(ticket);
					response.add(rTicketAux);
				}
			}
			return response;
		} catch (Exception e) {
			// Si la consulta de los tiquetes se totea devolvemos null
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseTicket getTicketById(int id) {
		try {
			Ticket ticket = ticketsDao.findById(id).orElse(new Ticket());
			return convertTicketToResponseTicket(ticket);
		} catch (Exception e) {
			// Si la consulta de los tiquetes se totea devolvemos null
			return null;
		}
	}

	@Override
	@Transactional
	public ResponseTicket createTicket(RequestTicket createTicket) {
		try {			
			if(createTicket.getFechaCreacion() == null || createTicket.getFechaCreacion().isEmpty()) {
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				createTicket.setFechaCreacion(sdf.format(timestamp));
			}
			if(createTicket.getFechaActualizacion() == null || createTicket.getFechaActualizacion().isEmpty()){
				createTicket.setFechaActualizacion(createTicket.getFechaCreacion());
			}
			
			ResponseTicket responseTicket = new ResponseTicket();
			Ticket ticketAux = convertRequestTicketToTicket(createTicket);
			Ticket ticketExist = ticketsDao.getByUsuario(ticketAux.getUsuario());
			
			if (ticketExist != null) {
				log.error("El nombre de usuario ya existe");
				responseTicket.setMessageError("El nombre de usuario ya existe");
				return responseTicket;
			}
			
			ticketsDao.save(ticketAux);
			ticketAux = ticketsDao.getByUsuario(ticketAux.getUsuario());
			responseTicket = convertTicketToResponseTicket(ticketAux);
			return responseTicket;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	@Transactional
	public ResponseTicket updateTicket(RequestUpdateTicket updateTicket) {
		try {
			ResponseTicket responseTicket = new ResponseTicket();
			Ticket ticketAux = new Ticket();
			Ticket ticketExist = new Ticket();
			ticketAux = ticketsDao.findById(updateTicket.getId()).orElse(null);
			if(ticketAux == null) {
				log.error("El usuario no fue encontrado en la BD por su Id, por favor verifique su existencia");
				responseTicket.setMessageError("El usuario no fue encontrado en la BD por su Id, por favor verifique su existencia");
				return responseTicket;
			} else {
				// Se actualizo el ticket con fecha de hoy
				ticketAux = convertRequestUpdateTicketToTicket(updateTicket);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				ticketAux.setFecha_actualizacion(timestamp);
			}
			
			ticketExist = ticketsDao.getByUsuario(ticketAux.getUsuario());
			if (ticketExist != null) {
				log.error("El nombre de usuario ya existe");
				responseTicket.setMessageError("El nombre de usuario ya existe");
				return responseTicket;
			} 
			
			ticketsDao.save(ticketAux);
			ticketAux = ticketsDao.getByUsuario(ticketAux.getUsuario());
			responseTicket = convertTicketToResponseTicket(ticketAux);
			return responseTicket;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	@Transactional
	public boolean deleteTicketById(int id) {
		try {
			ticketsDao.deleteById(id);
			return true;
		} catch (Exception e) {
			// No pudo eliminarse el ticket
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean deleteTicketByUser(String user) {
		try {
			ticketsDao.deleteByUsuario(user);
			return true;
		} catch (Exception e) {
			// No pudo eliminarse el ticket
			return false;
		}
	}
	
	@Override
	@Transactional
	public boolean deleteTicket(RequestUpdateTicket deleteTicket) {
		try {
			ticketsDao.delete(convertRequestUpdateTicketToTicket(deleteTicket));
			return true;
		} catch (Exception e) {
			// No pudo eliminarse el ticket
			return false;
		}
	}
	
	public Ticket convertRequestTicketToTicket(RequestTicket requestTicket) {
		try {
			Ticket convert = new Ticket();
			convert.setUsuario(requestTicket.getUsuario());
			convert.setFecha_creacion(Timestamp.valueOf(requestTicket.getFechaCreacion()));
			convert.setFecha_actualizacion(Timestamp.valueOf(requestTicket.getFechaActualizacion()));
			convert.setEstatus(requestTicket.getEstatus());
			return convert;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Ticket convertRequestUpdateTicketToTicket(RequestUpdateTicket requestTicket) {
		try {
			Ticket convert = new Ticket();
			convert.setId(requestTicket.getId());
			convert.setUsuario(requestTicket.getUsuario());
			convert.setFecha_creacion(Timestamp.valueOf(requestTicket.getFechaCreacion()));
			convert.setFecha_actualizacion(Timestamp.valueOf(requestTicket.getFechaActualizacion()));
			convert.setEstatus(requestTicket.getEstatus());
			return convert;
		} catch (Exception e) {
			return null;
		}
	}
	
	public ResponseTicket convertTicketToResponseTicket(Ticket ticket) {
		try {
			ResponseTicket convert = new ResponseTicket();
			convert.setId(ticket.getId());
			convert.setUsuario(ticket.getUsuario());
			convert.setFechaCreacion(sdf.format(ticket.getFecha_creacion()));
			convert.setFechaActualizacion(sdf.format(ticket.getFecha_actualizacion()));
			convert.setEstatus(ticket.getEstatus());
			return convert;
		} catch (Exception e) {
			return null;
		}
	}
}
