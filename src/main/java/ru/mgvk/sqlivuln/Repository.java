package ru.mgvk.sqlivuln;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<User, Integer> {


    @Query(value = "Select * from User where username=?1 and token=?2", nativeQuery = true)
    List<User> getUsers(String username, String token);



//    static List<User> getVuln(){
//
//    }



}