package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RestTemplate restTemplate;
    private final Environment env;

    @Override
    public UserEntity createUser(UserDto userDto) {
        UserEntity user = UserEntity.CREATE(userDto, bCryptPasswordEncoder);
        userRepository.save(user);
        return user;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId + " 존재하는 계정이 없습니다."));
    }

    @Override
    public UserDto getUserDtoByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId + " 존재하는 계정이 없습니다."));
        UserDto userDto = UserDto.of(userEntity.getUserId(), userEntity.getEmail(), userEntity.getName(), userEntity.getEncryptedPwd());

        String usersOrderUrl = String.format(env.getProperty("order-service.url"), userId);
        ResponseEntity<List<ResponseOrder>> responseEntity = restTemplate.exchange(usersOrderUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResponseOrder>>() {
        });
        List<ResponseOrder> orderList = responseEntity.getBody();
        userDto.setOrders(orderList);

        return userDto;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + ": not found"));
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd()
        ,true,true,true,true,new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String userName) {
        UserEntity userEntity = userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException(userName + ": not found user by userName"));
        return UserDto.of(userEntity.getUserId(), userEntity.getEmail(), userEntity.getName(), userEntity.getEncryptedPwd());
    }
}
