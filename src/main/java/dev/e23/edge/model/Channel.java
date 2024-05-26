package dev.e23.edge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
@Entity // 告诉 Spring 这是一个实体类
@Table(name = "channels") // 指定数据库中的表名
@EntityListeners(AuditingEntityListener.class) // 启用 JPA 审计功能（即自动填充创建时间和更新时间）
public class Channel implements Serializable {
    /*
    频道的 ID，自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    频道的名字，不允许为空
     */
    @Column(name = "name", nullable = false)
    private String name;

    /*
    创建时间，由 JPA 自动生成
     */
    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_at") // 序列化的时候将 createdAt 转换为 created_at，更符合前端的命名规范
    private Date createdAt;

    /*
    更新时间，由 JPA 自动生成
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonProperty("updated_at") // 序列化的时候将 updatedAt 转换为 updated_at，更符合前端的命名规范
    private Date updatedAt;

    /*
    一个频道可以有多条消息，这里使用了一对多的关系，mappedBy 指定了消息类中的 channel 属性，表示这个属性是由消息类中的 channel 属性维护的
    @JsonIgnoreProperties({"channel"}) 表示在序列化的时候忽略掉消息类中的 channel 属性
    避免循环引用（即频道中有消息，消息中有频道，频道中有消息，消息中有频道……）（这样子会导致序列化的时候出现栈溢出）
     */
    @OneToMany(mappedBy = "channel")
    @JsonIgnoreProperties({"channel"})
    private List<Message> messages;

    public Channel() {}

    public Channel(String name) {
        this.name = name;
    }
}
