package com.ra2.users.com_ra2_users.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra2.users.com_ra2_users.model.User;
import com.ra2.users.com_ra2_users.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public int save(String name, String description, String email, String password, Timestamp ultimAcces, Timestamp dataCreated, Timestamp dataUpdated) {
        return userRepository.save(name, description, email, password, ultimAcces, dataCreated, dataUpdated);
    }

	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	public int modifyUser(User user, Long id) {
		return userRepository.modifyUser(user, id);
	}

	public int deleteUser(Long id) {
		return userRepository.deleteUser(id);
	}

	public String uploadImage(Long user_id, MultipartFile imageFile) {
		
		User user = userRepository.findOne(user_id);

		if (user != null) {
			return "existe";

		} else {
			return "No existeix l'usuari";
		}
	}

}
