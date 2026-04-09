package com.moviebooking.identity.service;

import com.moviebooking.identity.dto.*;
import com.moviebooking.identity.entity.Role;
import com.moviebooking.identity.entity.User;
import com.moviebooking.identity.repository.RoleRepository;
import com.moviebooking.identity.repository.UserRepository;
import com.moviebooking.identity.security.CustomUserDetailsService;
import com.moviebooking.identity.security.JwtUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                      CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public void register(RegisterRequest request) {

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(request.username);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setEmail(request.email);
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

  public String login(LoginRequest request) {

    User user = userRepository.findByUsername(request.username)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!passwordEncoder.matches(request.password, user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.username);

    return jwtUtil.generateToken(userDetails);
  }
}