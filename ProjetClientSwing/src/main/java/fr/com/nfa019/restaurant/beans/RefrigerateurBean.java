package fr.com.nfa019.restaurant.beans;

import java.util.List;

public class RefrigerateurBean {

	private Long id;
	private String code;
	private List<Long> refrigerateurTemperatureIds;

	@Override
	public String toString() {
		return "RefrigerateurBean [id=" + id + ", code=" + code + "]";
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

	public List<Long> getRefrigerateurTemperatureIds() {
		return refrigerateurTemperatureIds;
	}

	public void setRefrigerateurTemperatureIds(List<Long> refrigerateurTemperatureIds) {
		this.refrigerateurTemperatureIds = refrigerateurTemperatureIds;
	}

}
