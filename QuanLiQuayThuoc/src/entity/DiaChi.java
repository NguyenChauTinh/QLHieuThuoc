package entity;

import java.util.Objects;

public class DiaChi {
	private String maDiaChi;
	private String soNha;
	private String tenDuong;
	private String phuong;
	private String quan;
	private String thanhPho;
	public DiaChi(String maDiaChi, String soNha, String tenDuong, String phuong, String quan, String thanhPho) {
		super();
		this.maDiaChi = maDiaChi;
		this.soNha = soNha;
		this.tenDuong = tenDuong;
		this.phuong = phuong;
		this.quan = quan;
		this.thanhPho = thanhPho;
	}
	public DiaChi(String ma) {
		super();
		this.maDiaChi = ma;
		// TODO Auto-generated constructor stub
	}
	public String getMaDiaChi() {
		return maDiaChi;
	}
	public void setMaDiaChi(String maDiaChi) {
		this.maDiaChi = maDiaChi;
	}
	public String getSoNha() {
		return soNha;
	}
	public void setSoNha(String soNha) {
		this.soNha = soNha;
	}
	public String getTenDuong() {
		return tenDuong;
	}
	public void setTenDuong(String tenDuong) {
		this.tenDuong = tenDuong;
	}
	public String getPhuong() {
		return phuong;
	}
	public void setPhuong(String phuong) {
		this.phuong = phuong;
	}
	public String getQuan() {
		return quan;
	}
	public void setQuan(String quan) {
		this.quan = quan;
	}
	public String getThanhPho() {
		return thanhPho;
	}
	public void setThanhPho(String thanhPho) {
		this.thanhPho = thanhPho;
	}
	
	@Override
	public String toString() {
		return "DiaChi [maDiaChi=" + maDiaChi + ", soNha=" + soNha + ", tenDuong=" + tenDuong + ", phuong=" + phuong
				+ ", quan=" + quan + ", thanhPho=" + thanhPho + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maDiaChi);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiaChi other = (DiaChi) obj;
		return Objects.equals(maDiaChi, other.maDiaChi);
	}
	
	
	
	
	
}