package me.gdu.springdeveloper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    // findAll() : select * from member ;
    // findById() : select * from member where ( id = 2 );
    // deleteAll() : delete from member;
    // deleteById() : delete from member where ( id = 2 );
    // save() : insert into member values( ?,? ) || update member set .. where id = ?;

    // findByName() : select * from member where name = ?
    //public List<Member> findByName(String name);
    public Optional<Member> findByName(String name);
    // select * from member where name = ? limit 0, 1 Top-N SQL문은 DBMS마다 다름

    // select * from member where email = ?
    // public Optional<Member> findByEmail(String email);

    // findByNameAndEmail() :select * from member where name = ? and email = ?
    // public Optional<Member> findByNameAndEmail(String Name,String Email);
}
