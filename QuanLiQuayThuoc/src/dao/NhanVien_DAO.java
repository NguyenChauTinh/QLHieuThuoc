package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DiaChi;
import entity.NhanVien;

public class NhanVien_DAO {
	public NhanVien_DAO() {
		// TODO Auto-generated constructor stub
	}
    public ArrayList<NhanVien> getAllNhanViens(){
		ArrayList<NhanVien> dsNV = new ArrayList<NhanVien>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from NhanVien";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maNV = rs.getString(1);
				DiaChi diaChi = new DiaChi(rs.getString("maDiaChi"));
				String caLamViec = rs.getString(3);
				String ten = rs.getString(4);
				String ho = rs.getString(5);
				Date ngaySinh = rs.getDate(6);
				String gioiTinh = rs.getString(7);
				String cccd = rs.getString(8);
				String soDienThoai = rs.getString(9);
				double heSoLuong = rs.getDouble(10);
				
				
				NhanVien nv = new NhanVien( maNV, caLamViec, ten, ho, cccd, soDienThoai, heSoLuong, ngaySinh, diaChi, gioiTinh);
				dsNV.add(nv);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsNV;
	}
	public boolean createNhanVien(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("insert into "
					+ "NhanVien(maNV,maDiaChi,caLamViec,ten,ho,ngaySinh,gioiTinh,cccd,soDienThoai,heSoLuong)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?)");
			state.setString(1, nv.getMaNV());
			state.setString(2, nv.getDiaChi().getMaDiaChi());
			state.setString(3, nv.getCaLamViec());
			state.setString(4, nv.getTen());
			state.setString(5, nv.getHo());
			java.sql.Date ns = new java.sql.Date(nv.getNgaySinh().getTime());
			state.setDate(6, ns);
			state.setString(7, nv.getGioiTinh());
			state.setString(8, nv.getCccd());
			state.setString(9, nv.getSoDienThoai());
			state.setDouble(10, nv.getHeSoLuong());
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
	public boolean updateNhanVien(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement state = null;
		int n = 0;
		try {
			state = con.prepareStatement("update NhanVien set maDiaChi=?,caLamViec=?,ten=?,ho=?,ngaySinh=?,gioiTinh=?,cccd=?,soDienThoai=?,heSoLuong=? where maNV=?");
			state.setString(1, nv.getDiaChi().getMaDiaChi());
			state.setString(2, nv.getCaLamViec());
			state.setString(3, nv.getTen());
			state.setString(4, nv.getHo());
			java.sql.Date ns = new java.sql.Date(nv.getNgaySinh().getTime());
			state.setDate(5, ns);
			state.setString(6, nv.getGioiTinh());
			state.setString(7, nv.getCccd());
			state.setString(8, nv.getSoDienThoai());
			state.setDouble(9, nv.getHeSoLuong());
			state.setString(10, nv.getMaNV());
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
	public void DeleteNhanVien(String maNV) {
		ConnectDB.getInstance();
		PreparedStatement pst = null;
		Connection con = ConnectDB.getConnection();
		
		String sql ="DELETE FROM NhanVien WHERE maNV=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, maNV);
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
