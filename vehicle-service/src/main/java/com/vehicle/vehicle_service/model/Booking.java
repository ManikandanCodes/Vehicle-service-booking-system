package com.vehicle.vehicle_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String vehicle;
    private String model;
    private String regNumber;
    private String serviceType;
    private String partService;
    private String pickupOption;
    private String date;
    private String timeSlot;
    private String remarks;
    private String status = "Pending"; 

    
    public Booking() {}

    
    public Booking(String name, String email, String phone, String vehicle, String model,
                   String regNumber, String serviceType, String partService, String pickupOption,
                   String date, String timeSlot, String remarks, String status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.vehicle = vehicle;
        this.model = model;
        this.regNumber = regNumber;
        this.serviceType = serviceType;
        this.partService = partService;
        this.pickupOption = pickupOption;
        this.date = date;
        this.timeSlot = timeSlot;
        this.remarks = remarks;
        this.status = status;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPartService() {
        return partService;
    }

    public void setPartService(String partService) {
        this.partService = partService;
    }

    public String getPickupOption() {
        return pickupOption;
    }

    public void setPickupOption(String pickupOption) {
        this.pickupOption = pickupOption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
