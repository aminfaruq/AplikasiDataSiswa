package id.co.maminfaruq.datasiswa.model.login;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private LoginData data;
}
