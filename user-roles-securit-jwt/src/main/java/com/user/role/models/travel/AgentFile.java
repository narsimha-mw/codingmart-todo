package com.user.role.models.travel;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "agent_files", uniqueConstraints = {@UniqueConstraint (columnNames="fileName")})
public class AgentFile implements Serializable {

    @Id
    @GenericGenerator(name = "file_id",
            strategy = "com.user.role.models.travel.CustomeAgentFileGenerator")
    @GeneratedValue(generator = "file_id")
    private String id;
    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_file_fk",nullable = true)
    private Agent agent;

    public AgentFile() {
    }

    public AgentFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
