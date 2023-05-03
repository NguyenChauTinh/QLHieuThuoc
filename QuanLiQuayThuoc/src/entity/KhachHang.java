package entity;

import java.util.Objects;

public class KhachHang {
	private String maKH;
	private String hoKH;
	private String tenKH;
	private String gioiTinh;
	private String soDienThoai;
	public KhachHang(String maKH, String hoKH, String tenKH, String gioiTinh, String soDienThoai) {
		super();
		this.maKH = maKH;
		this.hoKH = hoKH;
		this.tenKH = tenKH;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
	}
	public KhachHang(String ma) {
		super();
		this.maKH = ma;
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getHoKH() {
		return hoKH;
	}
	public void setHoKH(String hoKH) {
		this.hoKH = hoKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", hoKH=" + hoKH + ", tenKH=" + tenKH + ", gioiTinh=" + gioiTinh
				+ ", soDienThoai=" + soDienThoai + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKH);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKH, other.maKH);
	}
		
}
