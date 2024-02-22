package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user= this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.userdToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		;
		return this.userdToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		return this.userdToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users=this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user->this.userdToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		
//		User user=new User();
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
		
		return user;
	}
	
	private UserDto userdToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
//		UserDto userDto=new UserDto();
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		
		return userDto;
	}
}
