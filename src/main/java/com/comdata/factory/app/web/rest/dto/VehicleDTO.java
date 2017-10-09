package com.comdata.factory.app.web.rest.dto;

import com.comdata.factory.app.domain.Manufacturer;
import com.comdata.factory.app.domain.Parking;
import com.comdata.factory.app.domain.Vehicle;

public class VehicleDTO {

	
    private Long id;

    private String vehicleType;
    private String color;
    private Manufacturer manufacturer;
    private Parking parking;

	private Integer area;

	public VehicleDTO() {
		super();	
	}

	public VehicleDTO(Vehicle vehicleEntity)
	{
		this.id = vehicleEntity.getId();
		this.vehicleType = vehicleEntity.getVehicleType().toString();
		this.color = vehicleEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(vehicleEntity.getManufacturer().getId());
		this.manufacturer.setName(vehicleEntity.getManufacturer().getName());
		this.parking = new Parking(vehicleEntity.getParking());
		this.area = vehicleEntity.getArea();
	}


	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	

}
