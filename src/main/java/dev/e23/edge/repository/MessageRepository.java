package dev.e23.edge.repository;

import dev.e23.edge.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 告诉 Spring 这是一个数据访问组件，继承于 JpaRepository，表示这是一个 JPA 数据访问组件
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /*
    根据频道 ID 查询最新的 20 条消息
     */
    @Query("SELECT m FROM Message m WHERE m.channelId = ?1 ORDER BY m.createdAt DESC LIMIT 20")
    List<Message> findByChannelId(Integer channelId);
}
