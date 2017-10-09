package com.comdata.factory.app.web.rest.dto;

import com.comdata.factory.app.domain.Manufacturer;
import com.comdata.factory.app.domain.Parking;

public class CreateVehicleDTO {
	
	/**** VEHICLE properties *****/
    private Long id;
    
    private String vehicleType;
    private String color;
    private Manufacturer manufacturer;
    private Parking parking;
    
	private Integer area;
	
	/****************************
	 * CAR properties 
	 */

    private Integer seatsNumber;
    private Boolean hasAbs;
    private Boolean hasEsp;
    private Boolean hasGlassRoof;
    private Boolean hasAluWheels;
    
    private Boolean hasRemovableRoof;
    private Integer roofTopCapacity;
    
    private String carType;
    
    /******** BUS properties ******/
    
    private Integer seatsSitting;
    private Integer seatsStanding;
    private Boolean hasWhrist;
    private Integer trunkCapacity;

    private String busType;
    
    /******** TRUCK properties ********/

    private Integer numberOfAxles;
    private Integer horsePower;
    private Integer tankCapacity;
    private String truckType;
    
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String returnValue() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Manufacturer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Parking getParking() {
		return parking;
	}
	public void setParking(Parking parking) {
		this.parking = parking;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getSeatsNumber() {
		return seatsNumber;
	}
	public void setSeatsNumber(Integer seatsNumber) {
		this.seatsNumber = seatsNumber;
	}
	public Boolean getHasAbs() {
		return hasAbs;
	}
	public void setHasAbs(Boolean hasAbs) {
		this.hasAbs = hasAbs;
	}
	public Boolean getHasEsp() {
		return hasEsp;
	}
	public void setHasEsp(Boolean hasEsp) {
		this.hasEsp = hasEsp;
	}
	public Boolean getHasGlassRoof() {
		return hasGlassRoof;
	}
	public void setHasGlassRoof(Boolean hasGlassRoof) {
		this.hasGlassRoof = hasGlassRoof;
	}
	public Boolean getHasAluWheels() {
		return hasAluWheels;
	}
	public void setHasAluWheels(Boolean hasAluWheels) {
		this.hasAluWheels = hasAluWheels;
	}
	public Boolean getHasRemovableRoof() {
		return hasRemovableRoof;
	}
	public void setHasRemovableRoof(Boolean hasRemovableRoof) {
		this.hasRemovableRoof = hasRemovableRoof;
	}
	public Integer getRoofTopCapacity() {
		return roofTopCapacity;
	}
	public void setRoofTopCapacity(Integer roofTopCapacity) {
		this.roofTopCapacity = roofTopCapacity;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Integer getSeatsSitting() {
		return seatsSitting;
	}
	public void setSeatsSitting(Integer seatsSitting) {
		this.seatsSitting = seatsSitting;
	}
	public Integer getSeatsStanding() {
		return seatsStanding;
	}
	public void setSeatsStanding(Integer seatsStanding) {
		this.seatsStanding = seatsStanding;
	}
	public Boolean getHasWhrist() {
		return hasWhrist;
	}
	public void setHasWhrist(Boolean hasWhrist) {
		this.hasWhrist = hasWhrist;
	}
	public Integer getTrunkCapacity() {
		return trunkCapacity;
	}
	public void setTrunkCapacity(Integer trunkCapacity) {
		this.trunkCapacity = trunkCapacity;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public Integer getNumberOfAxles() {
		return numberOfAxles;
	}
	public void setNumberOfAxles(Integer numberOfAxles) {
		this.numberOfAxles = numberOfAxles;
	}
	public Integer getHorsePower() {
		return horsePower;
	}
	public void setHorsePower(Integer horsePower) {
		this.horsePower = horsePower;
	}
	public Integer getTankCapacity() {
		return tankCapacity;
	}
	public void setTankCapacity(Integer tankCapacity) {
		this.tankCapacity = tankCapacity;
	}
	public String getTruckType() {
		return truckType;
	}
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
	public String getVehicleType() {
		return vehicleType;
	}
    
    
	
    
    
    
	
}
