package com.incapp.doctors.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.incapp.doctors.model.Doctor;
import com.incapp.doctors.model.DoctorAvail;
import com.incapp.doctors.model.DoctorNotAvail;
import com.incapp.doctors.model.DoctorOnline;
import com.incapp.doctors.model.User;

public interface DoctorService {
	public void save(Doctor doctor) ;
	public void updateDoctor(Doctor doctor) ;
	public void updatePhoto(Doctor doctor) ;
	public void updateDocAvail(String email,DoctorAvail doctorAvail);
	public List<Doctor> get() ;
	public List<Doctor> get(String name) ;
	public List<Doctor> get(String state,String city);
	public List<Doctor> getBySpeciality(String speciality);
	public Doctor getByEmail(String email) ;
	public boolean addDocNotAvail(DoctorNotAvail doctorNotAvail);
	public List<DoctorNotAvail> getDocNotAvail(String email) ;
	public Doctor login(String email,String password) ;
	public DoctorOnline doctorAvailable(String available, String docEmail);
	public DoctorOnline checkDoctorVideoCall(String docEmail) ;
	public boolean userVideoCall(String speciality, String userEmail,String roomId);
	public DoctorOnline doctorVideoCallCancel(String doctorEmail);
}
