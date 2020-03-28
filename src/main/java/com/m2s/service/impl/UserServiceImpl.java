package com.m2s.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2s.entities.User;
import com.m2s.entities.UserDTO;
import com.m2s.message.MessageStatus;
import com.m2s.repository.UserRepository;
import com.m2s.service.UserService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;


	@Override
	public MessageStatus updateUser(UserDTO user) {
		System.out.println("updateUser method");
		MessageStatus msg = new MessageStatus();
		List<UserDTO> userList=new ArrayList<UserDTO>();
		
		 File file = new File("output.csv");
		 boolean exists = file.exists();
		 if(!exists) {
			 try { 
				file.createNewFile();
			 } catch (IOException e) {
		        e.printStackTrace(); 
			 } 
		 }
		 
		 userList=readAllDataFromFile(file);
	
		// password encrypt
		if (user.getId() == null) {
					return new MessageStatus("Please provide id od user", HttpServletResponse.SC_OK);
		} else { 
				System.out.println("Inside update user");
				 UserDTO userDTO=getUser(user.getId());
				 int pos =userList.indexOf(userDTO); 
				 userDTO.setFirstName(user.getFirstName());
				 userDTO.setLastName(user.getLastName());
				 userList.remove(pos);
				 userList.add(pos,userDTO);
				 writeDataLineByLine(file,userList);  
				return new MessageStatus("User has been sucessfully update", HttpServletResponse.SC_OK);
		}
	}


	@Override
	public MessageStatus createUser(UserDTO user) {
		System.out.println("updateUser method");
		MessageStatus msg = new MessageStatus();
		List<UserDTO> userList=new ArrayList<UserDTO>();
		
		 File file = new File("output.csv");
		 boolean exists = file.exists();
		 if(!exists) {
			 try { 
				file.createNewFile();
			 } catch (IOException e) {
		        e.printStackTrace(); 
			 } 
		 }
		 
		 userList=readAllDataFromFile(file);
		 if(userList.size()==0) {
			 user.setId(1);
			 userList.add(user);
			 System.out.println("new user=====" + user);
		 }
		 else{
			 if(user.getId()==null || user.getId()==0 ) {
				 user.setId(userList.size()+1);
				 userList.add(user);
			 }
			 else{
				 return new MessageStatus("please remove user id", HttpServletResponse.SC_OK);
			 }
		 }
	        
		 writeDataLineByLine(file,userList);
		 
		return new MessageStatus("User has been sucessfully Create", HttpServletResponse.SC_OK);
	}

	public List<UserDTO> readAllDataFromFile(File file) 
	{ 
		 List<UserDTO> userList=new ArrayList<UserDTO>();
	    try { 
	        // Create an object of file reader 
	        // class with CSV file as a parameter. 
	        FileReader filereader = new FileReader(file); 
	  
	        // create csvReader object and skip first Line 
	        CSVReader csvReader = new CSVReaderBuilder(filereader) 
	                                  .withSkipLines(1) 
	                                  .build(); 
	        List<String[]> allData = csvReader.readAll(); 
	       
	        // print Data 
	        for (String[] row : allData) { 
	               UserDTO user=new UserDTO();
	               user.setId(Integer.parseInt(row[0]));
	               user.setFirstName(row[1]);
	               user.setLastName(row[2]);
	               System.out.println("===========user" + user);
	               userList.add(user);  
	        } 
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    } 
	    return userList;
	} 
	
	public static void writeDataLineByLine(File file, List<UserDTO> userList) 
	{ 
	    try { 
	    	
	    	FileWriter outputfile = new FileWriter(file); 	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile); 
	  
	        // adding header to csv 
	        String[] header = { "Id", "FirstName", "LastName" }; 
	        writer.writeNext(header); 
	    
	        for (UserDTO userDTO : userList) {
	        	 String[] data= {userDTO.getId().toString(),userDTO.getFirstName(), userDTO.getLastName() };
	        	 System.out.println(userDTO);
	        	 writer.writeNext(data);
			}
	        // closing writer connection 
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	}

	@Override
	public MessageStatus<List<UserDTO>> findAll(Integer orderId) {
		MessageStatus msg=new MessageStatus<>();
		
		if(orderId==null)
			orderId=0;
		
		File file = new File("output.csv");
		 boolean exists = file.exists();
		 if(!exists) {
			 try { 
				file.createNewFile();
			 } catch (IOException e) {
		        e.printStackTrace(); 
			 } 
		 }
		 List<UserDTO> userFromFile= readAllDataFromFile(file);
		 if(orderId==1){
			 Comparator<UserDTO> compareById = (UserDTO o1, UserDTO o2) -> 
	         o1.getId().compareTo( o2.getId() );
	         Collections.sort(userFromFile, compareById);
		 }
		 else if(orderId==2){
			//First name sorter
			 Comparator<UserDTO> compareByFirstName = (UserDTO o1, UserDTO o2) ->
			          o1.getFirstName().compareTo( o2.getFirstName() );
			          Collections.sort(userFromFile, compareByFirstName);
		 }
		 else
		 {
			//Last name sorter
			 Comparator<UserDTO> compareByLastName = (UserDTO o1, UserDTO o2) -> 
			 o1.getLastName().compareTo( o2.getLastName() );
			 Collections.sort(userFromFile, compareByLastName);
		 }
		 
		
		msg.setData(userFromFile);
		msg.setMessage("List get sucessfulyt");
		msg.setStatusCode(200);
		return msg;
	}

	@Override
	public MessageStatus synchToDB() {
		 File file = new File("output.csv");
		 boolean exists = file.exists();
		 if(!exists) {
			 try { 
				file.createNewFile();
			 } catch (IOException e) {
		        e.printStackTrace(); 
			 } 
		 }
		 List<UserDTO> userFromFile= readAllDataFromFile(file);
		 for (UserDTO userDTO : userFromFile) {
			 User user=new User();
			 BeanUtils.copyProperties(userDTO, user);
			 System.out.println("user Data ========"+ user);
			 userRepository.save(user);
		}
		return new MessageStatus<>("Data synch successfully to db", 200);
	}

	@Override
	public UserDTO getUser(Integer userId) {
		 File file = new File("output.csv");
		 boolean exists = file.exists();
		 if(!exists) {
			 try { 
				file.createNewFile();
			 } catch (IOException e) {
		        e.printStackTrace(); 
			 } 
		 }
		 List<UserDTO> userFromFile= readAllDataFromFile(file);
		 Map<Integer, UserDTO> usermap=new HashMap<Integer,UserDTO>();
		 for (UserDTO userDTO : userFromFile) {
			 usermap.put(userDTO.getId(), userDTO);
		}
		 UserDTO user=usermap.get(userId);
		 return user;
	}	
}
