package entity;

import java.util.Objects;

public class ChiTietHoaDon {
	private String CTHD;
	private HoaDon maHD;
	private Thuoc maThuoc;
	private double donGia;
	private int soLuong;
	private double thanhTien;
	public ChiTietHoaDon(String CTHD, HoaDon maHD, Thuoc maThuoc, double donGia, int soLuong,double thanhTien) {
		super();
		this.CTHD = CTHD;
		this.maHD = maHD;
		this.maThuoc = maThuoc;
		this.donGia = donGia;
		this.soLuong = soLuong;
		this.thanhTien = thanhTien;
	}
	public ChiTietHoaDon(String ma) {
		super();
		this.CTHD = ma;
		// TODO Auto-generated constructor stub
	}
	
	public String getCTHD() {
		return CTHD;
	}
	public void setCTHD(String cTHD) {
		CTHD = cTHD;
	}
	public HoaDon getMaHD() {
		return maHD;
	}
	public void setMaHD(HoaDon maHD) {
		this.maHD = maHD;
	}
	public Thuoc getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(Thuoc maThuoc) {
		this.maThuoc = maThuoc;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	
	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	@Override
	public int hashCode() {
		return Objects.hash(CTHD);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Objects.equals(CTHD, other.CTHD);
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [CTHD=" + CTHD + ", maHD=" + maHD + ", maThuoc=" + maThuoc + ", donGia=" + donGia
				+ ", soLuong=" + soLuong + ", thanhTien=" + thanhTien + "]";
	}

	
	
	
	
}
