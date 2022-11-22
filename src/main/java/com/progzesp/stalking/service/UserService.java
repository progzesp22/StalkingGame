package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.UserEto;

public interface UserService extends Service {

    UserEto save(UserEto newTask);

    UserEto getByUsername(String username);

}
