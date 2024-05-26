package dev.e23.edge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data // Lombok 注解，自动生成 getter()，setter()，toString() 等方法
@Entity // 告诉 Spring 这是一个实体类
@EntityListeners(AuditingEntityListener.class) // 启用 JPA 审计功能（即自动填充创建时间和更新时间）
@Table(name = "users") // 指定数据库中的表名
public class User implements Serializable {
    /*
    用户的 ID，自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    用户名，不允许为空，且唯一
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /*
    昵称，不允许为空
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /*
    邮箱，不允许为空，且唯一
     */
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /*
    密码，不允许为空
     */
    @Column(name = "password", nullable = false)
    private String password;

    /*
    创建时间，由 JPA 自动生成
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("created_at") // 序列化的时候将 createdAt 转换为 created_at，更符合前端的命名规范
    private Date createdAt;

    /*
    更新时间，由 JPA 自动生成
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @JsonProperty("updated_at") // 序列化的时候将 updatedAt 转换为 updated_at，更符合前端的命名规范
    private Date updatedAt;

    public User() {}

    public User(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}
