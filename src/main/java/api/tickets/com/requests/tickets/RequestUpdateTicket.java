package api.tickets.com.requests.tickets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class RequestUpdateTicket implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotNull
	private Integer id;
	@NotNull(message = "El nombre de usuario no puede ser nulo")
	@NotEmpty(message = "El nombre de usuario no puede estar vacio")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre de usuario solo debe contener numeros y letras")
	private String usuario;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "La fecha de creacion debe enviarse con el siguiente formato: 2023-07-19 12:34:56")
	private String fechaCreacion;
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "La fecha de actualizacion debe enviarse con el siguiente formato: 2023-07-19 12:34:56")
	private String fechaActualizacion;
	@Pattern(regexp = "^(abierto|cerrado)$", message = "El estatus solo puede ser abierto o cerrado")
	private String estatus;
}
