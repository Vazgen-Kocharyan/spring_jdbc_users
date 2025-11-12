package com.ra2.users.com_ra2_users.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public ResponseEntity<String> uploadImage(Long user_id, MultipartFile imageFile) {
		
		User user = userRepository.findOne(user_id);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		try {
			Path pathFitxer = Paths.get("private/images/" + imageFile.getOriginalFilename());
			Path pathDirectori = Paths.get("private/images");

			Files.createDirectories(pathDirectori);
			Files.copy(imageFile.getInputStream(), pathFitxer);
			
			userRepository.saveImagePath(pathFitxer.toString(), user.getId());

			return ResponseEntity.ok(Paths.get("private/images/" + imageFile.getOriginalFilename()).toString());

		} catch (FileAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("File already exists");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image could not be saved");
		}
	}

	public String uploadCsv(MultipartFile csvFile) {

		int numeroRegistres = 0;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))){
			
			byte[] contenido = csvFile.getBytes();
			
			String linia = br.readLine();

			linia = br.readLine();

			while (linia != null) {
				String[] camps = linia.split(",");

				Timestamp timestamp = new Timestamp(System.currentTimeMillis());

				User user = new User(camps[0], camps[1], camps[2], camps[3]);

				numeroRegistres += userRepository.save(user.getName(), user.getDescription(), user.getEmail(), user.getPassword(), timestamp, timestamp, timestamp);

				linia = br.readLine();
			}

			Path pathDirectori = Paths.get("private/csv_processed");
			Path pathFitxer = Paths.get("private/csv_processed/" + csvFile.getOriginalFilename());

			Files.createDirectories(pathDirectori);
			Files.write(pathFitxer, contenido);

			return "S'han afegit " + numeroRegistres + " registres";

		} catch (IOException e) {
			return null;
		}
	}
}
