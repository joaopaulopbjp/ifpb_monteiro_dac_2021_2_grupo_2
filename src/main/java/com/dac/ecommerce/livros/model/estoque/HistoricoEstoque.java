package com.dac.ecommerce.livros.model.estoque;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_HISTORICO_ESTOQUE")
public class HistoricoEstoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "historico_Id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private EstadoItem estadoItem;
	
	private String nomeItem;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar calendario;

	@Override
	public String toString() {
		return "HistoricoEstoque [id=" + id 
				+ ", estadoItem=" + estadoItem 
				+ ", calendario=" + calendario + "]";
	}

}
