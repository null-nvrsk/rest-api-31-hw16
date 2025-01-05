package models.users.single;

import lombok.Data;

import java.util.Date;

@Data
public class CreateUserResponseModel {
    public String name;
    public String job;
    public String id;
    public Date createdAt;
}
