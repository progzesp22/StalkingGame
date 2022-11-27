package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.UserEto;
import com.progzesp.stalking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/register")
public class UserRegisterRestController {

	@Autowired
	UserService userService;

	@PostMapping()
	public ResponseEntity<UserEto> registerUser(@RequestBody UserEto newUser) {
		// TODO add permissions to this action
		if(userService.getByUsername(newUser.getUsername()) != null) {
			return ResponseEntity.status(400).body(null);
		}
		userService.encodePasswordAndSave(newUser);
		return ResponseEntity.ok().body(null);
	}

}
