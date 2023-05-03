package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.DiaChi;
import entity.NhaCungCap;


public class NhaCungCap_DAO {
	public NhaCungCap_DAO() {
	}
	public ArrayList<NhaCungCap> getAlltbNhaCungCap(){
		ArrayList<NhaCungCap> dsncc = new ArrayList<NhaCungCap>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from NhaCungCap";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maNCC = rs.getString(1);
				DiaChi maDC = new DiaChi(rs.getString("maDiaChi"));
				String ten = rs.getString(3);
				String sdt = rs.getString(4);
				
				NhaCungCap ncc = new NhaCungCap(maNCC, ten, maDC, sdt);
				dsncc.add(ncc);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsncc;
	}
	public boolean createNhaCungCap(NhaCungCap ncc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "NhaCungCap(maNhaCungCap,maDiaChi,tenNhaCungCap,soDienThoai) "
					+ "VALUES(?,?,?,?)");
			state.setString(1, ncc.getMaNCC());
			state.setString(2, ncc.getDiaChi().getMaDiaChi());
			state.setString(3, ncc.getTenNCC());
			state.setString(4, ncc.getSoDienThoai());
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
	public boolean updateNCC(NhaCungCap ncc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update NhaCungCap set maDiaChi=?,tenNhaCungCap=?,soDienThoai=? where maNhaCungCap=?");
			
			state.setString(1, ncc.getDiaChi().getMaDiaChi());
			state.setString(2, ncc.getTenNCC());
			state.setString(3, ncc.getSoDienThoai());
			state.setString(4, ncc.getMaNCC());
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
	public void DeleteNCC(String maNCC) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		 Connection con = ConnectDB.getConnection();
		
		 String sql ="DELETE FROM NhaCungCap WHERE maNhaCungCap=?";
		 try {
			 pst = con.prepareStatement(sql);
			pst.setString(1, maNCC);
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
