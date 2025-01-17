package models.users.single.read;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {
    public int id;
    public String email;
    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String lastName;
    public String avatar;
}
