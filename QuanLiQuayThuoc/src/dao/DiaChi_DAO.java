package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DiaChi;
import entity.NhaCungCap;

public class DiaChi_DAO {
	public DiaChi_DAO() {
		// TODO Auto-generated constructor stub
	}
	public boolean createDiaChi(DiaChi dc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "DiaChi(maDiaChi,soNha,tenDuong,phuong,quan,thanhPho)"
					+ "VALUES(?,?,?,?,?,?)");
			state.setString(1, dc.getMaDiaChi());
			state.setString(2, dc.getSoNha());
			state.setString(3, dc.getTenDuong());
			state.setString(4, dc.getPhuong());
			state.setString(5, dc.getQuan());
			state.setString(6, dc.getThanhPho());
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
	public ArrayList<DiaChi> getAlltbDiaChi(){
		ArrayList<DiaChi> dsncc = new ArrayList<DiaChi>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from DiaChi";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maDC = rs.getString(1);
				String soNha = rs.getString(2);
				String tenDuong = rs.getString(3);
				String phuong = rs.getString(4);
				String quan = rs.getString(5);
				String thanhPho = rs.getString(6);
				
				DiaChi dc = new DiaChi(maDC, soNha, tenDuong, phuong, quan, thanhPho);
				dsncc.add(dc);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsncc;
	}
	public boolean updateDiaChi(DiaChi dc) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update DiaChi set soNha=?,tenDuong=?,phuong=?,quan=?,thanhPho=? where maDiaChi=?");
			
			state.setString(1, dc.getSoNha());
			state.setString(2, dc.getTenDuong());
			state.setString(3, dc.getPhuong());
			state.setString(4, dc.getQuan());
			state.setString(5, dc.getThanhPho());
			state.setString(6, dc.getMaDiaChi());
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
	public void DeleteDiaChi(String maDC) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		 Connection con = ConnectDB.getConnection();
		
		 String sql ="DELETE FROM DiaChi WHERE maDiaChi=?";
		 try {
			 pst = con.prepareStatement(sql);
			pst.setString(1, maDC);
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
