package com.user.role.models.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.role.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="agent_file", uniqueConstraints = {
        @UniqueConstraint(columnNames = "fileName")})
@Data
@AllArgsConstructor
public class AgentFile implements Serializable {

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="file_fk", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Agent agent;

    public  AgentFile(){}

    public AgentFile(String fileName, String contentType, byte[] bytes) {
        this.fileName=fileName;
        this.fileType=contentType;
        this.data=bytes;
    }
}
