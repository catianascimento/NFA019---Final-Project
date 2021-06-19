package fr.com.nfa019.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.com.nfa019.restaurant.modele.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
