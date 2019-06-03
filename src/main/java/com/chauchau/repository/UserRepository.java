package com.chauchau.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.chauchau.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
    
    @Query(value = "SELECT * FROM USERS WHERE phone_number = ?1",
    	    nativeQuery = true)
    User getUserbyPhone(String phone);
   
    /*
     * need annotation Transactional for Modifying
     * Modifying --> excuteUpdate (return integer value )
     * 0: it mean unsuccessful*/
    
    @Transactional
    @Modifying
    @Query(value = "Update users set  email = ?1, first_name=?2 where id=?3 ",
    	    nativeQuery = true)
    int updateUser(String param1,String param2,long id);

    @Transactional
    @Modifying
    @Query(value = "Delete from users where id=?1 ",
    	    nativeQuery = true)
    int deleteUser(long id);

}

