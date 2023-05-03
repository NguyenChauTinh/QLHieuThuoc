package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class HoaDon_DAO {
	public HoaDon_DAO() {
		// TODO Auto-generated constructor stub
	}
    public ArrayList<HoaDon> getAllHoaDons(){
		ArrayList<HoaDon> dsHD = new ArrayList<HoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from HoaDon";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maHD = rs.getString(1);
				KhachHang khachHang = new KhachHang(rs.getString("maKH"));
				NhanVien nhanVien = new NhanVien(rs.getString("maNV"));
				Date ngayBan = rs.getDate(4);
				double tongTien = rs.getDouble(5);
				double giamGia = rs.getDouble(6);
				
				HoaDon hd = new HoaDon(maHD, ngayBan, tongTien, nhanVien, khachHang, giamGia);
				dsHD.add(hd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsHD;
	}
	public boolean createHoaDon(HoaDon hd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "HoaDon(maHD,maKH,maNV,ngayBan,tongTien,giamGia)"
					+ "VALUES(?,?,?,?,?,?)");
			state.setString(1, hd.getMaHD());
			state.setString(2, hd.getKhachHang().getMaKH());
			state.setString(3, hd.getNhanVien().getMaNV());
			java.sql.Date ngay = new java.sql.Date(hd.getNgayBan().getTime());
			state.setDate(4, ngay);
			state.setDouble(5, hd.getTongTien());
			state.setDouble(6, hd.getGiamGia());
			n = state.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				state.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n>0;
	}
	public boolean updateHoaDon(HoaDon hd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update HoaDon set maKH=?,maNV=?,ngayBan=?,tongTien=?,giamGia=? where maHD=?");
			state.setString(1, hd.getKhachHang().getMaKH());
			state.setString(2, hd.getNhanVien().getMaNV());
			java.sql.Date ngay = new java.sql.Date(hd.getNgayBan().getTime());
			state.setDate(3, ngay);
			state.setDouble(4, hd.getTongTien());
			state.setDouble(5, hd.getGiamGia());	
			state.setString(6, hd.getMaHD());
			n = state.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				state.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return n>0;
	}
	public void DeleteHoaDon(String maHD) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		Connection con = ConnectDB.getConnection();
		
		String sql ="DELETE FROM HoaDon WHERE maHD=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, maHD);
			pst.executeUpdate() ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				pst.close();
			} catch (SQLException e2) {
				// TODO: handle exceptione2 
				e2.printStackTrace();
			}
		}
	}

}
