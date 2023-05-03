package gui;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
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
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.DiaChi;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;

public class FrmHoaDon extends javax.swing.JFrame {
	private Thuoc_DAO t_dao = new Thuoc_DAO();
	
	private ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
	private HoaDon_DAO hd_dao = new HoaDon_DAO();
	private KhachHang_DAO kh_dao = new KhachHang_DAO();
	private NhanVien_DAO nv_dao = new NhanVien_DAO();
	
	Pattern regexHo = Pattern.compile("^[a-zA-Z\\s]+$");
	Pattern regexTen = Pattern.compile("^([A-Za-z\\'\\.\\-\\d\\s]+)");
	Pattern regexSdt = Pattern.compile("^[\\d]{10}$");
	Pattern regexSL = Pattern.compile("[0-9]+");
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form FrmLapHoaDon
     */
    public FrmHoaDon() {
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
        jDialogTimThuoc.setLocationRelativeTo(null);
        txtMaHD.setText(createMaHD());
        txtMaKH.setText(createMaKH());
		updateDataThuoc();

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
    	if(txtSoLuongMua.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(null, "Số lượng mua không được rỗng");
    		txtSoLuongMua.requestFocus();
    		txtSoLuongMua.selectAll();
    		return false;
    	}
    	if(!regexSL.matcher(txtSoLuongMua.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, Số lượng mua chỉ bao gồm chữ số và lớn hơn 0");
    		txtSoLuongMua.requestFocus();
    		txtSoLuongMua.selectAll();
    		return false;
    	}
    	if(Integer.parseInt(txtSoLuongMua.getText().trim()) <= 0) {
    		JOptionPane.showMessageDialog(null, "Số lượng mua không được bé hơn hoặc bằng 0");
    		txtSoLuongMua.requestFocus();
    		txtSoLuongMua.selectAll();
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
    
    public String createMaHD() {
    	ArrayList<HoaDon> listHD = hd_dao.getAllHoaDons();
    	Integer count = 1;
    	Integer countNew = 0;
    	for(HoaDon hd : listHD) {
    		countNew = Integer.parseInt(hd.getMaHD().substring(2));
    		if(countNew > count) {
    			break;
    		}
    		if(countNew >= count) {
    			count = (++count);
    		}
    	}
    	String res5 = count.toString().length() == 1 ? ("HD0000" + count)
    			: count.toString().length() == 2 ? ("HD000" + count) 
    					: count.toString().length() == 3 ? ("HD00" + count)
    							: count.toString().length() == 4 ? ("HD0" + count)
    									: ("HD" + count);
    	return res5;
    }
    
    public String createMaKH() {
    	ArrayList<KhachHang> listKH = kh_dao.getAllKhachHang();
    	Integer count = 1;
    	Integer countNew = 0;
    	for(KhachHang kh : listKH) {
    		countNew = Integer.parseInt(kh.getMaKH().substring(2));
    		if(countNew > count) {
    			break;
    		}
    		if(countNew >= count) {
    			count = (++count);
    		}
    	}
    	String res5 = count.toString().length() == 1 ? ("KH0000" + count)
    			: count.toString().length() == 2 ? ("KH000" + count) 
    					: count.toString().length() == 3 ? ("KH00" + count)
    							: count.toString().length() == 4 ? ("KH0" + count)
    									: ("KH" + count);
    	return res5;

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
    		if(countNew >= count) {
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

    public HoaDon createHDForm() {

    	String maHD = txtMaHD.getText().trim();
    	Date ngayLap = dateNgayLap.getDate();
    	NhanVien maNV = new NhanVien(cboMaNV.getSelectedItem().toString());
		KhachHang kh = new KhachHang(txtMaKH.getText().trim());
		double giamGia = Double.parseDouble(cboGiamGiaHD.getSelectedItem().toString());
		
//		double tongTienThanhToan = (tongTien * ((100-giamGia)/100));
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
    
    public ChiTietHoaDon createCTHDForm() {
    	HoaDon maHD = new HoaDon(txtMaHD.getText().trim());
    	Thuoc maThuoc = new Thuoc(cboMaThuoc.getSelectedItem().toString());
    	int soLuongMua = Integer.parseInt(txtSoLuongMua.getText().trim());
    	
    	ArrayList<Thuoc> listThuoc = t_dao.getAlltbThuoc();
    	double donGia = 0.0;
    	
    	for(Thuoc t : listThuoc) {
    		if(cboMaThuoc.getSelectedItem().toString().equals(t.getMaThuoc())) {
    			donGia = t.getDonGiaBan();
    			break;
    		}
    	}
    	
    	double thanhTien = donGia*soLuongMua;
    	String maCTHD = createMaCTHD();
    	
    	ChiTietHoaDon cthd = new ChiTietHoaDon(maCTHD,maHD, maThuoc, donGia, soLuongMua, thanhTien);
    	
    	return cthd;
    	
    }
    
	public void deleteTable() {
		DefaultTableModel dm = (DefaultTableModel) tableHoaDon.getModel();
		dm.getDataVector().removeAllElements();
		tableHoaDon.clearSelection();
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
	
	public void updateDataThuoc() {
		deleteTable();
		ArrayList<Thuoc> dst = t_dao.getAlltbThuoc();
		DefaultTableModel tableModal = (DefaultTableModel) tableThuoc.getModel();
		for(Thuoc t: dst) {
//			String tenDonViThuoc = getDVThuoc(t.getDvThuoc().getMaDonVi());
			tableModal.addRow(new Object[] {t.getMaThuoc(),t.getTenThuoc(),t.getDvThuoc().getMaDonVi(),t.getDonGiaBan()});
		
		}
		
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
	public void updateDataHoaDon() {
		deleteTable();
		ArrayList<ChiTietHoaDon> listCthd = cthd_dao.getAllChiTietHoaDons();
		DefaultTableModel tableModal = (DefaultTableModel) tableHoaDon.getModel();
		HoaDon hd = getThongTinHD(txtMaHD.getText());
		KhachHang kh = getThongTinKH(txtMaKH.getText());
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
        jDialogTimThuoc = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableThuoc = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        btnTimDialg = new javax.swing.JButton();
        txtTimThuoc = new javax.swing.JTextField();
        rdoMa = new javax.swing.JRadioButton();
        rdoTen = new javax.swing.JRadioButton();
        buttonGroup4 = new javax.swing.ButtonGroup();
        btnLapHoaDon = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuongMua = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnSuaCTHD = new javax.swing.JButton();
        btnXoaCTHD = new javax.swing.JButton();
        btnLamMoiCTHD = new javax.swing.JButton();
        btnThemCTHD = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();
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
        jLabel9 = new javax.swing.JLabel();
        cboMaNV = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        cboMaThuoc = new javax.swing.JComboBox<>();
        dateNgayLap = new com.toedter.calendar.JDateChooser();
        btnTimThuoc = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnThemHD = new javax.swing.JButton();
        btnThoatHD = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTongTienThanhToanHD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTongTienThuoc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cboGiamGiaHD = new javax.swing.JComboBox<>();
        btnXemThuoc = new javax.swing.JButton();
        btnXemHoaDon = new javax.swing.JButton();
        btnXemNhanVien = new javax.swing.JButton();
        btnXemThongKe = new javax.swing.JButton();
        btnXemKhachHang = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuExit = new javax.swing.JMenu();

        jDialogTimThuoc.setTitle("Tìm thuốc");
        jDialogTimThuoc.setSize(new java.awt.Dimension(600, 300));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setColumnHeaderView(null);
        jScrollPane3.setRowHeaderView(null);
        jScrollPane3.setViewportView(null);

        tableThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Đơn vị thuốc", "Đơn giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableThuoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableThuoc.setShowHorizontalLines(true);
        tableThuoc.setShowVerticalLines(true);
        tableThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableThuocMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableThuoc);

        jLabel21.setText("Nhập vào thông tin tìm:");

        btnTimDialg.setBackground(new java.awt.Color(0, 153, 255));
        btnTimDialg.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimDialg.setText("Tìm");
        btnTimDialg.setFocusable(false);
        btnTimDialg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimDialg.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimDialg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimDialgActionPerformed(evt);
            }
        });

        buttonGroup4.add(rdoMa);
        rdoMa.setText("Mã thuốc");

        buttonGroup4.add(rdoTen);
        rdoTen.setText("Tên thuốc");

        javax.swing.GroupLayout jDialogTimThuocLayout = new javax.swing.GroupLayout(jDialogTimThuoc.getContentPane());
        jDialogTimThuoc.getContentPane().setLayout(jDialogTimThuocLayout);
        jDialogTimThuocLayout.setHorizontalGroup(
            jDialogTimThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogTimThuocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(jDialogTimThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogTimThuocLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(rdoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(rdoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jDialogTimThuocLayout.createSequentialGroup()
                        .addComponent(txtTimThuoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimDialg, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
        );
        jDialogTimThuocLayout.setVerticalGroup(
            jDialogTimThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogTimThuocLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jDialogTimThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialogTimThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(txtTimThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTimDialg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialogTimThuocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("HÓA ĐƠN");

        jLabel3.setText("Mã hóa đơn:");

        jLabel4.setText("Mã nhân viên:");

        jLabel6.setText("Ngày lập:");

        btnSuaCTHD.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaCTHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSuaCTHD.setText("Sửa chi tiết hóa đơn");
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
        btnXoaCTHD.setText("Xóa chi tiết hóa đơn");
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

        btnThemCTHD.setBackground(new java.awt.Color(0, 153, 255));
        btnThemCTHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemCTHD.setText("Thêm chi tiết hóa đơn");
        btnThemCTHD.setFocusable(false);
        btnThemCTHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThemCTHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemCTHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoiCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnThemCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuaCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(btnXoaCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoiCTHD, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setColumnHeaderView(null);
        jScrollPane2.setRowHeaderView(null);
        jScrollPane2.setViewportView(null);

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
        tableHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableHoaDon.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableHoaDon.setShowHorizontalLines(true);
        tableHoaDon.setShowVerticalLines(true);
        tableHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHoaDonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableHoaDon);
        if (tableHoaDon.getColumnModel().getColumnCount() > 0) {
            tableHoaDon.getColumnModel().getColumn(0).setMinWidth(150);
            tableHoaDon.getColumnModel().getColumn(1).setMinWidth(100);
            tableHoaDon.getColumnModel().getColumn(3).setMinWidth(100);
        }
        tableHoaDon.getAccessibleContext().setAccessibleParent(jScrollPane2);

        MaKH.setText("Mã khách hàng:");

        HoKH.setText("Họ khách hàng:");

        TenKH.setText("Tên khách hàng:");

        GioiTinhKH.setText("Giới tính:");

        buttonGroup3.add(rdoKHNu);
        rdoKHNu.setText("Nữ");

        buttonGroup3.add(rdoKHNam);
        rdoKHNam.setSelected(true);
        rdoKHNam.setText("Nam");

        SDTKH.setText("Số điện thoại:");

        txtMaKH.setEditable(false);

        jLabel9.setText("Mã thuốc:");

        cboMaNV.setEditable(true);
        cboMaNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cboMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaNVActionPerformed(evt);
            }
        });

        jLabel10.setText("Số lượng mua:");

        txtMaHD.setEditable(false);

        cboMaThuoc.setEditable(true);
        cboMaThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        dateNgayLap.setDate(new Date());
        dateNgayLap.setDateFormatString("dd-MM-yyyy");

        btnTimThuoc.setBackground(new java.awt.Color(0, 153, 255));
        btnTimThuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimThuoc.setText("Tìm thuốc");
        btnTimThuoc.setFocusable(false);
        btnTimThuoc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimThuoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimThuocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboMaNV, 0, 261, Short.MAX_VALUE)
                            .addComponent(txtMaHD)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuongMua, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(cboMaThuoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(MaKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GioiTinhKH)
                                .addGap(50, 50, 50))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SDTKH)
                                    .addComponent(HoKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TenKH, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtSDTKH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(rdoKHNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdoKHNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21))
                                    .addComponent(txtHoKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))))
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
                            .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(HoKH)
                            .addComponent(txtHoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(TenKH)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(GioiTinhKH)
                                .addComponent(rdoKHNu)
                                .addComponent(rdoKHNam))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboMaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnTimThuoc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SDTKH)
                                .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(txtSoLuongMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rdoKHNu.setSelected(true);
        ArrayList<NhanVien> dsNV = nv_dao.getAllNhanViens();
        for(NhanVien nv : dsNV) {
            cboMaNV.addItem(nv.getMaNV());
        }
        ArrayList<Thuoc> dsT = t_dao.getAlltbThuoc();
        for(Thuoc t : dsT) {
            cboMaThuoc.addItem(t.getMaThuoc());
        }

        btnThemHD.setBackground(new java.awt.Color(0, 153, 255));
        btnThemHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemHD.setText("Thêm hóa đơn mới");
        btnThemHD.setFocusable(false);
        btnThemHD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThemHD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDActionPerformed(evt);
            }
        });

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

