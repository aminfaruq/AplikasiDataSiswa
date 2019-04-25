package id.co.maminfaruq.datasiswa.model.login.kelas;

import com.google.gson.annotations.SerializedName;

public class KelasData {

	@SerializedName("nama_jurusan")
	private String namaJurusan;

	@SerializedName("view")
	private String view;

	@SerializedName("foto_kelas")
	private String fotoKelas;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("nama_kelas")
	private String namaKelas;

	@SerializedName("nama_wali")
	private String namaWali;

	@SerializedName("insert_time")
	private String insertTime;

	@SerializedName("url_kelas")
	private String urlKelas;

	@SerializedName("id_kelas")
	private String idKelas;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("nama_user")
	private String namaUser;

	@SerializedName("nama_kategori")
	private String namaKategori;

	@SerializedName("foto_kategori")
	private String fotoKategori;

	public String getFotoKategori() {
		return fotoKategori;
	}

	public void setFotoKategori(String fotoKategori) {
		this.fotoKategori = fotoKategori;
	}

	public void setNamaJurusan(String namaJurusan){
		this.namaJurusan = namaJurusan;
	}

	public String getNamaJurusan(){
		return namaJurusan;
	}

	public void setView(String view){
		this.view = view;
	}

	public String getView(){
		return view;
	}

	public void setFotoKelas(String fotoKelas){
		this.fotoKelas = fotoKelas;
	}

	public String getFotoKelas(){
		return fotoKelas;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
	}

	public void setNamaKelas(String namaKelas){
		this.namaKelas = namaKelas;
	}

	public String getNamaKelas(){
		return namaKelas;
	}

	public void setNamaWali(String namaWali){
		this.namaWali = namaWali;
	}

	public String getNamaWali(){
		return namaWali;
	}

	public void setInsertTime(String insertTime){
		this.insertTime = insertTime;
	}

	public String getInsertTime(){
		return insertTime;
	}

	public void setUrlKelas(String urlKelas){
		this.urlKelas = urlKelas;
	}

	public String getUrlKelas(){
		return urlKelas;
	}

	public void setIdKelas(String idKelas){
		this.idKelas = idKelas;
	}

	public String getIdKelas(){
		return idKelas;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setNamaUser(String namaUser){
		this.namaUser = namaUser;
	}

	public String getNamaUser(){
		return namaUser;
	}

	public void setNamaKategori(String namaKategori){
		this.namaKategori = namaKategori;
	}

	public String getNamaKategori(){
		return namaKategori;
	}
}