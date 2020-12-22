package com.h3c.vdi.viewscreen.security.basic;

import com.h3c.vdi.viewscreen.api.result.ApiErrorEnum;
import com.h3c.vdi.viewscreen.constant.Constant;
import com.h3c.vdi.viewscreen.dao.UserDao;
import com.h3c.vdi.viewscreen.entity.User;
import com.h3c.vdi.viewscreen.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Date 2020/7/17 16:57
 * @Created by lgw2845
 */
@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsernameAndLogicDelete(username, Constant.LogicDelete.LOGIC_DELETE_N);
        if (Objects.isNull(user) || Objects.isNull(user.getUsername()) || Objects.isNull(user.getPassword()) || Objects.isNull(user.getRoles())) {
            throw new BusinessException(ApiErrorEnum.S00001.getName(), Constant.Message.ERROR_FAILURE);
        }
        List<String> roles = user.getRoles().stream().map(item -> "ROLE_"+item.getName()).collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(),user.getUsername(), user.getPassword(), roles);
    }
}
