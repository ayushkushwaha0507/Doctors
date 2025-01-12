package com.incapp.doctor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.doctors.model.Appointments;
import com.incapp.doctors.model.Doctor;
import com.incapp.doctors.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
	
	String URL="http://localhost:9900";
	RestTemplate restTemplate=new RestTemplate();
	
	@PostMapping("/register")
	public String register(@ModelAttribute User user,HttpSession session) {
		String API="/user/save";
		//Password Encoding
		//BCryptPasswordEncoder b=new BCryptPasswordEncoder();
		//user.setPassword(b.encode(user.getPassword()));
		//end
		
		User u=restTemplate.postForObject(URL+API,user, User.class);
		session.setAttribute("user", u);
		return "UserHome";
	}
	@PostMapping("/updatePhoto")
	public String updatePhoto(@RequestPart MultipartFile photo,HttpSession session,ModelMap model) throws IOException {
		User user=(User)session.getAttribute("user");
		user.setPhoto(photo.getBytes());
		String API="/user/updatePhoto";
		restTemplate.put(URL+API, user, User.class);
		model.addAttribute("msg","Success!");
		session.setAttribute("user", user);
		return "UserProfile";
	}
	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute User user,HttpSession session,ModelMap model) {
		String API="/user/updateUser";
		restTemplate.put(URL+API, user, User.class);
		model.addAttribute("msg","Success!");
		user=restTemplate.getForObject(URL+"/user/getByEmail/"+user.getEmail(),User.class);
		session.setAttribute("user", user);
		return "UserProfile";
	}

	@GetMapping("/getPhoto")
	public void getPhoto(HttpServletResponse response,HttpSession session ) throws IOException {
		User user=(User)session.getAttribute("user");
		byte image[]=user.getPhoto();
		if(image==null || image.length==0 ) {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("static/images/person.png");
			image=is.readAllBytes();
		}
		response.getOutputStream().write(image);
	}
	@GetMapping("/FindDoctor")
	public String findDoctor() {
		return "FindDoctor";
	}
	@GetMapping("/MyAppointments")
	public String myAppointments(HttpSession session,ModelMap model) {
		String API="/appointment/getByUserEmail/"+((User)session.getAttribute("user")).getEmail();
		List<Appointments> appointments=restTemplate.getForObject(URL+API,List.class);
		model.addAttribute("apts",appointments);
		return "MyAppointments";
	}
	@GetMapping("/Help")
	public String help() {
		return "Help";
	}
	@GetMapping("/UserProfile")
	public String userProfile() {
		return "UserProfile";
	}
	@GetMapping("/UserHome")
	public String userHome() {
		return "UserHome";
	}
	@PostMapping("/videocall")
	public String videocall(@RequestParam String speciality, HttpSession session,ModelMap model) {
		String roomID=(int)Math.round(Math.random()*10000) +"";
		String API="/doctor/userVideoCall/"+speciality+"/"+((User)session.getAttribute("user")).getEmail()+"/"+roomID;
		boolean result=restTemplate.getForObject(URL+API,Boolean.class);
		if(result) {
			model.addAttribute("roomID",roomID);
			model.addAttribute("userName",((User)session.getAttribute("user")).getName());
			return "videocall";
		}else {
			model.addAttribute("msg","No Doctor Avialable!");
			return "UserHome";
		}
	}
}
