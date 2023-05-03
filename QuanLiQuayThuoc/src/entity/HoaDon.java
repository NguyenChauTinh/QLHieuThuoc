package entity;

import java.util.Date;
import java.util.Objects;

public class HoaDon {
	private String maHD;
	private Date ngayBan;
	private double tongTien;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	private double giamGia;
	public HoaDon(String maHD, Date ngayBan, double tongTien, NhanVien nhanVien, KhachHang khachHang,double giamGia) {
		super();
		this.maHD = maHD;
		this.ngayBan = ngayBan;
		this.tongTien = tongTien;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.giamGia = giamGia;
	}
	public HoaDon(String ma) {
		super();
		this.maHD = ma;
		// TODO Auto-generated constructor stub
	}
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public Date getNgayBan() {
		return ngayBan;
	}
	public void setNgayBan(Date ngayBan) {
		this.ngayBan = ngayBan;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	
	public double getGiamGia() {
		return giamGia;
	}
	public void setGiamGia(double giamGia) {
		this.giamGia = giamGia;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHD, other.maHD);
	}
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", ngayBan=" + ngayBan + ", tongTien=" + tongTien + ", nhanVien=" + nhanVien
				+ ", khachHang=" + khachHang + ", giamGia=" + giamGia + "]";
	}
	
	
	
	
}
