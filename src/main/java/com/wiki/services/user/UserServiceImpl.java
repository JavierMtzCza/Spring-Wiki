package com.wiki.services.user;

import com.wiki.models.user.entities.User;
import com.wiki.models.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private UserRepository userRepository;


   @Override
   @Transactional(readOnly = true)
   public User getUserByEmail(String email) {
      return userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found by email"));
   }

   @Override
   @Transactional(readOnly = true)
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

}
