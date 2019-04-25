package id.co.maminfaruq.datasiswa.model.login.kelas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KelasResponse{

	@SerializedName("result")
	private int result;

	@SerializedName("data")
	private List<KelasData> data;

	@SerializedName("message")
	private String message;

	public void setResult(int result){
		this.result = result;
	}

	public int getResult(){
		return result;
	}

	public void setData(List<KelasData> data){
		this.data = data;
	}

	public List<KelasData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}