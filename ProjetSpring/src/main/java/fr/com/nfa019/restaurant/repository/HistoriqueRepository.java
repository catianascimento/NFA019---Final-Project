package fr.com.nfa019.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.com.nfa019.restaurant.modele.Historique;

public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

}
