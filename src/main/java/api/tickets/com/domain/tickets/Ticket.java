package api.tickets.com.domain.tickets;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "ticket_seq_generator", sequenceName = "ticket_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_seq_generator")
	private Integer id;
	@NotNull
	@NotEmpty
	private String usuario;
	@NotNull
	private Timestamp fecha_creacion;
	@NotNull
	private Timestamp fecha_actualizacion;
	@NotNull
	private String estatus;
}
