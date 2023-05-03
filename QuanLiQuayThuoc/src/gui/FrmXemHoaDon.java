package gui;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import connectDB.ConnectDB;
import dao.ChiTietHoaDon_DAO;
import dao.DiaChi_DAO;
import dao.DonViThuoc_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhaCungCap_DAO;
import dao.NhanVien_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.DiaChi;
import entity.DonViThuoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;

import java.time.LocalDateTime;

public class FrmXemHoaDon extends javax.swing.JFrame {
	private DonViThuoc_DAO dv_dao = new DonViThuoc_DAO();
	private Thuoc_DAO t_dao = new Thuoc_DAO();
	private NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
	private DiaChi_DAO dc_dao = new DiaChi_DAO();
	private FrmThuoc frm_thuoc = new FrmThuoc();
	private ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
	private HoaDon_DAO hd_dao = new HoaDon_DAO();
	private KhachHang_DAO kh_dao = new KhachHang_DAO();
	private NhanVien_DAO nv_dao = new NhanVien_DAO();
	
	Pattern regexHo = Pattern.compile("^[a-zA-Z\\s]+$");
	Pattern regexTen = Pattern.compile("^([A-Za-z\\'\\.\\-\\d\\s]+)");
	Pattern regexSdt = Pattern.compile("^[\\d]{10}$");
	Pattern regexSL = Pattern.compile("[0-9]+");
	Pattern regexTenThuoc = Pattern.compile("^([A-z\\'\\.\\-\\d]*(\\s))+[A-z\\'\\.\\-\\d]*$", Pattern.CASE_INSENSITIVE);
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form FrmLapHoaDon
     */
    public FrmXemHoaDon() {
    	try {
    		ConnectDB.getInstance().connect();
    	} catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        initComponents();
        ImageIcon imageIcon = new ImageIcon("icon//heart.png");
        setIconImage(imageIcon.getImage());
        setTitle("Quản lí hiệu thuốc");
        setLocationRelativeTo(null);
        frmChiTiet.setLocationRelativeTo(null);
        updateDataXemHoaDon();
    }
    public boolean kiemTraThongTinNhapVao() {
    	Date dateNL = dateNgayLap.getDate();
    	Date newDate = new Date();
    	if(dateNL == null) {
    		JOptionPane.showMessageDialog(null, "Ngày lập hoá đơn không được rỗng!");
    		dateNgayLap.requestFocus();
    		return false;
    	}
    	if(dateNL.after(newDate)) {
    		JOptionPane.showMessageDialog(null, "Ngày lập phải trước ngày hiện tại!");
    		dateNgayLap.requestFocus();
    		return false;
    	}

    	if(txtHoKH.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Họ khách hàng không được rỗng!");
    		txtHoKH.requestFocus();
    		txtHoKH.selectAll();
    		return false;
    		
    	}
    	if(!regexHo.matcher(txtHoKH.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, họ khách hàng chỉ chứa ký tự là chữ");
    		txtHoKH.requestFocus();
    		txtHoKH.selectAll();
    		return false;
    	}
    	
    	if(txtTenKH.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên khách hàng không được rỗng!");
    		txtTenKH.requestFocus();
    		txtTenKH.selectAll();
    		return false;
    		
    	}
    	if(!regexTen.matcher(txtTenKH.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên khách hàng chứa ký tự là chữ");
    		txtTenKH.requestFocus();
    		txtTenKH.selectAll();
    		return false;
    	}
    	if(txtSDTKH.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Số điện thoại khách hàng không được rỗng!");
    		return false;
    		
    	}
    	if(!regexSdt.matcher(txtSDTKH.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, số điện thoại khách hàng chỉ chứa ký tự là số từ 0-9 và đủ 10 chữ số");
    		txtSDTKH.requestFocus();
    		txtSDTKH.selectAll();
    		return false;
    	}
    	return true;
    }
    
    public boolean ktThuoc() {
    	if(txtSoLuongMua.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(null, "Số lượng nhập không được rỗng");
    		txtSoLuongMua.requestFocus();
    		txtSoLuongMua.selectAll();
    		return false;
    	}
    	if(!regexSL.matcher(txtSoLuongMua.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, Số lượng nhập chỉ bao gồm chữ số và lớn hơn 0");
    		txtSoLuongMua.requestFocus();
    		txtSoLuongMua.selectAll();
    		return false;
    	}
    	if(Integer.parseInt(txtSoLuongMua.getText().trim()) <= 0) {
    		JOptionPane.showMessageDialog(null, "Số lượng nhập không được bé hơn hoặc bằng 0");
    		txtSoLuongMua.requestFocus();
    		txtSoLuongMua.selectAll();
    		return false;
    	}
    	return true;
    	
    }
    
    public HoaDon createHDForm() {
    	int row = tableXemHoaDon.getSelectedRow();
    	String maHD = txtMaHD.getText().trim();
    	Date ngayLap = dateNgayLap.getDate();
    	NhanVien maNV = new NhanVien(cboMaNV.getSelectedItem().toString());
		KhachHang kh = new KhachHang(tableXemHoaDon.getValueAt(row, 6).toString());
		double giamGia = Double.parseDouble(cboGiamGiaHD.getSelectedItem().toString());
		
		double tongTienThanhToan = 0.0;
		
		HoaDon hd = new HoaDon(maHD, ngayLap, tongTienThanhToan, maNV, kh, giamGia);
		return hd;
	}
    
    public KhachHang createKhachHangForm() {
    	String maKH = txtMaKH.getText().trim();
    	String hoKH = txtHoKH.getText().trim();
    	String tenKH = txtTenKH.getText().trim();
    	String sdt = txtSDTKH.getText().trim();
    	String gioiTinhKH = "Nữ";
    	if(rdoKHNu.isSelected()) {
    		gioiTinhKH = "Nữ";
    	}
    	if(rdoKHNam.isSelected()) {
    		gioiTinhKH = "Nam";
    	}
    	KhachHang kh = new KhachHang(maKH, hoKH, tenKH, gioiTinhKH, sdt);
    	
    	return kh;
    	
    }
  
	public void deleteTable() {
		DefaultTableModel dm = (DefaultTableModel) tableXemHoaDon.getModel();
		dm.getDataVector().removeAllElements();
	}
	
	public void deleteTableHoaDon() {
		DefaultTableModel dm = (DefaultTableModel) tableHoaDon.getModel();
		dm.getDataVector().removeAllElements();
	}
	
	public HoaDon getThongTinHD(String ma) {
		ArrayList<HoaDon> listHD = hd_dao.getAllHoaDons();
		for(HoaDon hd : listHD) {
			if(hd.getMaHD().equalsIgnoreCase(ma))
				return hd;
		}
		return null;
	}
	public ChiTietHoaDon getThongTinCTHD(String ma) {
		ArrayList<ChiTietHoaDon> listCTHD = cthd_dao.getAllChiTietHoaDons();
		for(ChiTietHoaDon hd : listCTHD) {
			if(hd.getCTHD().equalsIgnoreCase(ma))
				return hd;
		}
		return null;
	}
	public KhachHang getThongTinKH(String ma) {
		ArrayList<KhachHang> listKH = kh_dao.getAllKhachHang();
		for(KhachHang kh : listKH) {
			if(kh.getMaKH().equalsIgnoreCase(ma))
				return kh;
		}
		return null;
	}
	
	public NhanVien getThongTinNV(String ma) {
		ArrayList<NhanVien> listNV = nv_dao.getAllNhanViens();
		for(NhanVien nv : listNV) {
			if(nv.getMaNV().equalsIgnoreCase(ma))
				return nv;
		}
		return null;
	}
	public Thuoc getThongTinThuoc(String ma) {
		ArrayList<Thuoc> listT = t_dao.getAlltbThuoc();
		for(Thuoc t : listT) {
			if(t.getMaThuoc().equalsIgnoreCase(ma))
				return t;
		}
		return null;
	}

	public int timKiemTheoMaThuoc(String ma) {
		ArrayList<Thuoc> listThuoc = t_dao.getAlltbThuoc();
		for(Thuoc t : listThuoc) {
			if(t.getMaThuoc().equalsIgnoreCase(ma))
				return listThuoc.indexOf(t);
		}
		return -1;
	}
	
	public int timKiemTheoTenThuoc(String ten) {
		ArrayList<Thuoc> listThuoc = t_dao.getAlltbThuoc();
		for(Thuoc t : listThuoc) {
			if(t.getTenThuoc().equalsIgnoreCase(ten))
				return listThuoc.indexOf(t);
		}
		return -1;
	}
	public int timKiemTheoMaNV(String ma) {
		ArrayList<NhanVien> listNV = nv_dao.getAllNhanViens();
		for(NhanVien nv : listNV) {
			if(nv.getMaNV().equalsIgnoreCase(ma))
				return listNV.indexOf(nv);
		}
		return -1;
	}
	public int timKiemTheoMaKH(String ma) {
		ArrayList<KhachHang> listKH = kh_dao.getAllKhachHang();
		for(KhachHang kh : listKH) {
			if(kh.getMaKH().equalsIgnoreCase(ma))
				return listKH.indexOf(kh);
		}
		return -1;
	}
	public int timKiemTheoMaHD(String ma) {
		ArrayList<HoaDon> listHD = hd_dao.getAllHoaDons();
		for(HoaDon hd : listHD) {
			if(hd.getMaHD().equalsIgnoreCase(ma))
				return listHD.indexOf(hd);
		}
		return -1;
	}
	public void updateDataXemHoaDon() {
		deleteTable();
		ArrayList<HoaDon> listHD = hd_dao.getAllHoaDons();
		DefaultTableModel tableModal = (DefaultTableModel) tableXemHoaDon.getModel();
		for(HoaDon hd : listHD) {
			KhachHang kh = getThongTinKH(hd.getKhachHang().getMaKH());
			tableModal.addRow(new Object[] {hd.getMaHD(),hd.getNhanVien().getMaNV(),df.format(hd.getNgayBan()),
					hd.getTongTien()/((100-hd.getGiamGia())/100),hd.getGiamGia(),hd.getTongTien(),
					kh.getMaKH(),kh.getHoKH(),kh.getTenKH(),kh.getGioiTinh(),kh.getSoDienThoai()});	
		}
		
	}
    public String createMaCTHD() {
    	ArrayList<ChiTietHoaDon> listCTHD = cthd_dao.getAllChiTietHoaDons();
    	Integer count = 1;
    	Integer countNew = 0;
    	for(ChiTietHoaDon cthd : listCTHD) {
    		countNew = Integer.parseInt(cthd.getCTHD().substring(4));
    		if(countNew > count) {
    			break;
    		}
    		else if(countNew >= count) {
    			count = (++count);
    		}
    	}
    	String res5 = count.toString().length() == 1 ? ("CTHD0000" + count)
    			: count.toString().length() == 2 ? ("CTHD000" + count) 
    					: count.toString().length() == 3 ? ("CTHD00" + count)
    							: count.toString().length() == 4 ? ("CTHD0" + count)
    									: ("CTHD" + count);
    	return res5;

    }
	public void updateDataHoaDon() {
		deleteTableHoaDon();
		ArrayList<ChiTietHoaDon> listCthd = cthd_dao.getAllChiTietHoaDons();
		DefaultTableModel tableModal = (DefaultTableModel) tableHoaDon.getModel();
		for(ChiTietHoaDon cthd : listCthd) {
			if(cthd.getMaHD().getMaHD().equals(txtMaHD.getText())) {
				Thuoc t = getThongTinThuoc(cthd.getMaThuoc().getMaThuoc());
				tableModal.addRow(new Object[] {cthd.getCTHD(),cthd.getMaThuoc().getMaThuoc(),t.getTenThuoc()
	    				,cthd.getSoLuong(),cthd.getDonGia(),cthd.getThanhTien()});
			}
		
		}
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        frmChiTiet = new javax.swing.JFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
        cboMaThuoc = new javax.swing.JComboBox<>();
        txtSoLuongMua = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        btnThemCTHD = new javax.swing.JButton();
        btnSuaCTHD = new javax.swing.JButton();
        btnXoaCTHD = new javax.swing.JButton();
        btnLamMoiCTHD = new javax.swing.JButton();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dateNgayLap = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSuaHD = new javax.swing.JButton();
        btnXoaHD = new javax.swing.JButton();
        btnLamMoiHD = new javax.swing.JButton();
        btnXemChiTiet = new javax.swing.JButton();
        MaKH = new javax.swing.JLabel();
        HoKH = new javax.swing.JLabel();
        TenKH = new javax.swing.JLabel();
        GioiTinhKH = new javax.swing.JLabel();
        rdoKHNu = new javax.swing.JRadioButton();
        rdoKHNam = new javax.swing.JRadioButton();
        SDTKH = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        txtHoKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtMaHD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTongTienThuoc = new javax.swing.JTextField();
        cboGiamGiaHD = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTongTienThanhToanHD = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableXemHoaDon = new javax.swing.JTable();
        cboMaNV = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        btnThoatHD = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtTimHD = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        rdoMaHD = new javax.swing.JRadioButton();
        rdoMaNV = new javax.swing.JRadioButton();
        rdoMaKH = new javax.swing.JRadioButton();
        btnTimHD = new javax.swing.JButton();
        btnLapHoaDon = new javax.swing.JButton();
        btnXemHoaDon = new javax.swing.JButton();
        btnXemThuoc = new javax.swing.JButton();
        btnXemNhanVien = new javax.swing.JButton();
        btnXemKhachHang = new javax.swing.JButton();
        btnXemThongKe = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuExit = new javax.swing.JMenu();

        frmChiTiet.setTitle("Chi Tiết Hóa Đơn");
        frmChiTiet.setSize(new java.awt.Dimension(632, 420));

        jScrollPane3.setColumnHeaderView(null);
        jScrollPane3.setRowHeaderView(null);
        jScrollPane3.setViewportView(null);

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã CTHD", "Mã thuốc", "Tên thuốc", "Số lượng mua", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableHoaDon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableHoaDon.setShowHorizontalLines(true);
        tableHoaDon.setShowVerticalLines(true);
        tableHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableHoaDon);
        if (tableHoaDon.getColumnModel().getColumnCount() > 0) {
            tableHoaDon.getColumnModel().getColumn(0).setMinWidth(150);
            tableHoaDon.getColumnModel().getColumn(2).setMinWidth(100);
        }

        cboMaThuoc.setEditable(true);
        cboMaThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        jLabel9.setText("Mã thuốc:");

        jLabel10.setText("Số lượng mua:");

        jLabel12.setText("Đơn giá:");

        jLabel13.setText("Thành tiền:");

        txtDonGia.setEditable(false);

        txtThanhTien.setEditable(false);

        btnThemCTHD.setBackground(new java.awt.Color(0, 153, 255));
        btnThemCTHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemCTHD.setText("Thêm");
        btnThemCTHD.setFocusable(false);
        btnThemCTHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThemCTHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTHDActionPerformed(evt);
            }
        });

        btnSuaCTHD.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaCTHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSuaCTHD.setText("Sửa");
        btnSuaCTHD.setFocusable(false);
        btnSuaCTHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSuaCTHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSuaCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaCTHDActionPerformed(evt);
            }
        });

        btnXoaCTHD.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaCTHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaCTHD.setText("Xóa");
        btnXoaCTHD.setFocusable(false);
        btnXoaCTHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoaCTHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoaCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTHDActionPerformed(evt);
            }
        });

        btnLamMoiCTHD.setBackground(new java.awt.Color(0, 153, 255));
        btnLamMoiCTHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoiCTHD.setText("Làm mới");
        btnLamMoiCTHD.setFocusable(false);
        btnLamMoiCTHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoiCTHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLamMoiCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiCTHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmChiTietLayout = new javax.swing.GroupLayout(frmChiTiet.getContentPane());
        frmChiTiet.getContentPane().setLayout(frmChiTietLayout);
        frmChiTietLayout.setHorizontalGroup(
            frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmChiTietLayout.createSequentialGroup()
                .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                    .addGroup(frmChiTietLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(frmChiTietLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(frmChiTietLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmChiTietLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboMaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(frmChiTietLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSoLuongMua, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSuaCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoaCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(btnLamMoiCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(btnThemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        frmChiTietLayout.setVerticalGroup(
            frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmChiTietLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboMaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThemCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmChiTietLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtSoLuongMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(frmChiTietLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnSuaCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addComponent(btnXoaCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frmChiTietLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(frmChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(23, 23, 23))
                    .addGroup(frmChiTietLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoiCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ArrayList<Thuoc> dsT = t_dao.getAlltbThuoc();
        for(Thuoc t : dsT) {
            cboMaThuoc.addItem(t.getMaThuoc());
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("XEM HÓA ĐƠN");

        jLabel3.setText("Mã hóa đơn:");

        jLabel4.setText("Mã nhân viên:");

        dateNgayLap.setDateFormatString("dd-MM-yyyy");

        jLabel6.setText("Ngày lập:");

        btnSuaHD.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSuaHD.setText("Sửa hóa đơn");
        btnSuaHD.setFocusable(false);
        btnSuaHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSuaHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSuaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHDActionPerformed(evt);
            }
        });

        btnXoaHD.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaHD.setText("Xóa hóa đơn");
        btnXoaHD.setFocusable(false);
        btnXoaHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoaHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHDActionPerformed(evt);
            }
        });

        btnLamMoiHD.setBackground(new java.awt.Color(0, 153, 255));
        btnLamMoiHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoiHD.setText("Làm mới");
        btnLamMoiHD.setFocusable(false);
        btnLamMoiHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoiHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLamMoiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiHDActionPerformed(evt);
            }
        });

        btnXemChiTiet.setBackground(new java.awt.Color(0, 153, 255));
        btnXemChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.setFocusable(false);
        btnXemChiTiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemChiTiet.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemChiTietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXemChiTiet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSuaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(btnLamMoiHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(49, 49, 49))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSuaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoiHD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(btnXemChiTiet))
        );

        MaKH.setText("Mã khách hàng:");

        HoKH.setText("Họ khách hàng:");

        TenKH.setText("Tên khách hàng:");

        GioiTinhKH.setText("Giới tính:");

        buttonGroup3.add(rdoKHNu);
        rdoKHNu.setText("Nữ");

        buttonGroup3.add(rdoKHNam);
        rdoKHNam.setText("Nam");

        SDTKH.setText("Số điện thoại:");

        txtMaKH.setEditable(false);

        txtMaHD.setEditable(false);

        jLabel5.setText("Tổng tiền thuốc:");

        txtTongTienThuoc.setEditable(false);

        cboGiamGiaHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"0.0","5.0","10.0","15.0","20.0","25.0","30.0","35.0","40.0","45.0","50.0","55.0","60.0","65.0","70.0","75.0","80.0","85.0","90.0","95.0","100.0"}));
        cboGiamGiaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiamGiaHDActionPerformed(evt);
            }
        });

        jLabel11.setText("Giảm giá:");

        jLabel7.setText("Tổng tiền cần thanh toán:");

        txtTongTienThanhToanHD.setEditable(false);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setColumnHeaderView(null);
        jScrollPane2.setRowHeaderView(null);
        jScrollPane2.setViewportView(null);

        tableXemHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã nhân viên", "Ngày lập", "Tổng tiền", "Giảm giá", "Tổng tiền thanh toán", "Mã khách hàng", "Họ khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableXemHoaDon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableXemHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableXemHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableXemHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableXemHoaDon.setShowGrid(true);
        tableXemHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableXemHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableXemHoaDon);
        if (tableXemHoaDon.getColumnModel().getColumnCount() > 0) {
            tableXemHoaDon.getColumnModel().getColumn(0).setMinWidth(150);
            tableXemHoaDon.getColumnModel().getColumn(1).setMinWidth(150);
            tableXemHoaDon.getColumnModel().getColumn(2).setMinWidth(100);
            tableXemHoaDon.getColumnModel().getColumn(3).setMinWidth(100);
            tableXemHoaDon.getColumnModel().getColumn(4).setMinWidth(100);
            tableXemHoaDon.getColumnModel().getColumn(5).setMinWidth(150);
            tableXemHoaDon.getColumnModel().getColumn(6).setMinWidth(150);
            tableXemHoaDon.getColumnModel().getColumn(7).setMinWidth(100);
            tableXemHoaDon.getColumnModel().getColumn(8).setMinWidth(100);
            tableXemHoaDon.getColumnModel().getColumn(9).setMinWidth(100);
            tableXemHoaDon.getColumnModel().getColumn(10).setMinWidth(100);
        }
        tableXemHoaDon.getAccessibleContext().setAccessibleName("");
        tableXemHoaDon.getAccessibleContext().setAccessibleParent(jScrollPane2);

        cboMaNV.setEditable(true);
        cboMaNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cboMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaHD)
                            .addComponent(cboMaNV, 0, 255, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTongTienThanhToanHD))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11)
                            .addComponent(jLabel6))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateNgayLap, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(cboGiamGiaHD, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongTienThuoc))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TenKH, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(HoKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(GioiTinhKH)
                            .addComponent(MaKH)
                            .addComponent(SDTKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(rdoKHNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoKHNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addComponent(txtMaKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(txtHoKH, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSDTKH, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(MaKH)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(HoKH)
                            .addComponent(txtHoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TenKH)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(GioiTinhKH)
                                    .addComponent(rdoKHNu)
                                    .addComponent(rdoKHNam))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SDTKH)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txtTongTienThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboGiamGiaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTongTienThanhToanHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        ArrayList<NhanVien> dsNV = nv_dao.getAllNhanViens();
        for(NhanVien nv : dsNV) {
            cboMaNV.addItem(nv.getMaNV());
        }

        btnThoatHD.setBackground(new java.awt.Color(0, 153, 255));
        btnThoatHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThoatHD.setText("Thoát");
        btnThoatHD.setFocusable(false);
        btnThoatHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThoatHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThoatHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatHDActionPerformed(evt);
            }
        });

        jLabel21.setText("Nhập thông tin tìm kiếm:");

        jLabel22.setText("Tìm theo:");

        buttonGroup4.add(rdoMaHD);
        rdoMaHD.setText("Mã hóa đơn");

        buttonGroup4.add(rdoMaNV);
        rdoMaNV.setText("Mã nhân viên");

        buttonGroup4.add(rdoMaKH);
        rdoMaKH.setText("Mã khách hàng");

        btnTimHD.setBackground(new java.awt.Color(0, 153, 255));
        btnTimHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimHD.setText("Tìm");
        btnTimHD.setFocusable(false);
        btnTimHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(rdoMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(rdoMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(rdoMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThoatHD, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnThoatHD))
                    .addComponent(btnTimHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(rdoMaHD)
                        .addComponent(rdoMaKH))
                    .addComponent(rdoMaNV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(402, 402, 402)
                .addComponent(jLabel2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLapHoaDon.setBackground(new java.awt.Color(0, 153, 255));
        btnLapHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLapHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMenu/bill.png"))); // NOI18N
        btnLapHoaDon.setText("Lập Hóa Đơn");
        btnLapHoaDon.setFocusable(false);
        btnLapHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLapHoaDon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLapHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapHoaDonActionPerformed(evt);
            }
        });

        btnXemHoaDon.setBackground(new java.awt.Color(0, 153, 255));
        btnXemHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXemHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMenu/see_bill.png"))); // NOI18N
        btnXemHoaDon.setText("Xem Hóa Đơn");
        btnXemHoaDon.setFocusable(false);
        btnXemHoaDon.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemHoaDon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemHoaDonActionPerformed(evt);
            }
        });

        btnXemThuoc.setBackground(new java.awt.Color(0, 153, 255));
        btnXemThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXemThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMenu/medicine.png"))); // NOI18N
        btnXemThuoc.setText("Thuốc");
        btnXemThuoc.setFocusable(false);
        btnXemThuoc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemThuoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXemThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemThuocActionPerformed(evt);
            }
        });

        btnXemNhanVien.setBackground(new java.awt.Color(0, 153, 255));
        btnXemNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXemNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMenu/employee.png"))); // NOI18N
        btnXemNhanVien.setText("Nhân Viên");
        btnXemNhanVien.setFocusable(false);
        btnXemNhanVien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemNhanVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemNhanVienActionPerformed(evt);
            }
        });

        btnXemKhachHang.setBackground(new java.awt.Color(0, 153, 255));
        btnXemKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMenu/group.png"))); // NOI18N
        btnXemKhachHang.setText("Khách Hàng");
        btnXemKhachHang.setFocusable(false);
        btnXemKhachHang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemKhachHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemKhachHangActionPerformed(evt);
            }
        });

        btnXemThongKe.setBackground(new java.awt.Color(0, 153, 255));
        btnXemThongKe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXemThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconMenu/bar-chart.png"))); // NOI18N
        btnXemThongKe.setText("Thống Kê");
        btnXemThongKe.setFocusable(false);
        btnXemThongKe.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXemThongKe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXemThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemThongKeActionPerformed(evt);
            }
        });

        menuExit.setText("Exit");
        menuExit.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                menuExitMenuSelected(evt);
            }
        });
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuExit);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLapHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXemThuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLapHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatHDActionPerformed
        // TODO add your handling code here:
        int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát không?","Chú ý",JOptionPane.YES_NO_OPTION);
		if(comfrim == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }//GEN-LAST:event_btnThoatHDActionPerformed

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
    	int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát không?","Chú ý",JOptionPane.YES_NO_OPTION);
		if(comfrim == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuExitMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_menuExitMenuSelected
        // TODO add your handling code here:
    	int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát không?","Chú ý",JOptionPane.YES_NO_OPTION);
		if(comfrim == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }//GEN-LAST:event_menuExitMenuSelected

    private void btnLamMoiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiHDActionPerformed
    	txtMaHD.setText("");
    	dateNgayLap.setDate(new Date());
    	txtTongTienThuoc.setText("");
    	txtTongTienThanhToanHD.setText("");
    	txtMaKH.setText("");
    	txtHoKH.setText("");
    	txtTenKH.setText("");
    	txtSDTKH.setText("");
    }//GEN-LAST:event_btnLamMoiHDActionPerformed

    private void btnXoaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHDActionPerformed
    	int r = tableXemHoaDon.getSelectedRow();
    	if(r!=-1) {
    		try {
    			int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa không?","Chú ý",JOptionPane.YES_NO_OPTION);
    			if(comfrim == JOptionPane.YES_OPTION) {
    				DefaultTableModel modal = (DefaultTableModel) tableXemHoaDon.getModel();
    				modal.removeRow(r); // xóa trong table model
    				String maHD = tableXemHoaDon.getValueAt(r, 0).toString();
    				ArrayList<ChiTietHoaDon> listCT = cthd_dao.getAllChiTietHoaDons();
    				for(ChiTietHoaDon cthd : listCT) {
    					if(cthd.getMaHD().getMaHD().equals(maHD)) {
    						cthd_dao.DeleteChiTietHoaDon(cthd.getCTHD());
    					}
    				}
    				hd_dao.DeleteHoaDon(maHD);
    				updateDataXemHoaDon();
    				JOptionPane.showMessageDialog(null, "Xóa thành công");
    			} else {
    				JOptionPane.showMessageDialog(null, "Bạn đã chọn không xóa");
    			}
    		} catch (Exception e2) {
    			JOptionPane.showMessageDialog(null, "Xóa thất bại");
    			e2.printStackTrace();
    		}
    		
    	} else {
    		JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần xóa");
    	}
    }//GEN-LAST:event_btnXoaHDActionPerformed

    private void tableXemHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableXemHoaDonMouseClicked
    	int row = tableXemHoaDon.getSelectedRow();
    	txtMaHD.setText(tableXemHoaDon.getValueAt(row, 0).toString());
    	cboMaNV.setSelectedItem(tableXemHoaDon.getValueAt(row, 1).toString());
    	try {
    		Date ngay = new SimpleDateFormat("dd-MM-yyyy").parse(tableXemHoaDon.getValueAt(row, 2).toString());
    		dateNgayLap.setDate(ngay);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	txtTongTienThuoc.setText(tableXemHoaDon.getValueAt(row, 3).toString());
    	cboGiamGiaHD.setSelectedItem(tableXemHoaDon.getValueAt(row, 4).toString());
    	txtTongTienThanhToanHD.setText(tableXemHoaDon.getValueAt(row, 5).toString());
    	txtMaKH.setText(tableXemHoaDon.getValueAt(row, 6).toString());
        txtHoKH.setText(tableXemHoaDon.getValueAt(row, 7).toString());
        txtTenKH.setText(tableXemHoaDon.getValueAt(row, 8).toString());
        if(tableXemHoaDon.getValueAt(row, 9).toString().equalsIgnoreCase("Nữ"))
        	rdoKHNu.setSelected(true);
        else
        	rdoKHNam.setSelected(true);
		txtSDTKH.setText(tableXemHoaDon.getValueAt(row, 10).toString());
		
		tableXemHoaDon.setRowSelectionInterval(row, row);
//		updateDataHoaDon();
//		frmChiTiet.setVisible(true);
    }//GEN-LAST:event_tableXemHoaDonMouseClicked

    private void btnTimHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimHDActionPerformed
    	boolean luaChonMaHD = rdoMaHD.isSelected() ? true : false;
    	boolean luaChonMaNV = rdoMaNV.isSelected() ? true : false;
    	boolean luaChonMaKH = rdoMaKH.isSelected() ? true : false;
    	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableXemHoaDon.getModel());
    	tableXemHoaDon.setRowSorter(sorter);
    	String tim = txtTimHD.getText().trim();
    	if(luaChonMaHD == true) {
			int index = timKiemTheoMaHD(tim);
			if (index == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"));
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + tim));
			}
        }
        else if(luaChonMaNV == true) {
			int index = timKiemTheoMaNV(tim);
			if (index == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"));
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + tim));
			}
        } 
        else if(luaChonMaKH == true) {
			int index = timKiemTheoMaKH(tim);
			if (index == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"));
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + tim));
			}
        } 
        else {
        	JOptionPane.showMessageDialog(null, "Bạn cần lựa chọn cách tìm!");
        }
    }//GEN-LAST:event_btnTimHDActionPerformed

    private void btnSuaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHDActionPerformed
    	if(tableXemHoaDon.getSelectedRow() != -1) {
    		if(kiemTraThongTinNhapVao()) {
    			HoaDon hd = createHDForm();
    			KhachHang kh = createKhachHangForm();
    			ArrayList<ChiTietHoaDon> listc = cthd_dao.getAllChiTietHoaDons();
    			double tien = 0.0;
    			for(ChiTietHoaDon c : listc) {
    				if(c.getMaHD().getMaHD().equals(hd.getMaHD())) {
    					tien+=c.getThanhTien();
    				}
    			}
    			double tongTien = (tien * ((100-hd.getGiamGia())/100));
    			hd.setTongTien(tongTien);
    			if(kh_dao.updateKhachHang(kh) && hd_dao.updateHoaDon(hd)) {
    				updateDataXemHoaDon();
    				JOptionPane.showMessageDialog(null, "Sửa thành công");
    			} else {
    				JOptionPane.showMessageDialog(null, "Sửa thất bại");
    			}
    		}
    	} else {
    		JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
    	}
    }//GEN-LAST:event_btnSuaHDActionPerformed

    private void btnLapHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapHoaDonActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmHoaDon().setVisible(true);
    }//GEN-LAST:event_btnLapHoaDonActionPerformed

    private void btnXemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemHoaDonActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmXemHoaDon().setVisible(true);
    }//GEN-LAST:event_btnXemHoaDonActionPerformed

    private void btnXemThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemThuocActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmThuoc().setVisible(true);
    }//GEN-LAST:event_btnXemThuocActionPerformed

    private void btnXemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemNhanVienActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmNhanVien().setVisible(true);
    }//GEN-LAST:event_btnXemNhanVienActionPerformed

    private void btnXemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemKhachHangActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmKhachHang().setVisible(true);
    }//GEN-LAST:event_btnXemKhachHangActionPerformed

    private void btnXemThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemThongKeActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmThongKe().setVisible(true);
    }//GEN-LAST:event_btnXemThongKeActionPerformed

    private void cboMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNVActionPerformed

    }//GEN-LAST:event_cboMaNVActionPerformed

    private void tableHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonMouseClicked
        int row = tableHoaDon.getSelectedRow();
    	cboMaThuoc.setSelectedItem(tableHoaDon.getValueAt(row, 1).toString());
    	txtSoLuongMua.setText(tableHoaDon.getValueAt(row, 3).toString());
    	txtDonGia.setText(tableHoaDon.getValueAt(row, 4).toString());
    	txtThanhTien.setText(tableHoaDon.getValueAt(row, 5).toString());
    }//GEN-LAST:event_tableHoaDonMouseClicked

    private void btnThemCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTHDActionPerformed
        // TODO add your handling code here:
        if(ktThuoc()==true && kiemTraThongTinNhapVao()==true) {
        	String ma = createMaCTHD();
        	String maHD = txtMaHD.getText().trim();
        	Date ngayLap = dateNgayLap.getDate();
        	NhanVien maNV = new NhanVien(cboMaNV.getSelectedItem().toString());
    		KhachHang kh = new KhachHang(txtMaKH.getText().trim());
    		double giamGia = Double.parseDouble(cboGiamGiaHD.getSelectedItem().toString());
    		double tongTienThanhToan = 0.0;
    		HoaDon hd = new HoaDon(maHD, ngayLap, tongTienThanhToan, maNV, kh, giamGia);
    		HoaDon hds = new HoaDon(txtMaHD.getText().trim());
        	int sl = Integer.parseInt(txtSoLuongMua.getText().trim());
        	Thuoc t = getThongTinThuoc(cboMaThuoc.getSelectedItem().toString());
        	double thanhTien = t.getDonGiaBan()*sl;
        	ChiTietHoaDon cthd = new ChiTietHoaDon(ma, hds, t, t.getDonGiaBan(), sl, thanhTien);
    		DefaultTableModel tableModal = (DefaultTableModel) tableHoaDon.getModel();
    		tableModal.addRow(new Object[] {cthd.getCTHD(),cthd.getMaThuoc().getMaThuoc(),t.getTenThuoc()
    				,cthd.getSoLuong(),cthd.getDonGia(),cthd.getThanhTien()});
    		cthd_dao.createChiTietHoaDon(cthd);
			ArrayList<ChiTietHoaDon> listc = cthd_dao.getAllChiTietHoaDons();
			double tien = 0.0;
			for(ChiTietHoaDon c : listc) {
				if(c.getMaHD().getMaHD().equals(cthd.getMaHD().getMaHD())) {
					tien+=c.getThanhTien();
				}
			}
			double tongTien = (tien * ((100-hd.getGiamGia())/100));
			hd.setTongTien(tongTien);
			hd_dao.updateHoaDon(hd);
    		updateDataXemHoaDon();
        }

    }//GEN-LAST:event_btnThemCTHDActionPerformed

    private void btnSuaCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTHDActionPerformed
        int row = tableHoaDon.getSelectedRow();
        if(row != -1) {
            if(kiemTraThongTinNhapVao()==true && ktThuoc()==true) {
            	Thuoc thuoc = new Thuoc(cboMaThuoc.getSelectedItem().toString());
            	int sl = Integer.parseInt(txtSoLuongMua.getText().trim());
            	String ma = tableHoaDon.getValueAt(row, 0).toString();
            	Thuoc t = getThongTinThuoc(cboMaThuoc.getSelectedItem().toString());
            	double thanhTien = t.getDonGiaBan()*sl;
            	
            	String maHD = txtMaHD.getText().trim();
            	Date ngayLap = dateNgayLap.getDate();
            	NhanVien maNV = new NhanVien(cboMaNV.getSelectedItem().toString());
        		KhachHang kh = new KhachHang(txtMaKH.getText().trim());
        		double giamGia = Double.parseDouble(cboGiamGiaHD.getSelectedItem().toString());
        		double tongTienThanhToan = 0.0;
        		HoaDon hd = new HoaDon(maHD, ngayLap, tongTienThanhToan, maNV, kh, giamGia);
        		HoaDon hds = new HoaDon(txtMaHD.getText().trim());
        		ChiTietHoaDon cthd = new ChiTietHoaDon(ma, hds, thuoc, t.getDonGiaBan(), sl, thanhTien);
        		cthd_dao.updateChiTietHoaDon(cthd);
    			ArrayList<ChiTietHoaDon> listc = cthd_dao.getAllChiTietHoaDons();
    			double tien = 0.0;
    			for(ChiTietHoaDon c : listc) {
    				if(c.getMaHD().getMaHD().equals(cthd.getMaHD().getMaHD())) {
    					tien+=c.getThanhTien();
    				}
    			}
    			double tongTien = (tien * ((100-hd.getGiamGia())/100));
    			hd.setTongTien(tongTien);
    			hd_dao.updateHoaDon(hd);            	
    			updateDataHoaDon();
            	updateDataXemHoaDon();
            	
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
        }
    }//GEN-LAST:event_btnSuaCTHDActionPerformed

    private void btnXoaCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTHDActionPerformed
        if(tableHoaDon.getSelectedRowCount() > 0) {
            try {
                int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa không?","Chú ý",JOptionPane.YES_NO_OPTION);
                if(comfrim == JOptionPane.YES_OPTION) {
                    int r = tableHoaDon.getSelectedRow();
                    DefaultTableModel modal = (DefaultTableModel) tableHoaDon.getModel();
                    modal.removeRow(r); // xóa trong table model
                    String maCTHD = (String) tableHoaDon.getValueAt(r, 0);
                    cthd_dao.DeleteChiTietHoaDon(maCTHD);
                    updateDataHoaDon();
                	String maHD = txtMaHD.getText().trim();
                	Date ngayLap = dateNgayLap.getDate();
                	NhanVien maNV = new NhanVien(cboMaNV.getSelectedItem().toString());
            		KhachHang kh = new KhachHang(txtMaKH.getText().trim());
            		double giamGia = Double.parseDouble(cboGiamGiaHD.getSelectedItem().toString());
            		double tongTienThanhToan = 0.0;
            		HoaDon hd = new HoaDon(maHD, ngayLap, tongTienThanhToan, maNV, kh, giamGia);
            		ArrayList<ChiTietHoaDon> listc = cthd_dao.getAllChiTietHoaDons();
        			double tien = 0.0;
        			for(ChiTietHoaDon c : listc) {
        				if(c.getMaHD().getMaHD().equals(hd.getMaHD())) {
        					tien+=c.getThanhTien();
        				}
        			}
        			double tongTien = (tien * ((100-hd.getGiamGia())/100));
        			hd.setTongTien(tongTien);
            		hd_dao.updateHoaDon(hd);
                    updateDataXemHoaDon();
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Bạn đã chọn không xóa");
                }
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, "Xóa thất bại");
                e2.printStackTrace();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần xóa");
        }
    }//GEN-LAST:event_btnXoaCTHDActionPerformed

    private void btnLamMoiCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiCTHDActionPerformed
        txtSoLuongMua.setText("");
        cboMaThuoc.setSelectedItem("MT00001");
        txtDonGia.setText("");
        txtThanhTien.setText("");
    }//GEN-LAST:event_btnLamMoiCTHDActionPerformed

    private void cboGiamGiaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiamGiaHDActionPerformed
        // TODO add your handling code here:
    	int row = tableXemHoaDon.getSelectedRow();
    	if(row != -1) {
    		HoaDon hd = createHDForm();
    		double giamGia = Double.parseDouble(cboGiamGiaHD.getSelectedItem().toString());
    		hd.setGiamGia(giamGia);
    		ArrayList<ChiTietHoaDon> listc = cthd_dao.getAllChiTietHoaDons();
    		double tien = 0.0;
    		for(ChiTietHoaDon c : listc) {
    			if(c.getMaHD().getMaHD().equals(hd.getMaHD())) {
    				tien+=c.getThanhTien();
    			}
    		}
    		double tongTien = (tien * ((100-hd.getGiamGia())/100));
    		hd.setTongTien(tongTien);
    		txtTongTienThanhToanHD.setText(hd.getTongTien()+"");
    		hd_dao.updateHoaDon(hd);
    		updateDataXemHoaDon();
    		
    	} else {
    		JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng!");
    	}
    }//GEN-LAST:event_cboGiamGiaHDActionPerformed

    private void btnXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemChiTietActionPerformed
        // TODO add your handling code here:
    	updateDataHoaDon();
    	frmChiTiet.setVisible(true);
    }//GEN-LAST:event_btnXemChiTietActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmXemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmXemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmXemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmXemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmXemHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GioiTinhKH;
    private javax.swing.JLabel HoKH;
    private javax.swing.JLabel MaKH;
    private javax.swing.JLabel SDTKH;
    private javax.swing.JLabel TenKH;
    private javax.swing.JButton btnLamMoiCTHD;
    private javax.swing.JButton btnLamMoiHD;
    private javax.swing.JButton btnLapHoaDon;
    private javax.swing.JButton btnSuaCTHD;
    private javax.swing.JButton btnSuaHD;
    private javax.swing.JButton btnThemCTHD;
    private javax.swing.JButton btnThoatHD;
    private javax.swing.JButton btnTimHD;
    private javax.swing.JButton btnXemChiTiet;
    private javax.swing.JButton btnXemHoaDon;
    private javax.swing.JButton btnXemKhachHang;
    private javax.swing.JButton btnXemNhanVien;
    private javax.swing.JButton btnXemThongKe;
    private javax.swing.JButton btnXemThuoc;
    private javax.swing.JButton btnXoaCTHD;
    private javax.swing.JButton btnXoaHD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> cboGiamGiaHD;
    private javax.swing.JComboBox<String> cboMaNV;
    private javax.swing.JComboBox<String> cboMaThuoc;
    private com.toedter.calendar.JDateChooser dateNgayLap;
    private javax.swing.JFrame frmChiTiet;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenu menuExit;
    private javax.swing.JRadioButton rdoKHNam;
    private javax.swing.JRadioButton rdoKHNu;
    private javax.swing.JRadioButton rdoMaHD;
    private javax.swing.JRadioButton rdoMaKH;
    private javax.swing.JRadioButton rdoMaNV;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tableXemHoaDon;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtHoKH;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSoLuongMua;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTimHD;
    private javax.swing.JTextField txtTongTienThanhToanHD;
    private javax.swing.JTextField txtTongTienThuoc;
    // End of variables declaration//GEN-END:variables
}
