package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 로그인 인증을 위한 로직
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Security 에서 제공하는 User 클래스로 리턴
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>()); // 로그인 후 얻을 수 있는 권한들
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto resultDto = modelMapper.map(userEntity, UserDto.class);
        return resultDto;
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity byUserId = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDto resultUser = modelMapper.map(byUserId, UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
        resultUser.setOrders(orders);

        return resultUser;
    }

    @Override
    public Iterable<UserDto> getUserAll() {
        List<UserEntity> userAll = userRepository.findAll();
        List<UserDto> resultUserAll = userAll.stream().map(e -> modelMapper.map(e, UserDto.class))
                .collect(Collectors.toList());
        return resultUserAll;
    }

    @Override
    public UserDto getUserDetailsByEmail(String userName) {
        UserEntity userEntity = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        ModelMapper modelMapper1 = new ModelMapper();
        UserDto userDto = modelMapper1.map(userEntity, UserDto.class);
        return userDto;
    }
}
