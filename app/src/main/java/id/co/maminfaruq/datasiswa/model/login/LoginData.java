package id.co.maminfaruq.datasiswa.model.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginData implements Parcelable {

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_user")
    private String namaUser;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("jenkel")
    private String jenkel;

    @SerializedName("no_telp")
    private String noTelp;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("level")
    private String level;


    @Override
    public int describeContents() {
        return 0;
    }

    public LoginData() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_user);
        dest.writeString(this.namaUser);
        dest.writeString(this.alamat);
        dest.writeString(this.jenkel);
        dest.writeString(this.noTelp);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.level);
    }

    protected LoginData(Parcel in) {
        this.id_user = in.readString();
        this.namaUser = in.readString();
        this.alamat = in.readString();
        this.jenkel = in.readString();
        this.noTelp = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.level = in.readString();
    }

    public static final Parcelable.Creator<LoginData> CREATOR = new Parcelable.Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel source) {
            return new LoginData(source);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };
}
