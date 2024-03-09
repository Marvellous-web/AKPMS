package com.idsargus.akpmsauthservice.config;

import com.idsargus.akpmsauthservice.entity.UserEntity;
import com.idsargus.akpmsauthservice.repository.IUserRepository;
import com.idsargus.akpmsauthservice.service.CustomUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class CustomSessionRegistry {

    private Map<String, String> activeSessions = new ConcurrentHashMap<>();
    @Autowired
    private IUserRepository userRepository;
    public void registerSession(String username, String basicToken)
    {
            activeSessions.put(username,basicToken);
    }

    public void removeSession(String username){
        activeSessions.remove(username);
    }

//    public void invalidateAllOtherSessions(String username, HttpSession currentSession){
//        activeSessions.entrySet()
//                .stream()
//                .filter(entry-> entry.getKey().equals(username))
//                .filter(entry->!entry.getValue().equals(currentSession.getId()))
//                .forEach(entry -> entry.getValue().invalidate());
//    }

    public boolean isSessionActive(String username, String sessionId){
        UserEntity isUserExists = userRepository.findByUsername(username);
        if(isUserExists != null && activeSessions.get(username) != null) {
            return sessionId.equals(activeSessions.get(username));
        }else {
            return false;
        }
    }

    public String getSession(String username){
        return activeSessions.get(username);
    }
}
