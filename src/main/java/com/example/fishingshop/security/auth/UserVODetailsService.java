package com.example.fishingshop.security.auth;

import com.example.fishingshop.models.User;
import com.example.fishingshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserVODetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        if(user.isEmpty()){
            user = userRepository.findUserBySurname(username);
            if(user.isEmpty()){
                throw new UsernameNotFoundException("User not found");
            }
        }
        return new UserVODetails(user.get());
    }
}
