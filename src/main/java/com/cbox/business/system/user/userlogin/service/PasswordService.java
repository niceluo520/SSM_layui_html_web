package com.cbox.business.system.user.userlogin.service;

import org.springframework.stereotype.Service;

import com.cbox.base.util.Md5Util;
import com.cbox.business.system.user.userlogin.bean.SysUserVO;

@Service
public class PasswordService {

    /** 校验新就密码是否一致 */
    public boolean matches(SysUserVO userVO, String newPassword) {

        return matches(userVO.getUser_id(), userVO.getPassword(), newPassword, userVO.getSalt());
    }

    /** 校验新就密码是否一致 */
    public boolean matches(String userId, String oldPassword, String newPassword, String salt) {

        String newEncryPwd = encryptPassword(userId, newPassword, salt);
        boolean hasMatch = false;
        if (newEncryPwd.equals(oldPassword)) {
            hasMatch = true;
        }

        return hasMatch;
    }

    /** 加密 */
    public String encryptPassword(String username, String password, String salt) {
        return Md5Util.hash(username + password + salt);
    }

}
