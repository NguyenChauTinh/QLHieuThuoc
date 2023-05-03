package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.Thuoc;
import entity.ChiTietHoaDon;

public class ChiTietHoaDon_DAO {
	public ChiTietHoaDon_DAO() {
		// TODO Auto-generated constructor stub
	}
    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDons(){
		ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<ChiTietHoaDon>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from ChiTietHoaDon";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maCTHD = rs.getString(1);
				HoaDon maHD = new HoaDon(rs.getString("maHD"));
				Thuoc maThuoc = new Thuoc(rs.getString("maThuoc"));
				double donGia = rs.getDouble(4);
				int soLuong = rs.getInt(5);
				double thanhTien = rs.getDouble(6);
				
				ChiTietHoaDon cthd = new ChiTietHoaDon( maCTHD,maHD, maThuoc, donGia, soLuong, thanhTien);
				dsCTHD.add(cthd);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsCTHD;
	}
	public boolean createChiTietHoaDon(ChiTietHoaDon cthd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "ChiTietHoaDon(maCTHD,maHD,maThuoc,donGia,soLuong,thanhTien)"
					+ "VALUES(?,?,?,?,?,?)");
			state.setString(1, cthd.getCTHD());
			state.setString(2, cthd.getMaHD().getMaHD());
			state.setString(3, cthd.getMaThuoc().getMaThuoc());
			state.setDouble(4, cthd.getDonGia());
			state.setInt(5, cthd.getSoLuong());
			state.setDouble(6, cthd.getThanhTien());
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
	public boolean updateChiTietHoaDon(ChiTietHoaDon cthd) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update ChiTietHoaDon set maHD=?,maThuoc=?,donGia=?,soLuong=?,thanhTien=? where maCTHD=? ");
			state.setString(1, cthd.getMaHD().getMaHD());
			state.setString(2, cthd.getMaThuoc().getMaThuoc());
			state.setDouble(3, cthd.getDonGia());
			state.setInt(4, cthd.getSoLuong());
			state.setDouble(5, cthd.getThanhTien());
			state.setString(6, cthd.getCTHD());
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
	public void DeleteChiTietHoaDon(String maHD) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		Connection con = ConnectDB.getConnection();
		
		String sql ="DELETE FROM ChiTietHoaDon WHERE maCTHD=?";
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
