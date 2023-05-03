package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DonViThuoc;

public class DonViThuoc_DAO {
	public DonViThuoc_DAO() {
	// TODO Auto-generated constructor stub
	}
	public ArrayList<DonViThuoc> getAlltbDonViThuoc(){
		ArrayList<DonViThuoc> dsDV = new ArrayList<DonViThuoc>();
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		try {
			
			String sql = "select * from DonViThuoc";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				String maDV = rs.getString(1);
				String tenDV = rs.getString(2);
				
				DonViThuoc dv = new DonViThuoc(maDV, tenDV);
				dsDV.add(dv);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsDV;
	}
}
