package com.m2s.service;

import java.util.List;

import com.m2s.dto.UserDTO;
import com.m2s.message.MessageStatus;

public interface UserService {
	public MessageStatus updateUser(UserDTO user);
	public MessageStatus createUser(UserDTO user);
	public MessageStatus<List<UserDTO>> findAll();
	public MessageStatus getUser(Integer userId);
	public MessageStatus deleteUser(Long userId);
}
