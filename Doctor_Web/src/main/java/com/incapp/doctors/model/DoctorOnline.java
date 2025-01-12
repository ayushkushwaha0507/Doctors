package com.incapp.doctors.model;

import org.springframework.stereotype.Component;

@Component
public class DoctorOnline {
	private String docEmail;
	private String userEmail;
	private String speciality;
	private String roomId;
	
	public String getDocEmail() {
		return docEmail;
	}
	public void setDocEmail(String docEmail) {
		this.docEmail = docEmail;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	@Override
	public String toString() {
		return "DoctorOnline [docEmail=" + docEmail + ", userEmail=" + userEmail + ", speciality=" + speciality
				+ ", roomId=" + roomId + "]";
	}
}
