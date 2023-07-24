package api.tickets.com.controller.tickets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.tickets.com.requests.tickets.RequestTicket;
import api.tickets.com.requests.tickets.RequestUpdateTicket;
import api.tickets.com.response.tickets.ResponseTicket;
import api.tickets.com.service.interfaces.tickets.TicketsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/TicketsApi") // Ruta base del controlador
public class TicketsController {
	@Autowired
	TicketsService ticketService;
	
	@GetMapping("/getTickets") // Ruta especifica del endpoint de primer nivel
	public ResponseEntity<?> getTickets(){
		List<ResponseTicket> tickets = new ArrayList<>();
		try {
			log.info("Consultando la lista de ticketes");
			tickets = ticketService.getTickets();
			if (tickets.isEmpty()) {
				return new ResponseEntity<List<ResponseTicket>>(tickets, HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<List<ResponseTicket>>(tickets, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getTicketById/{id}")
	public ResponseEntity<?> getTicketById(@PathVariable Integer id){
		ResponseTicket ticket = new ResponseTicket();
		try {
			log.info("Consultando tickete");
			ticket = ticketService.getTicketById(id);
			if (ticket == null) {
				return new ResponseEntity<ResponseTicket>(ticket, HttpStatus.NO_CONTENT);
			}
			else {
				return new ResponseEntity<ResponseTicket>(ticket, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createTicket")
	public ResponseEntity<?> createTicket(@Valid @RequestBody RequestTicket ticket, BindingResult result){
		if (result.hasErrors()) {
			return new ResponseEntity<String>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		try {
			ResponseTicket response = ticketService.createTicket(ticket);
			if (response == null) {
				return new ResponseEntity<String>("Ocurrio un error durante la creacion del ticket", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(response.getMessageError() != null && !response.getMessageError().isEmpty()){
				return new ResponseEntity<String>(response.getMessageError(), HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<ResponseTicket>(response, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@PutMapping("/updateTicket")
	public ResponseEntity<?> updateTicket(@Valid @RequestBody RequestUpdateTicket ticket){
		try {
			ResponseTicket response = ticketService.updateTicket(ticket);
			if (response == null) {
				return new ResponseEntity<String>("Ocurrio un error durante la actualizacion del ticket", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			else if(response.getMessageError() != null && !response.getMessageError().isEmpty()){
				return new ResponseEntity<String>(response.getMessageError(), HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<ResponseTicket>(response, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteTicketById/{id}")
	public ResponseEntity<?> deleteTicketById(@PathVariable Integer id){
		try {
			log.info("Eliminando tickete por id");
			boolean delete = ticketService.deleteTicketById(id);
			if (delete) {
				return new ResponseEntity<String>("Ticket Eliminado", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se pudo eliminar el ticket", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteTicketByUsername/{user}")
	public ResponseEntity<?> deleteTicketByUsername(@PathVariable String user){
		try {
			log.info("Eliminando tickete por usuario");
			boolean delete = ticketService.deleteTicketByUser(user);
			if (delete) {
				return new ResponseEntity<String>("Ticket Eliminado", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se pudo eliminar el ticket", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteTicket")
	public ResponseEntity<?> deleteTicket(@Valid @RequestBody RequestUpdateTicket request){
		try {
			log.info("Eliminando tickete");
			boolean delete = ticketService.deleteTicket(request);
			if (delete) {
				return new ResponseEntity<String>("Ticket Eliminado", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("No se pudo eliminar el ticket", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
