package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.UserEto;
import com.progzesp.stalking.domain.mapper.UserMapper;
import com.progzesp.stalking.persistance.entity.TeamEntity;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.TeamRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TeamRepo teamRepo;


    @Override
    public UserEto save(UserEto newTask) {

        UserEntity userEntity = userMapper.mapToEntity(newTask);
        Long id = newTask.getTeamId();
        Optional<TeamEntity> optionalTeam = id != null ? teamRepo.findById(id) : Optional.empty();
        TeamEntity team = optionalTeam.orElse(null);
        userEntity.setTeam(team);
        userEntity = this.userRepo.save(userEntity);
        return userMapper.mapToETO(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.getByUsername(username);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // TODO add some permissions?
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
