package models.users.read_single;

import lombok.Data;

@Data
public class SingleUserResponseModel {
    public models.users.read_single.Data data;
    public Support support;
}
