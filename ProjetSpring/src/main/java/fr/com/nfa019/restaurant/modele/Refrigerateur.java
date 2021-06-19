package fr.com.nfa019.restaurant.modele;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "refrigerateur")
public class Refrigerateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;

	@OneToMany(mappedBy = "refrigerateur", cascade = CascadeType.ALL)
	private List<RefrigerateurTemperature> temperatures;

	public Refrigerateur() {
	}

	public Refrigerateur(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Refrigerateur other = (Refrigerateur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public void setCode(String code) {
		this.code = code;
	}

	public List<RefrigerateurTemperature> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(List<RefrigerateurTemperature> temperatures) {
		this.temperatures = temperatures;
	}

}
