package com.user.role.models.travel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="agent_file", uniqueConstraints = {@UniqueConstraint(columnNames = "fileName")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Agent agent;

    public AgentFile(String fileName, String contentType, byte[] bytes) {
    this.fileName=fileName;
    this.fileType=contentType;
    this.data=bytes;
    }
}
