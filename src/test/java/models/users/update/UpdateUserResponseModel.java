package models.users.update;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserResponseModel {
    public String name;
    public String job;
    public Date updatedAt;
}
