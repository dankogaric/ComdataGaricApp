package com.comdata.factory.app.web.rest.dto;

import com.comdata.factory.app.domain.Truck;
import com.comdata.factory.app.domain.TructorTruck;
import com.comdata.factory.app.domain.Manufacturer;
import com.comdata.factory.app.domain.TankTruck;

public class TruckDTO {
	/**
	 * ENUM for Front-end mapping from DTO to appropriate entity!!!
	 */
	private static final String TRUCK_TYPE_TRUCTOR = "TructorTruck";
	private static final String TRUCK_TYPE_TANK = "TankTruck";
	
    private Long id;

    private Integer numberOfAxles;
    private Integer horsePower;
    private String color;
    private Integer tankCapacity;
    private Manufacturer manufacturer;
    
    
    private String truckType;

    /**
      "seatsNumber": 4,
      "roofTopCapacity": 100,
      "truckType": "ClassicTruck"
      
      roofTopCapacity
      truckType
     */

	public TruckDTO() {
		super();	
	}

	public TruckDTO(Truck truckEntity) {
		this.id = truckEntity.getId();
		this.numberOfAxles = truckEntity.getNumberOfAxles();
		this.color = truckEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(truckEntity.getManufacturer().getId());
		this.manufacturer.setName(truckEntity.getManufacturer().getName());
			
		if(truckEntity instanceof TructorTruck) {
			truckType = TRUCK_TYPE_TRUCTOR;
			this.horsePower = ((TructorTruck)truckEntity).getHorsePower();
			
		} else if(truckEntity instanceof TankTruck) {
			truckType = TRUCK_TYPE_TANK;
			this.tankCapacity = ((TankTruck)truckEntity).getTankCapacity();
		}
		
	}


	public Truck convertToTruckEntity () {
		// determine which type of truck is this
		if(truckType.equals(TRUCK_TYPE_TRUCTOR)) {
			TructorTruck returnValue = new TructorTruck();
			returnValue.setId(id);
			returnValue.setNumberOfAxles(numberOfAxles);
			returnValue.setHorsePower(horsePower);
			returnValue.setManufacturer(manufacturer);
			returnValue.setColor(color);
			return returnValue;
			
		} else {
			TankTruck returnValue = new TankTruck();			
			returnValue.setId(id);
			returnValue.setNumberOfAxles(numberOfAxles);
			returnValue.setTankCapacity(tankCapacity);
			returnValue.setManufacturer(manufacturer);
			returnValue.setColor(color);
			returnValue.setColor(color);
			return returnValue;
		}	
	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}


	public String getTruckType() {
		return truckType;
	}





	public void setTruckType(String truckType) {
		this.truckType = truckType;
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
	
	
	
	
	

	
	
	
    
    

}
