package fr.com.nfa019.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.com.nfa019.restaurant.modele.RefrigerateurTemperature;

public interface RefrigerateurTemperatureRepository extends JpaRepository<RefrigerateurTemperature, Long> {

	List<RefrigerateurTemperature> findByRefrigerateurId(Long refrigerateurId);

}
