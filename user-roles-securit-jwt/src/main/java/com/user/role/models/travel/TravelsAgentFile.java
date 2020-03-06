package com.user.role.models.travel;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "agent_files")
public class TravelsAgentFile implements Serializable {

    @Id
    @GenericGenerator(name = "file_id",
            strategy = "com.user.role.models.travel.CustomeAgentFileGenerator")
    @GeneratedValue(generator = "file_id")
    private String id;
    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "agent_file_fk",nullable = true)
    private TravelsAgent agent;

    public TravelsAgentFile() {
    }

    public TravelsAgentFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }
}
