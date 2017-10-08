package com.comdata.factory.app.web.rest.dto;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.comdata.factory.app.domain.AdditionalEquipment;
import com.comdata.factory.app.domain.Cabrio;
import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.ClassicCar;
import com.comdata.factory.app.domain.Manufacturer;

public class CarDTO {
	/**
	 * ENUM for Front-end mapping from DTO to appropriate entity!!!
	 */
	private static final String CAR_TYPE_CLASSIC = "ClassicCar";
	private static final String CAR_TYPE_CABRIO = "Cabrio";
	
    private Long id;

    private Integer seatsNumber;

    /**
      "seatsNumber": 4,
      "roofTopCapacity": 100,
      "carType": "ClassicCar"
      
      roofTopCapacity
      carType
     */

    private Boolean hasAbs;
    private Boolean hasEsp;
    private Boolean hasGlassRoof;
    private Boolean hasAluWheels;
    
    private Boolean hasRemovableRoof;
    
    private Integer roofTopCapacity;
    
    private String carType;
    
    private Manufacturer manufacturer;
    
    private String color;
    


	public CarDTO() {
		super();
		
	}

	public CarDTO(Car carEntity) {
		this.id = carEntity.getId();
		this.seatsNumber = carEntity.getSeatsNumber();
		this.hasAbs = carEntity.getAddEq().isHasAbs();
		this.hasEsp = carEntity.getAddEq().isHasEsp();
		this.hasGlassRoof = carEntity.getAddEq().isHasGlassRoof();
		this.hasAluWheels = carEntity.getAddEq().isHasAluWheels();
		this.color = carEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(carEntity.getManufacturer().getId());
		this.manufacturer.setName(carEntity.getManufacturer().getName());
			
		if(carEntity instanceof ClassicCar) {
			carType = CAR_TYPE_CLASSIC;
			this.roofTopCapacity = ((ClassicCar)carEntity).getRoofTopCapacity();
			
		} else {
			carType = CAR_TYPE_CABRIO;
			this.hasRemovableRoof = ((Cabrio)carEntity).isHasRemovableRoof();
		}
		
	}


	public Car convertToCarEntity () {
		// determine which type of car is this
		if(carType.equals(CAR_TYPE_CLASSIC)) {
			ClassicCar returnValue = new ClassicCar();
			returnValue.setId(id);
			returnValue.setSeatsNumber(seatsNumber);
			AdditionalEquipment addEq = new AdditionalEquipment();
			addEq.setHasAbs(hasAbs);
			addEq.setHasAluWheels(hasAluWheels);
			addEq.setHasEsp(hasEsp);
			addEq.setHasGlassRoof(hasGlassRoof);
			
			returnValue.setManufacturer(manufacturer);
			returnValue.setColor(color);
			returnValue.setAddEq(addEq);
			returnValue.setRoofTopCapacity(roofTopCapacity);
			return returnValue;
			
		} else {
			Cabrio returnValue = new Cabrio();
			returnValue.setId(id);
			returnValue.setSeatsNumber(seatsNumber);
			AdditionalEquipment addEq = new AdditionalEquipment();
			addEq.setHasAbs(hasAbs);
			addEq.setHasAluWheels(hasAluWheels);
			addEq.setHasEsp(hasEsp);
			addEq.setHasGlassRoof(hasGlassRoof);
	
			returnValue.setManufacturer(manufacturer);
			returnValue.setColor(color);
			returnValue.setAddEq(addEq);
			returnValue.setHasRemovableRoof(hasRemovableRoof);
			return returnValue;
		}	
	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public Integer getSeatsNumber() {
		return seatsNumber;
	}





	public void setSeatsNumber(Integer seatsNumber) {
		this.seatsNumber = seatsNumber;
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





	public Manufacturer getManufacturer() {
		return manufacturer;
	}





	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	

	
	
	
    
    

}
