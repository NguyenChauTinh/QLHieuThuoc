package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;


public class KhachHang_DAO {
	public KhachHang_DAO() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<KhachHang> getAllKhachHang(){
		ArrayList<KhachHang> dsKH= new ArrayList<KhachHang>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			String sql = "select * from KhachHang";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String maKH = rs.getString(1);
				String hoKH = rs.getString(2);
				String tenKH = rs.getString(3);
				String gioiTinh = rs.getString(4);
				String soDienThoai = rs.getString(5);
				
				KhachHang kh = new KhachHang(maKH, hoKH, tenKH, gioiTinh, soDienThoai);
				dsKH.add(kh);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsKH;
	}
	public boolean createKhachHang(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "KhachHang(maKH,hoKH,tenKH,gioiTinh,soDienThoai) "
					+ "VALUES(?,?,?,?,?)");
			state.setString(1, kh.getMaKH());
			state.setString(2, kh.getHoKH());
			state.setString(3, kh.getTenKH());
			state.setString(4, kh.getGioiTinh());
			state.setString(5, kh.getSoDienThoai());
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
	public boolean updateKhachHang(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update KhachHang set hoKH=?,tenKH=?,gioiTinh=?,soDienThoai=? where maKH=?");
			
			state.setString(1, kh.getHoKH());
			state.setString(2, kh.getTenKH());
			state.setString(3, kh.getGioiTinh());
			state.setString(4, kh.getSoDienThoai());
			state.setString(5, kh.getMaKH());
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
	public void DeleteKhachHang(String maKH) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		 Connection con = ConnectDB.getConnection();
		
		 String sql ="DELETE FROM KhachHang WHERE maKH=?";
		 try {
			 pst = con.prepareStatement(sql);
			pst.setString(1, maKH);
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
