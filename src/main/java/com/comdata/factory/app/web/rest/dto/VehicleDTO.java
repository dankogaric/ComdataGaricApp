package com.comdata.factory.app.web.rest.dto;

import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.Car;
import com.comdata.factory.app.domain.Manufacturer;
import com.comdata.factory.app.domain.Truck;

public class VehicleDTO {
	/**
	 * ENUM for Front-end mapping from DTO to appropriate entity!!!
	 */
	private static final String VEHICLE_TYPE_CAR= "Car";
	private static final String VEHICLE_TYPE_BUS= "Bus";
	private static final String VEHICLE_TYPE_TRUCK= "Truck";
	
    private Long id;

    private String type;
    private String color;
    private Manufacturer manufacturer;
    
    
    private String busType;

    /**
      "seatsNumber": 4,
      "roofTopCapacity": 100,
      "busType": "ClassicBus"
      
      roofTopCapacity
      busType
     */

	public VehicleDTO() {
		super();	
	}

	public VehicleDTO(Bus busEntity) {
		this.id = busEntity.getId();
		this.type = VEHICLE_TYPE_BUS;
		this.color = busEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(busEntity.getManufacturer().getId());
		this.manufacturer.setName(busEntity.getManufacturer().getName());		
	}
	
	public VehicleDTO(Car carEntity) {
		this.id = carEntity.getId();
		this.type = VEHICLE_TYPE_CAR;
		this.color = carEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(carEntity.getManufacturer().getId());
		this.manufacturer.setName(carEntity.getManufacturer().getName());		
	}
	
	public VehicleDTO(Truck truckEntity) {
		this.id = truckEntity.getId();
		this.type = VEHICLE_TYPE_TRUCK;
		this.color = truckEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(truckEntity.getManufacturer().getId());
		this.manufacturer.setName(truckEntity.getManufacturer().getName());
	}


//	public Bus convertToBusEntity () {
//		// determine which type of bus is this
//		if(busType.equals(BUS_TYPE_CITY)) {
//			CityBus returnValue = new CityBus();
//			returnValue.setId(id);
//			returnValue.setHasWhrist(hasWhrist);
//			returnValue.setSeatsSitting(seatsSitting);
//			returnValue.setSeatsStanding(seatsStanding);
//			returnValue.setManufacturer(manufacturer);
//			returnValue.setColor(color);
//			return returnValue;
//			
//		} else {
//			InterCityBus returnValue = new InterCityBus();
//			returnValue.setId(id);
//			returnValue.setTrunkCapacity(trunkCapacity);
//			returnValue.setSeatsSitting(seatsSitting);
//			returnValue.setSeatsStanding(seatsStanding);
//			returnValue.setManufacturer(manufacturer);
//			returnValue.setColor(color);
//			return returnValue;
//		}	
//	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}


	public String getBusType() {
		return busType;
	}



	public void setBusType(String busType) {
		this.busType = busType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
