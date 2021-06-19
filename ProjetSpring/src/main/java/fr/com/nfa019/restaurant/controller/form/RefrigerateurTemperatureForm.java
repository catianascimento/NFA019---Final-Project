package fr.com.nfa019.restaurant.controller.form;

import java.time.LocalDateTime;
import java.util.Optional;

import fr.com.nfa019.restaurant.modele.Refrigerateur;
import fr.com.nfa019.restaurant.modele.RefrigerateurTemperature;
import fr.com.nfa019.restaurant.repository.RefrigerateurRepository;
import fr.com.nfa019.restaurant.repository.RefrigerateurTemperatureRepository;

public class RefrigerateurTemperatureForm {

	private Long refrigerateurId;
	private double minTemperature;
	private double maxTemperature;
	private LocalDateTime dateTime;

	public RefrigerateurTemperature converter(RefrigerateurRepository refrigerateurRepository) {
		RefrigerateurTemperature refrigerateurTemperature = new RefrigerateurTemperature(minTemperature,
				maxTemperature);

		Optional<Refrigerateur> refrigerateur = refrigerateurRepository.findById(getRefrigerateurId());
		if (refrigerateur.isPresent()) {
			refrigerateurTemperature.setRefrigerateur(refrigerateur.get());
		}
		return refrigerateurTemperature;
	}

	public RefrigerateurTemperature update(Long id,
			RefrigerateurTemperatureRepository refrigerateurTemperatureRepository,
			RefrigerateurRepository refrigerateurRepository) {
		RefrigerateurTemperature refrigerateurTemperature = refrigerateurTemperatureRepository.getOne(id);

		refrigerateurTemperature.setMinTemperature(this.getMinTemperature());
		refrigerateurTemperature.setMaxTemperature(this.getMaxTemperature());

		Optional<Refrigerateur> refrigerateur = refrigerateurRepository.findById(getRefrigerateurId());
		if (refrigerateur.isPresent()) {
			refrigerateurTemperature.setRefrigerateur(refrigerateur.get());
		}

		return refrigerateurTemperature;
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
