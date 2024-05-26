package dev.e23.edge.repository;

import dev.e23.edge.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 告诉 Spring 这是一个数据访问组件，继承于 JpaRepository，表示这是一个 JPA 数据访问组件
public interface ChannelRepository extends JpaRepository<Channel, Integer> {}
