package entity;

import java.util.Date;
import java.util.Objects;

public class Thuoc {
    private String maThuoc;
    private String tenThuoc;
    private DonViThuoc dvThuoc;
    private double donGiaNhap;
    private Date hanSuDung;
    private NhaCungCap nhaCC;
    private int soLuongTon;
    private Date ngaySX;
    private double donGiaBan;
	public Thuoc(String maThuoc, String tenThuoc, DonViThuoc dvThuoc, double donGiaNhap, Date hanSuDung,
			NhaCungCap nhaCC, int soLuongTon, Date ngaySX, double donGiaBan) {
		super();
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.dvThuoc = dvThuoc;
		this.donGiaNhap = donGiaNhap;
		this.hanSuDung = hanSuDung;
		this.nhaCC = nhaCC;
		this.soLuongTon = soLuongTon;
		this.ngaySX = ngaySX;
		this.donGiaBan = donGiaBan;
	}
	public Thuoc(String ma) {
		super();
		this.maThuoc = ma;
		// TODO Auto-generated constructor stub
	}
	public String getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}
	public String getTenThuoc() {
		return tenThuoc;
	}
	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}
	public DonViThuoc getDvThuoc() {
		return dvThuoc;
	}
	public void setDvThuoc(DonViThuoc dvThuoc) {
		this.dvThuoc = dvThuoc;
	}
	public double getDonGiaNhap() {
		return donGiaNhap;
	}
	public void setDonGiaNhap(double donGiaNhap) {
		this.donGiaNhap = donGiaNhap;
	}
	public Date getHanSuDung() {
		return hanSuDung;
	}
	public void setHanSuDung(Date hanSuDung) {
		this.hanSuDung = hanSuDung;
	}
	public NhaCungCap getNhaCC() {
		return nhaCC;
	}
	public void setNhaCC(NhaCungCap nhaCC) {
		this.nhaCC = nhaCC;
	}
	public int getSoLuongTon() {
		return soLuongTon;
	}
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}
	public Date getNgaySX() {
		return ngaySX;
	}
	public void setNgaySX(Date ngaySX) {
		this.ngaySX = ngaySX;
	}
	public double getDonGiaBan() {
		return donGiaBan;
	}
	public void setDonGiaBan(double donGiaBan) {
		this.donGiaBan = donGiaBan;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maThuoc);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thuoc other = (Thuoc) obj;
		return Objects.equals(maThuoc, other.maThuoc);
	}
	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", dvThuoc=" + dvThuoc + ", donGiaNhap="
				+ donGiaNhap + ", hanSuDung=" + hanSuDung + ", nhaCC=" + nhaCC + ", soLuongTon=" + soLuongTon
				+ ", ngaySX=" + ngaySX + ", donGiaBan=" + donGiaBan + "]";
	}
	
	
    
    
    
}
