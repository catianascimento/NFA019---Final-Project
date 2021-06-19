package fr.com.nfa019.restaurant.modele;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "refrigerateur_temperature")
public class RefrigerateurTemperature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "refrigerateur_id")
	private Refrigerateur refrigerateur;
	
	private double minTemperature;
	private double maxTemperature;
	private LocalDateTime dateTime;

	public RefrigerateurTemperature() {
	}

	public RefrigerateurTemperature(double minTemperature, double maxTemperature) {
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;

		// create a clock
        ZoneId zid = ZoneId.of("Europe/Paris");
		this.dateTime = LocalDateTime.now(zid);
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
		RefrigerateurTemperature other = (RefrigerateurTemperature) obj;
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Refrigerateur getRefrigerateur() {
		return refrigerateur;
	}

	public void setRefrigerateur(Refrigerateur refrigerateur) {
		this.refrigerateur = refrigerateur;
	}

}
