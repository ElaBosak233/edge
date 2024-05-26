package dev.e23.edge.repository;

import dev.e23.edge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // 告诉 Spring 这是一个数据访问组件，继承于 JpaRepository，表示这是一个 JPA 数据访问组件
public interface UserRepository extends JpaRepository<User, Integer> {
    /*
    根据用户名查询用户
     */
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);
}
