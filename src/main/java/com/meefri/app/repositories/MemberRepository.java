package com.meefri.app.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.meefri.app.data.Member;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {

    Member findByLoginAndPassword(String login, String password);
    Member findByLogin(String login);
    Member findByEmail(String email);

}
