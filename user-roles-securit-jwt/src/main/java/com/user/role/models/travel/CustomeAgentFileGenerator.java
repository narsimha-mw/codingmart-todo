package com.user.role.models.travel;

import lombok.Data;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Data
public class CustomeAgentFileGenerator implements IdentifierGenerator{

     @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "FIL_";
        Connection connection = session.connection();

        try {
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery("select count(id) as id from agent_files");
           if(rs.next())
            {
                int id=rs.getInt(1)+101;
                String generatedId = prefix + id;
                generatedId.trim();
                return generatedId;
            }
        }catch (NumberFormatException n) {
            n.getMessage();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
