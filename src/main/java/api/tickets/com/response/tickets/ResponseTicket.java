package api.tickets.com.response.tickets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ResponseTicket implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String usuario;
	private String fechaCreacion;
	private String fechaActualizacion;
	private String estatus;
	@JsonIgnore
	private String messageError;
}
