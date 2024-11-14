package com.auction.onlineauctionsystem.services;

import org.springframework.boot.autoconfigure.web.ServerProperties.Reactive.Session;

import com.auction.onlineauctionsystem.entities.user;
import com.auction.onlineauctionsystem.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

public class authenticateUser {
    
    private static UserRepository userRepository;

    public authenticateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static boolean authenticate(String userName, String password) {
        try {
            user userCheck = userRepository.findByUsernameAndPassword(userName, password);
            return true;
        }
        catch(NullPointerException e) {
            return false;
        }
    }
    public static boolean authenticate(String userName)
    {
        
        try{
            user userCheck=userRepository.findByUsername(userName);
            return true;
        }
        catch(NullPointerException e){
            return false;
        }
        
    }
    public boolean isLogin(String userName,String password,HttpSession session)
    {
        String name=(String) session.getAttribute("userName");
        return false;
    }

    // other methods
}
