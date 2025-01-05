package models.users.single;

import lombok.Data;
import models.users.single.read.Support;

@Data
public class ReadUserResponseModel {
    public models.users.single.read.Data data;
    public Support support;
}
