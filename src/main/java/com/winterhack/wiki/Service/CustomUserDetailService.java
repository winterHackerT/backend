package com.winterhack.wiki.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  @Autowired
  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {    
    UserEntity userEntity = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(username + " is not found"));

    List<GrantedAuthority> grantedAuthorites = userEntity
      .getAuthorities()
      .stream()
      .map((x) -> new SimpleGrantedAuthority(x == "" || x == null ? "USER" : x))
      .collect(Collectors.toList());
    
    User user = new User(
      userEntity.getUsername(),
      userEntity.getPassword(),
      grantedAuthorites
    );

    return user;
  }
  
}
