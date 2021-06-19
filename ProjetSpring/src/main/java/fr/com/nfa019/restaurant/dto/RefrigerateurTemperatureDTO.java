package fr.com.nfa019.restaurant.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.RefrigerateurTemperature;

public class RefrigerateurTemperatureDTO {

	private Long id;
	private Long refrigerateurId;

	private double minTemperature;
	private double maxTemperature;
	private LocalDateTime dateTime;

	public RefrigerateurTemperatureDTO(RefrigerateurTemperature refrigerateurTemperature) {
		this.id = refrigerateurTemperature.getId();
		this.setRefrigerateurId(refrigerateurTemperature.getRefrigerateur().getId());
		this.minTemperature = refrigerateurTemperature.getMinTemperature();
		this.maxTemperature = refrigerateurTemperature.getMaxTemperature();
		this.dateTime = refrigerateurTemperature.getDateTime();
	}

	public RefrigerateurTemperatureDTO() {
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public static List<RefrigerateurTemperatureDTO> converter(List<RefrigerateurTemperature> refrigerateurTemperatures) {
		return refrigerateurTemperatures.stream().map(RefrigerateurTemperatureDTO::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Long getRefrigerateurId() {
		return refrigerateurId;
	}

	public void setRefrigerateurId(Long refrigerateurId) {
		this.refrigerateurId = refrigerateurId;
	}

}
