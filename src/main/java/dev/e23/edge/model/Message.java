package dev.e23.edge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
@Entity // 告诉 Spring 这是一个实体类
@Table(name = "messages") // 指定数据库中的表名
@EntityListeners(AuditingEntityListener.class) // 启用 JPA 审计功能（即自动填充创建时间和更新时间）
public class Message implements Serializable {
    /*
    消息的 ID，自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    消息的内容
     */
    @Column(name = "content")
    private String content;

    /*
    创建时间，由 JPA 自动生成
     */
    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_at") // 序列化的时候将 createdAt 转换为 created_at，更符合前端的命名规范
    private Date createdAt;

    /*
    一条消息只能由一个用户发送，这里使用了多对一的关系，JoinColumn 指定了消息类中的 user 属性，表示这个属性是由消息类中的 user 属性维护的
    @JsonIgnoreProperties({"password", "createdAt", "updatedAt"})
    表示在序列化的时候忽略掉用户类中的 password、createdAt、updatedAt 属性（这个是为了安全考虑，你有见过返回值带密码的吗）
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password", "createdAt", "updatedAt"})
    private User user;

    /*
    用户的 ID，不允许插入和更新（因为这个是由消息类中的 user 属性维护的）
     */
    @JsonProperty("user_id")
    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    /*
    一条消息只能发送到一个频道，这里使用了多对一的关系，JoinColumn 指定了消息类中的 channel 属性，表示这个属性是由消息类中的 channel 属性维护的
    @JsonIgnoreProperties({"messages"}) 表示在序列化的时候忽略掉频道类中的 messages 属性，避免循环引用，避免栈溢出
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    @JsonIgnoreProperties({"messages"})
    private Channel channel;

    /*
    频道的 ID，不允许插入和更新（因为这个是由消息类中的 channel 属性维护的）
     */
    @JsonProperty("channel_id")
    @Column(name = "channel_id", insertable = false, updatable = false)
    private Integer channelId;

    public Message() {}
}
