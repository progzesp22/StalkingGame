package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.UserEto;

public interface UserService extends Service {

    UserEto encodePasswordAndSave(UserEto newUser);

    UserEto save(UserEto newUser);

    UserEto getByUsername(String username);

}
