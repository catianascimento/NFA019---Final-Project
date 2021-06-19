package fr.com.nfa019.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Refrigerateur;
import fr.com.nfa019.restaurant.modele.RefrigerateurTemperature;

public class RefrigerateurDTO {

	private Long id;
	private String code;
	private List<Long> refrigerateurTemperatureIds;

	public RefrigerateurDTO(Refrigerateur refrigerateur) {
		this.id = refrigerateur.getId();
		this.code = refrigerateur.getCode();
		this.setRefrigerateurTemperatureIds(refrigerateur.getTemperatures().stream().map(RefrigerateurTemperature::getId)
				.collect(Collectors.toList()));
	}

	public RefrigerateurDTO() {
	}

	public static List<RefrigerateurDTO> converter(List<Refrigerateur> refrigerateurs) {
		return refrigerateurs.stream().map(RefrigerateurDTO::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public List<Long> getRefrigerateurTemperatureIds() {
		return refrigerateurTemperatureIds;
	}

	public void setRefrigerateurTemperatureIds(List<Long> refrigerateurTemperatureIds) {
		this.refrigerateurTemperatureIds = refrigerateurTemperatureIds;
	}

}
