package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
	 public TaiKhoan_DAO() {
			// TODO Auto-generated constructor stub
		}
	    public ArrayList<TaiKhoan> getAllTaiKhoans(){
			ArrayList<TaiKhoan> dsTK = new ArrayList<TaiKhoan>();
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			try {
				
				String sql = "select * from TaiKhoan";
				Statement state = con.createStatement();
				ResultSet rs = state.executeQuery(sql);
				while(rs.next()) {
					String tenTK = rs.getString(1);
					String matKhau = rs.getString(2);
					
					TaiKhoan tk = new TaiKhoan( tenTK, matKhau);
					dsTK.add(tk);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return dsTK;
		}
		public boolean createTaiKhoan(TaiKhoan tk) {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement state = null;
			int n = 0;
			try {
				state = con.prepareStatement("insert into "
						+ "Thuoc(tenTK,matKhau) "
						+ "VALUES(?,?)");
				state.setString(1, tk.getTenTK());
				state.setString(2, tk.getMatKhau());
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
		public boolean updateTaiKhoan(TaiKhoan tk) {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			PreparedStatement state = null;
			int n = 0;
			try {
				state = con.prepareStatement("update TaiKhoan set matKhau=? where tenTK=?");
				
				state.setString(1, tk.getTenTK());
				state.setString(2, tk.getMatKhau());
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
		public void DeleteTaiKhoan(String tenTK) {
			ConnectDB.getInstance();
			PreparedStatement pst = null;
			 Connection con = ConnectDB.getConnection();
			
			 String sql ="DELETE FROM TaiKhoan WHERE tenTK=?";
			 try {
				 pst = con.prepareStatement(sql);
				pst.setString(1, tenTK);
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
