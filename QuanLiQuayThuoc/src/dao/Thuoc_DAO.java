package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import connectDB.ConnectDB;
import entity.DonViThuoc;
import entity.NhaCungCap;
import entity.Thuoc;

public class Thuoc_DAO {
    public Thuoc_DAO() {
		// TODO Auto-generated constructor stub
	}
    public ArrayList<Thuoc> getAlltbThuoc(){
		ArrayList<Thuoc> dsT = new ArrayList<Thuoc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from Thuoc";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maThuoc = rs.getString(1);
				DonViThuoc maDV = new DonViThuoc(rs.getString("maDonVi"));
				NhaCungCap maNCC = new NhaCungCap(rs.getString("maNhaCungCap"));
				String ten = rs.getString(4);
				double donGiaBan = rs.getDouble(5);
				Date hsd = rs.getDate(6);
				int soluongton = rs.getInt(7);
				Date nsx = rs.getDate(8);
				double donGiaNhap = rs.getDouble(9);
				
				Thuoc t = new Thuoc(maThuoc, ten, maDV, donGiaNhap, hsd, maNCC, soluongton, nsx, donGiaBan);
				dsT.add(t);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsT;
	}
	public boolean createThuoc(Thuoc t) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "Thuoc(maThuoc,maDonvi,maNhaCungCap,tenThuoc,donGiaThuoc,hanSuDung,soLuongTon,ngaySX,giaNhap) "
					+ "VALUES(?,?,?,?,?,?,?,?,?)");
			state.setString(1, t.getMaThuoc());
			state.setString(2, t.getDvThuoc().getMaDonVi());
			state.setString(3, t.getNhaCC().getMaNCC());
			state.setString(4, t.getTenThuoc());
			state.setDouble(5, t.getDonGiaBan());
			java.sql.Date hsd = new java.sql.Date(t.getHanSuDung().getTime());
			state.setDate(6, hsd);
			state.setInt(7, t.getSoLuongTon());
			java.sql.Date nsx = new java.sql.Date(t.getNgaySX().getTime());
			state.setDate(8,  nsx);
			state.setDouble(9, t.getDonGiaNhap());
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
	public boolean updateThuoc(Thuoc t) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update Thuoc set maDonvi=?,maNhaCungCap=?,tenThuoc=?,donGiaThuoc=?,hanSuDung=?,soLuongTon=?,ngaySX=?,giaNhap=? where maThuoc=?");
			
			state.setString(1, t.getDvThuoc().getMaDonVi());
			state.setString(2, t.getNhaCC().getMaNCC());
			state.setString(3, t.getTenThuoc());
			state.setDouble(4, t.getDonGiaBan());
			java.sql.Date hsd = new java.sql.Date(t.getHanSuDung().getTime());
			state.setDate(5,  hsd);
			state.setInt(6, t.getSoLuongTon());
			java.sql.Date nsx = new java.sql.Date(t.getNgaySX().getTime());
			state.setDate(7,  nsx);
			state.setDouble(8, t.getDonGiaNhap());
			state.setString(9, t.getMaThuoc());
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
	public void DeleteThuoc(String maThuoc) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		 Connection con = ConnectDB.getConnection();
		
		 String sql ="DELETE FROM Thuoc WHERE maThuoc=?";
		 try {
			 pst = con.prepareStatement(sql);
			pst.setString(1, maThuoc);
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
