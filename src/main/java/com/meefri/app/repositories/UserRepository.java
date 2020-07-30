package com.meefri.app.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.meefri.app.data.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String userName);
    User findByEmail(String email);
    User findByUsernameAndPassword(String userName, String password);
}
