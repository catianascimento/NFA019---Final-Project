package fr.com.nfa019.restaurant.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.com.nfa019.restaurant.modele.Refrigerateur;
import fr.com.nfa019.restaurant.modele.RefrigerateurTemperature;
import fr.com.nfa019.restaurant.repository.RefrigerateurRepository;
import fr.com.nfa019.restaurant.repository.RefrigerateurTemperatureRepository;

public class RefrigerateurForm {

	@NotNull
	@NotEmpty
	private String code;
	private List<Long> refrigerateurTemperatureIds;

	public Refrigerateur converter(RefrigerateurTemperatureRepository refrigerateurTemperatureRepository) {
		Refrigerateur refrigerateur = new Refrigerateur(code);

		List<RefrigerateurTemperature> refrigerateurTemperatures = new ArrayList<RefrigerateurTemperature>();
		if (refrigerateurTemperatureIds != null) {
			for (Long id : refrigerateurTemperatureIds) {
				Optional<RefrigerateurTemperature> refrigerateurTemperature = refrigerateurTemperatureRepository
						.findById(id);
				if (refrigerateurTemperature.isPresent()) {
					refrigerateurTemperatures.add(refrigerateurTemperature.get());
				}
			}
		}
		refrigerateur.setTemperatures(refrigerateurTemperatures);
		return refrigerateur;
	}

	public Refrigerateur update(Long id, RefrigerateurRepository refrigerateurRepository,
			RefrigerateurTemperatureRepository refrigerateurTemperatureRepository) {
		Refrigerateur refrigerateur = refrigerateurRepository.getOne(id);

		refrigerateur.setCode(this.getCode());
		List<RefrigerateurTemperature> refrigerateurTemperatures = new ArrayList<RefrigerateurTemperature>();
		if (refrigerateurTemperatureIds != null) {
			for (Long rTid : refrigerateurTemperatureIds) {
				Optional<RefrigerateurTemperature> refrigerateurTemperature = refrigerateurTemperatureRepository
						.findById(rTid);
				if (refrigerateurTemperature.isPresent()) {
					refrigerateurTemperatures.add(refrigerateurTemperature.get());
				}
			}
		}
		refrigerateur.setTemperatures(refrigerateurTemperatures);
		return refrigerateur;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
