package com.m2s.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2s.dto.UserDTO;
import com.m2s.entities.User;
import com.m2s.message.MessageStatus;
import com.m2s.repository.UserRepository;
import com.m2s.service.UserService;
import com.m2s.utils.GenericUtility;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public MessageStatus updateUser(UserDTO userDto) {
		log.info("Inside user service UpdateUser method ");
		try {
			User userDb = userRepository.findById(userDto.getId()).orElse(null);
			BeanUtils.copyProperties(userDto, userDb, GenericUtility.getNullPropertyNames(userDto));
			userRepository.save(userDb);
			
			log.info("User is updated successfully.");
		} catch (Exception e) {
			log.error("Exception Occured while updating User", e);
		}
		return new MessageStatus("User is updated successfully.", HttpServletResponse.SC_OK);
	}

	@Override
	public MessageStatus createUser(UserDTO userDto) {
		log.info("Inside user service createUser method");
		try {
			User user = new User();
			BeanUtils.copyProperties(userDto, user, GenericUtility.getNullPropertyNames(userDto));
			userRepository.save(user);
			log.info("User is created successfully.");
		} catch (Exception e) {
			log.error("Exception Occured while creating User", e);
		}
		return new MessageStatus("User has been sucessfully Create", HttpServletResponse.SC_CREATED);
	}

	@Override
	public MessageStatus<List<UserDTO>> findAll(Integer orderId) {
		MessageStatus msg = new MessageStatus<>();
		log.info("Inside User List");
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		try {
			List<User> userDB = userRepository.findAll();
			for (User user : userDB) {
				UserDTO userDto = new UserDTO();
				BeanUtils.copyProperties(user, userDto, GenericUtility.getNullPropertyNames(user));
				userDtoList.add(userDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Successfully get User list");
		msg.setData(userDtoList);
		msg.setMessage("List get sucessfulyt");
		msg.setStatusCode(200);
		return msg;
	}

	@Override
	public MessageStatus getUser(Integer userId) {
		log.info("Inside find User by id");
		UserDTO userDto = new UserDTO();
		MessageStatus msg = new MessageStatus();
		try {
			User user = userRepository.findById(userId.longValue()).orElse(null);
			if (user == null) {
				msg.setStatusCode(HttpServletResponse.SC_NOT_FOUND);
				msg.setMessage("Invalid user id");
				return msg;
			}
			BeanUtils.copyProperties(user, userDto, GenericUtility.getNullPropertyNames(user));
			msg.setData(userDto);
			msg.setMessage("sucessfully get user details");
			msg.setStatusCode(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Successfully get User details");
		return msg;
	}

	@Override
	public MessageStatus deleteUser(Long userId) {
		log.info("Inside delete User by id");
		MessageStatus msg = new MessageStatus();
		try {
			userRepository.deleteById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		msg.setMessage("user has been deleted successfully");
		msg.setStatusCode(HttpServletResponse.SC_OK);
		return msg;
	}
}
