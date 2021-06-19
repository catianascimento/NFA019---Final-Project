package fr.com.nfa019.restaurant.beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class RefrigerateurTemperatureBean {

	private Long id;
	private Long refrigerateurId;

	private double minTemperature;
	private double maxTemperature;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateTime;
	
	@Override
	public String toString() {
		return "RefrigerateurTemperatureBean [id=" + id + ", refrigerateurId=" + refrigerateurId + ", minTemperature="
				+ minTemperature + ", maxTemperature=" + maxTemperature + ", dateTime=" + dateTime + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRefrigerateurId() {
		return refrigerateurId;
	}
	public void setRefrigerateurId(Long refrigerateurId) {
		this.refrigerateurId = refrigerateurId;
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

}
