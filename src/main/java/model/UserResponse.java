package model;

import lombok.Data;

@Data
public class UserResponse {
    private String accessToken;
    private String refreshToken;
}
