package entity;

import java.util.Date;
import java.util.Objects;

public class NhanVien {
	private String maNV,caLamViec,ten,ho,cccd,soDienThoai;
	private double heSoLuong;
	private Date ngaySinh;
	private DiaChi diaChi;
	private String gioiTinh;
	public NhanVien(String maNV, String caLamViec, String ten, String ho, String cccd, String soDienThoai,
			double heSoLuong, Date ngaySinh, DiaChi diaChi, String gioiTinh) {
		super();
		this.maNV = maNV;
		this.caLamViec = caLamViec;
		this.ten = ten;
		this.ho = ho;
		this.cccd = cccd;
		this.soDienThoai = soDienThoai;
		this.heSoLuong = heSoLuong;
		this.ngaySinh = ngaySinh;
		this.diaChi = diaChi;
		this.gioiTinh = gioiTinh;
	}
	public NhanVien(String ma) {
		super();
		this.maNV = ma;
		// TODO Auto-generated constructor stub
	}
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getCaLamViec() {
		return caLamViec;
	}
	public void setCaLamViec(String caLamViec) {
		this.caLamViec = caLamViec;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getHo() {
		return ho;
	}
	public void setHo(String ho) {
		this.ho = ho;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public double getHeSoLuong() {
		return heSoLuong;
	}
	public void setHeSoLuong(double heSoLuong) {
		this.heSoLuong = heSoLuong;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public DiaChi getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(DiaChi diaChi) {
		this.diaChi = diaChi;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}
	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", caLamViec=" + caLamViec + ", ten=" + ten + ", ho=" + ho + ", cccd=" + cccd
				+ ", soDienThoai=" + soDienThoai + ", heSoLuong=" + heSoLuong
				+ ", ngaySinh=" + ngaySinh + ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + "]";
	}
	
	
	
	
	
}
