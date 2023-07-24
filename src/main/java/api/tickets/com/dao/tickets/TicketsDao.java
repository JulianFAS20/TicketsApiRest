package api.tickets.com.dao.tickets;

import org.springframework.data.jpa.repository.JpaRepository;

import api.tickets.com.domain.tickets.Ticket;

public interface TicketsDao extends JpaRepository<Ticket, Integer>{

	Ticket getByUsuario(String usuario);
	
	void deleteByUsuario(String usuario);
}
