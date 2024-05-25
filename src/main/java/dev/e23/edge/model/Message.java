package dev.e23.edge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password", "createdAt", "updatedAt"})
    private User user;

    @JsonProperty("user_id")
    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    @JsonIgnoreProperties({"messages"})
    private Channel channel;

    @JsonProperty("channel_id")
    @Column(name = "channel_id", insertable = false, updatable = false)
    private Integer channelId;

    public Message() {}
}
