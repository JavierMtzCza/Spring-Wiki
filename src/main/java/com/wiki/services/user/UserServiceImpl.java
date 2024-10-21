package com.wiki.services.user;

import com.wiki.models.role.entities.Role;
import com.wiki.models.role.repositories.RoleRepository;
import com.wiki.models.user.dtos.UserDTOLogin;
import com.wiki.models.user.dtos.UserDTORegister;
import com.wiki.models.user.dtos.UserDTOTokenResponse;
import com.wiki.models.user.dtos.mappers.UserMapper;
import com.wiki.models.user.entities.User;
import com.wiki.models.user.repositories.UserRepository;
import com.wiki.utils.JsonWebTokenUtil;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