        jLabel5.setText("Tổng tiền thuốc:");

        txtTongTienThanhToanHD.setEditable(false);

        jLabel7.setText("Tổng tiền cần thanh toán");

        txtTongTienThuoc.setEditable(false);

        jLabel11.setText("Giảm giá:");

        cboGiamGiaHD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"0","5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90","95","100"}));
        cboGiamGiaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiamGiaHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtTongTienThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(494, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboGiamGiaHD, javax.swing.GroupLayout.Alignment.LEADING, 0, 271, Short.MAX_VALUE)
                            .addComponent(txtTongTienThanhToanHD, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(72, 72, 72)
                        .addComponent(btnThemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThoatHD, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTongTienThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel11))
                            .addComponent(cboGiamGiaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addComponent(btnThoatHD)
                    .addComponent(btnThemHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtTongTienThanhToanHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
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
                        .addGap(0, 5, Short.MAX_VALUE)))
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
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemNhanVienActionPerformed
        // TODO add your handling code here:
    	dispose();
        new FrmNhanVien().setVisible(true);
    }//GEN-LAST:event_btnXemNhanVienActionPerformed

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
    	txtMaKH.setText(createMaKH());
		txtMaHD.setText(createMaHD());
    	deleteTable();
    	tableHoaDon.setRowHeight(20);
    	txtSoLuongMua.setText("");
    	txtHoKH.setText("");
    	txtTenKH.setText("");
    	txtSDTKH.setText("");
    	txtTongTienThuoc.setText("");
    	txtTongTienThanhToanHD.setText("");
    	dateNgayLap.setDate(new Date());
    	cboGiamGiaHD.setSelectedItem("0");
    	cboMaThuoc.setSelectedItem("MT00001");
    	
    }//GEN-LAST:event_btnThemHDActionPerformed

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
    }//GEN-LAST:event_menuExitMenuSelected

    private void btnThemCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTHDActionPerformed
        // TODO add your handling code here:
    	if(kiemTraThongTinNhapVao()) {
    		
    		KhachHang kh = createKhachHangForm();
    		ChiTietHoaDon cthd = createCTHDForm();
    		HoaDon hd = createHDForm();
    		if(getThongTinKH(kh.getMaKH())==null) {
    			kh_dao.createKhachHang(kh);
    		}
    		if(getThongTinKH(kh.getMaKH())!=null) {
    			kh_dao.updateKhachHang(kh);
    		}
    		if(getThongTinHD(hd.getMaHD())==null) {
    			hd_dao.createHoaDon(hd);
    		}
    		if(getThongTinHD(hd.getMaHD())!=null) {
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
    		}
    		Thuoc t = getThongTinThuoc(cthd.getMaThuoc().getMaThuoc());
    		DefaultTableModel tableModal = (DefaultTableModel) tableHoaDon.getModel();
    		tableModal.addRow(new Object[] {cthd.getCTHD(),cthd.getMaThuoc().getMaThuoc(),t.getTenThuoc()
    				,cthd.getSoLuong(),cthd.getDonGia(),cthd.getThanhTien()});
    		ArrayList<ChiTietHoaDon> listc = cthd_dao.getAllChiTietHoaDons();
    		double tien = 0.0;
    		for(ChiTietHoaDon c : listc) {
    			if(c.getMaHD().getMaHD().equals(cthd.getMaHD().getMaHD())) {
    				tien+=c.getThanhTien();
    			}
    		}
    		txtTongTienThuoc.setText(tien+"");
    		txtTongTienThanhToanHD.setText(hd.getTongTien()+"");
    	}
    	

    }//GEN-LAST:event_btnThemCTHDActionPerformed

    private void btnLamMoiCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiCTHDActionPerformed
    	dateNgayLap.setDate(new Date());
    	txtSoLuongMua.setText("");
    	cboMaThuoc.setSelectedItem("MT00001");
    }//GEN-LAST:event_btnLamMoiCTHDActionPerformed

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
    				HoaDon hd = createHDForm();
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
        			txtTongTienThuoc.setText(tien+"");
        			txtTongTienThanhToanHD.setText(hd.getTongTien()+"");
    				updateDataHoaDon();
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

    private void btnSuaCTHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaCTHDActionPerformed
    	int row = tableHoaDon.getSelectedRow();
    	if(row != -1) {
    		if(kiemTraThongTinNhapVao()) {
    			KhachHang kh = createKhachHangForm();
    			ChiTietHoaDon cthd = getThongTinCTHD((String) tableHoaDon.getValueAt(row, 0));
    			HoaDon hd = createHDForm();
    			kh_dao.updateKhachHang(kh);
    			cthd.setSoLuong(Integer.parseInt(txtSoLuongMua.getText()));
    			Thuoc thuoc = new Thuoc(cboMaThuoc.getSelectedItem().toString());
    			cthd.setMaThuoc(thuoc);
    	    	ArrayList<Thuoc> listThuoc = t_dao.getAlltbThuoc();
    	    	double donGia = 0.0;
    	    	for(Thuoc t : listThuoc) {
    	    		if(cthd.getMaThuoc().getMaThuoc().equals(t.getMaThuoc())) {
    	    			donGia = t.getDonGiaBan();
    	    			break;
    	    		}
    	    	}
    	    	double thanhTien = donGia*cthd.getSoLuong();
    	    	cthd.setThanhTien(thanhTien);
    			cthd_dao.updateChiTietHoaDon(cthd);
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
    			txtTongTienThuoc.setText(tien+"");
    			txtTongTienThanhToanHD.setText(hd.getTongTien()+"");
    			updateDataHoaDon();
        	}
    	} else {
    		JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
    	}
    }//GEN-LAST:event_btnSuaCTHDActionPerformed

    private void tableHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHoaDonMouseClicked
    	int row = tableHoaDon.getSelectedRow();
    	cboMaThuoc.setSelectedItem(tableHoaDon.getValueAt(row, 1).toString());
    	txtSoLuongMua.setText(tableHoaDon.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tableHoaDonMouseClicked

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

    private void btnLapHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapHoaDonActionPerformed
        // TODO add your handling code here:
        dispose();
        new FrmHoaDon().setVisible(true);
    }//GEN-LAST:event_btnLapHoaDonActionPerformed

    private void cboMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaNVActionPerformed
    	
    }//GEN-LAST:event_cboMaNVActionPerformed

    private void btnTimThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimThuocActionPerformed
        // TODO add your handling code here:
    	jDialogTimThuoc.setVisible(true);
    }//GEN-LAST:event_btnTimThuocActionPerformed

    private void tableThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableThuocMouseClicked
    }//GEN-LAST:event_tableThuocMouseClicked

    private void cboGiamGiaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiamGiaHDActionPerformed
        // TODO add your handling code here:
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
    		
    	
    }//GEN-LAST:event_cboGiamGiaHDActionPerformed

    private void btnTimDialgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimDialgActionPerformed
        // TODO add your handling code here:
    	boolean luaChonMa = rdoMa.isSelected() ? true : false;
    	boolean luaChonTen = rdoTen.isSelected() ? true : false;
    	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableThuoc.getModel());
    	tableThuoc.setRowSorter(sorter);
    	String tim = txtTimThuoc.getText().trim();
    	if(luaChonMa == true) {
			int index = timKiemTheoMaThuoc(tim);
			if (index == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"));
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + tim));
			}
        }
        else if(luaChonTen == true) {
			int index = timKiemTheoTenThuoc(tim);
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
    }//GEN-LAST:event_btnTimDialgActionPerformed

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
            java.util.logging.Logger.getLogger(FrmHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new FrmHoaDon().setVisible(true);
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
    private javax.swing.JButton btnLapHoaDon;
    private javax.swing.JButton btnSuaCTHD;
    private javax.swing.JButton btnThemCTHD;
    private javax.swing.JButton btnThemHD;
    private javax.swing.JButton btnThoatHD;
    private javax.swing.JButton btnTimDialg;
    private javax.swing.JButton btnTimThuoc;
    private javax.swing.JButton btnXemHoaDon;
    private javax.swing.JButton btnXemKhachHang;
    private javax.swing.JButton btnXemNhanVien;
    private javax.swing.JButton btnXemThongKe;
    private javax.swing.JButton btnXemThuoc;
    private javax.swing.JButton btnXoaCTHD;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> cboGiamGiaHD;
    private javax.swing.JComboBox<String> cboMaNV;
    private javax.swing.JComboBox<String> cboMaThuoc;
    private com.toedter.calendar.JDateChooser dateNgayLap;
    private javax.swing.JDialog jDialogTimThuoc;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JRadioButton rdoMa;
    private javax.swing.JRadioButton rdoTen;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTable tableThuoc;
    private javax.swing.JTextField txtHoKH;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtSoLuongMua;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimThuoc;
    private javax.swing.JTextField txtTongTienThanhToanHD;
    private javax.swing.JTextField txtTongTienThuoc;
    // End of variables declaration//GEN-END:variables
}
