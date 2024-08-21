package com.example.user.service;

import com.example.common.PriceType;
import com.example.user.data.UserDAO;
import com.example.user.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(int userUid) {
        return userDAO.getUserByUid(userUid);
    }

    public boolean hasEnoughTime(int userUid, PriceType priceType) {
        User user = userDAO.getUserByUid(userUid);
        if (user != null && user.getResttime() >= getTimeInHours(priceType)) {
            return true;
        } else {
            System.out.println("보유 시간이 부족합니다. 시간을 충전하시겠습니까? (예/아니오)");
            return false;
        }
    }

    public void deductTime(int userUid, PriceType priceType) {
        userDAO.updateUserTime(userUid, getTimeInHours(priceType));
    }

    public int getTimeInHours(PriceType priceType) {
        switch (priceType) {
            case ONEHOUR:
                return 1;
            case TWOHOUR:
                return 2;
            case THREEHOUR:
                return 3;
            default:
                throw new IllegalArgumentException("Unknown PriceType: " + priceType);
        }
    }
}
