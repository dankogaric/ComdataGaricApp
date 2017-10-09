package com.comdata.factory.app.web.rest.dto;

import com.comdata.factory.app.domain.CityBus;
import com.comdata.factory.app.domain.InterCityBus;
import com.comdata.factory.app.domain.Bus;
import com.comdata.factory.app.domain.Manufacturer;

public class BusDTO {
	/**
	 * ENUM for Front-end mapping from DTO to appropriate entity!!!
	 */
	private static final String BUS_TYPE_CITY = "CityBus";
	private static final String BUS_TYPE_INTER_CITY = "InterCityBus";
	
    private Long id;

    private Integer seatsSitting;
    private Integer seatsStanding;
    private String color;
    private Boolean hasWhrist;
    private Integer trunkCapacity;
    private Manufacturer manufacturer;
    
    
    private String busType;

    /**
      "seatsNumber": 4,
      "roofTopCapacity": 100,
      "busType": "ClassicBus"
      
      roofTopCapacity
      busType
     */
    
    /**
     
     {
     	    "color": "blue",
    	"manufacturer": {
    		"id":1
    		},
    		"seatsSitting" : 1,
    		"seatsStanding": 2,
    		"hasWhrist": true,
    		"busType": "CityBus"
    	}
        
    
    
    
     
     
     */
    
	public BusDTO() {
		super();	
	}

	public BusDTO(Bus busEntity) {
		this.id = busEntity.getId();
		this.seatsSitting = busEntity.getSeatsSitting();
		this.seatsStanding = busEntity.getSeatsStanding();
		this.color = busEntity.getColor();
		this.manufacturer = new Manufacturer();
		this.manufacturer.setId(busEntity.getManufacturer().getId());
		this.manufacturer.setName(busEntity.getManufacturer().getName());
			
		if(busEntity instanceof CityBus) {
			busType = BUS_TYPE_CITY;
			this.hasWhrist = ((CityBus)busEntity).isHasWhrist();
			
		} else if (busEntity instanceof InterCityBus) {
			busType = BUS_TYPE_INTER_CITY;
			this.trunkCapacity = ((InterCityBus)busEntity).getTrunkCapacity();
		}
		
	}


	public Bus convertToBusEntity () {
		// determine which type of bus is this
		if(busType.equals(BUS_TYPE_CITY)) {
			CityBus returnValue = new CityBus();
			returnValue.setId(id);
			returnValue.setHasWhrist(hasWhrist);
			returnValue.setSeatsSitting(seatsSitting);
			returnValue.setSeatsStanding(seatsStanding);
			returnValue.setManufacturer(manufacturer);
			returnValue.setColor(color);
			return returnValue;
			
		} else {
			InterCityBus returnValue = new InterCityBus();
			returnValue.setId(id);
			returnValue.setTrunkCapacity(trunkCapacity);
			returnValue.setSeatsSitting(seatsSitting);
			returnValue.setSeatsStanding(seatsStanding);
			returnValue.setManufacturer(manufacturer);
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
	
	
	

	
	
	
    
    

}
