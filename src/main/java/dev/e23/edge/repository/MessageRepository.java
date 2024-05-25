package dev.e23.edge.repository;

import dev.e23.edge.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.channelId = ?1 ORDER BY m.createdAt DESC LIMIT 20")
    List<Message> findByChannelId(Integer channelId);
}
