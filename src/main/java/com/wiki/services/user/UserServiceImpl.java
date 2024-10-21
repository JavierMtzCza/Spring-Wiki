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
public class UserServiceImpl implements UserService, UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private RoleRepository roleRepository;

   @Autowired
   private JsonWebTokenUtil tokenUtil;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Override
   @Transactional(readOnly = true)
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

      User userEntity = userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not found"));

      List<SimpleGrantedAuthority> authorities = userEntity
            .getRoles()
            .stream()
            .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol.getName().name())))
            .toList();

      return new org.springframework.security.core.userdetails.User(
            userEntity.getEmail(),
            userEntity.getPassword(),
            userEntity.isEnabled(),
            true,
            true,
            userEntity.isAccountNoLocked(),
            authorities
      );
   }

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

   @Override
   @Transactional
   public UserDTOTokenResponse createUser(UserDTORegister userData) {

      if (userRepository.findUserByEmail(userData.email()).isPresent())
         throw new EntityExistsException("El usuario ya existe");

      Set<Role> roles = roleRepository.findAllByNameIn(userData.roles());

      if (roles.isEmpty()) {
         throw new IllegalArgumentException("No se encontraron roles válidos");
      }

      User newUser = UserMapper.INSTANCE.toUser(userData);
      newUser.setRoles(roles);
      newUser.setPassword(passwordEncoder.encode(userData.password()));
      User savedUser = userRepository.save(newUser);

      UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword(), savedUser.getRoles().stream()
                  .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                  .collect(Collectors.toList()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String token = tokenUtil.createToken(authentication);

      return new UserDTOTokenResponse(newUser.getEmail(), newUser.getName(), token);
   }

   @Override
   public UserDTOTokenResponse loginUser(UserDTOLogin userDataLogin) {

      String email = userDataLogin.email();

      UserDetails userDetails = loadUserByUsername(email);
      if (userDetails == null) {
         throw new BadCredentialsException("Invalid username or password");
      }

      if (!passwordEncoder.matches(userDataLogin.password(), userDetails.getPassword())) {
         throw new BadCredentialsException("Invalid username or password");
      }

      Authentication authentication = new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = tokenUtil.createToken(authentication);

      return new UserDTOTokenResponse(email, email, token);
   }


}
