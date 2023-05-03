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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.regex.Pattern;

import connectDB.ConnectDB;
import dao.DiaChi_DAO;
import dao.DonViThuoc_DAO;
import dao.NhaCungCap_DAO;
import dao.NhanVien_DAO;
import dao.Thuoc_DAO;
import entity.DiaChi;
import entity.DonViThuoc;
import entity.HoaDon;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;

import java.time.LocalDateTime;

public class FrmNhanVien extends javax.swing.JFrame {
	private DonViThuoc_DAO dv_dao = new DonViThuoc_DAO();
	private Thuoc_DAO t_dao = new Thuoc_DAO();
	private NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
	private DiaChi_DAO dc_dao = new DiaChi_DAO();
	private NhanVien_DAO nv_dao = new NhanVien_DAO(); 
	
	Pattern regexHo = Pattern.compile("^[a-zA-Z\\s]+$");
	Pattern regexTen = Pattern.compile("^([A-Za-z\\'\\.\\-\\d\\s]+)");
	Pattern regexSDT = Pattern.compile("^[\\d]{10}$");
	Pattern regexHSL = Pattern.compile("[0-9\\.]+");
	Pattern regexDC = Pattern.compile("^[a-zA-Z0-9\\s]+$");
	Pattern regexCCCD = Pattern.compile("^0[0-9]{11}$");
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form FrmLapHoaDon
     */
    public FrmNhanVien() {
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
        
        txtMaDCNV.setText(createMaDC());
        txtMaNV.setText(createMaNV());
        
        updateDataNV();
    }
    public boolean kiemTraThongTinNhapVao() {
    	if(txtHoNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Họ nhân viên không được rỗng!");
    		txtHoNV.requestFocus();
    		txtHoNV.selectAll();
    		return false;
    		
    	}
    	if(!regexHo.matcher(txtHoNV.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, Họ nhân viên chỉ chứa ký tự là chữ");
    		txtHoNV.requestFocus();
    		txtHoNV.selectAll();
    		return false;
    	}
    	
    	if(txtTenNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên nhân viên không được rỗng!");
    		txtTenNV.requestFocus();
    		txtTenNV.selectAll();
    		return false;
    		
    	}
    	if(!regexTen.matcher(txtTenNV.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên nhân viên chứa ký tự là chữ");
    		txtTenNV.requestFocus();
    		txtTenNV.selectAll();
    		return false;
    	}
    	Date dateNgay = dateNSNV.getDate();
    	Date dateKT = new GregorianCalendar(2003,Calendar.JANUARY,1).getTime();
    	
    	if(dateNgay == null) {
    		JOptionPane.showMessageDialog(null, "Ngày sinh không được rỗng!");
    		return false;
    		
    	}
    	if(dateNgay.after(new Date())) {
    		JOptionPane.showMessageDialog(null, "Ngày sinh phải trước ngày hiện tại");
    		dateNSNV.requestFocus();
    		return false;
    	}
    	if(dateNgay.after(dateKT)) {
    		JOptionPane.showMessageDialog(null, "Nhân viên phải hơn 18 tuổi, ngày sinh phải trước 01-01-2003");
    		dateNSNV.requestFocus();
    		return false;
    	}
		if(txtSDTNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Số điện thoại nhân viên không được rỗng!");
    		return false;
    		
    	}
    	if(!regexSDT.matcher(txtSDTNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, số điện thoại nhân viên chỉ chứa ký tự là số từ 0-9 và đủ 10 chữ số");
    		txtSDTNV.requestFocus();
    		txtSDTNV.selectAll();
    		return false;
    	}
    	if(txtCCCDNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "CCCD nhân viên không được rỗng!");
    		return false;
    		
    	}
    	if(!regexCCCD.matcher(txtCCCDNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, CCCD nhân viên chỉ chứa ký tự là số từ 0-9, chữ số đầu tiên bắt buộc là số 0 và có 12 chữ số");
    		txtCCCDNV.requestFocus();
    		txtCCCDNV.selectAll();
    		return false;
    	}
    	if(txtHSLuongNV.getText().trim().length()<=0) {
    		JOptionPane.showMessageDialog(null, "Hệ số lương không được rỗng");
    		txtHSLuongNV.requestFocus();
    		txtHSLuongNV.selectAll();
    		return false;
    	}
    	if(!regexHSL.matcher(txtHSLuongNV.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, hệ số lương chỉ bao gồm chữ số và lớn hơn 0");
    		txtHSLuongNV.requestFocus();
    		txtHSLuongNV.selectAll();
    		return false;
    	}
    	if(Double.parseDouble(txtHSLuongNV.getText().trim()) <= 0) {
    		JOptionPane.showMessageDialog(null, "Hệ số lương không được bé hơn hoặc bằng 0");
    		txtHSLuongNV.requestFocus();
    		txtHSLuongNV.selectAll();
    		return false;
    	}
    	if(txtSoNhaNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Số nhà không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtSoNhaNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, số nhà bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtSoNhaNV.requestFocus();
    		txtSoNhaNV.selectAll();
    		return false;
    	}
    	if(txtTenDuongNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên đường không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtTenDuongNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên đường bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtTenDuongNV.requestFocus();
    		txtTenDuongNV.selectAll();
    		return false;
    	}
    	if(txtPhuongNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên phường không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtPhuongNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên phường bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtPhuongNV.requestFocus();
    		txtPhuongNV.selectAll();
    		return false;
    	}
    	if(txtQuanNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên quận không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtQuanNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên quận bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtQuanNV.requestFocus();
    		txtQuanNV.selectAll();
    		return false;
    	}
    	if(txtThanhPhoNV.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên thành phố không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtThanhPhoNV.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên thành phố bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtThanhPhoNV.requestFocus();
    		txtThanhPhoNV.selectAll();
    		return false;
    	}
    	return true;
    }
    
    public DiaChi createDCForm() {
    	String maDC = txtMaDCNV.getText().trim();
		String soNha = txtSoNhaNV.getText().trim();
		String tenDuong = txtTenDuongNV.getText().trim();
		String phuong = txtPhuongNV.getText().trim();
		String quan = txtQuanNV.getText().trim();
		String thanhPho = txtThanhPhoNV.getText().trim();
		
		DiaChi dc = new DiaChi(maDC, soNha, tenDuong, phuong, quan, thanhPho);
		return dc;
	}
    public NhanVien createNhanVienForm() {
    	
		String maNV = txtMaNV.getText().trim();
		String hoNV = txtHoNV.getText().trim();
		String tenNV = txtTenNV.getText().trim();
		Date ngaySinh = dateNSNV.getDate();
		String sdt = txtSDTNV.getText().trim();
		String cccd = txtCCCDNV.getText().trim();
		String caLam = cboCaLamNV.getSelectedItem().toString();
		double heSoLuong = Double.parseDouble(txtHSLuongNV.getText().trim());
		String gioiTinhNV = "";
		if(rdoNVNu.isSelected()) {
			gioiTinhNV = "Nữ";
		}
		if(rdoNVNam.isSelected()) {
			gioiTinhNV = "Nam";
		}
		DiaChi dc = new DiaChi(txtMaDCNV.getText().trim());
		
		NhanVien nv = new NhanVien(maNV, caLam, tenNV, hoNV, cccd, sdt, heSoLuong, ngaySinh, dc, gioiTinhNV);
		
		return nv;

	}
    
    public String createMaDC() {
    	ArrayList<DiaChi> listDC = dc_dao.getAlltbDiaChi();
    	Integer count = 1;
    	Integer countNew = 0;
    	for(DiaChi dc : listDC) {
    		countNew = Integer.parseInt(dc.getMaDiaChi().substring(2));
    		if(countNew > count) {
    			break;
    		}
    		if(countNew >= count) {
    			count = (++count);
    		}
    	}
//    	count = count + 1;
    	String res5 = count.toString().length() == 1 ? ("DC0000" + count)
    			: count.toString().length() == 2 ? ("DC000" + count) 
    					: count.toString().length() == 3 ? ("DC00" + count)
    							: count.toString().length() == 4 ? ("DC0" + count)
    									: ("DC" + count);
    	return res5;

    }
    
    public String createMaNV() {
    	ArrayList<NhanVien> listNV = nv_dao.getAllNhanViens();
    	Integer count = 1;
    	Integer countNew = 0;
    	for(NhanVien nv : listNV) {
    		countNew = Integer.parseInt(nv.getMaNV().substring(2));
    		if(countNew > count) {
    			break;
    		}
    		if(countNew >= count) {
    			count = (++count);
    		}
    	}
//    	count = count + 1;
    	String res5 = count.toString().length() == 1 ? ("NV0000" + count)
    			: count.toString().length() == 2 ? ("NV000" + count) 
    					: count.toString().length() == 3 ? ("NV00" + count)
    							: count.toString().length() == 4 ? ("NV0" + count)
    									: ("NV" + count);
    	return res5;

    }
    
	public void deleteTable() {
		DefaultTableModel dm = (DefaultTableModel) tableNV.getModel();
		dm.getDataVector().removeAllElements();
	}
	
	public void updateDataNV() {
		deleteTable();
		ArrayList<NhanVien> dsnv = nv_dao.getAllNhanViens();
		DefaultTableModel tableModal = (DefaultTableModel) tableNV.getModel();
		for(NhanVien nv: dsnv) {
			DiaChi dc = getThongTinDiaChi(nv.getDiaChi().getMaDiaChi());
			tableModal.addRow(new Object[] {nv.getMaNV(),nv.getHo(),nv.getTen(),df.format(nv.getNgaySinh())
					,nv.getSoDienThoai(),nv.getCccd(),nv.getCaLamViec(),nv.getHeSoLuong(),nv.getGioiTinh()
					,dc.getMaDiaChi(),dc.getSoNha(),dc.getTenDuong(),dc.getPhuong(),dc.getQuan(),dc.getThanhPho()});
		
		}
		
	}
	
	public DiaChi getThongTinDiaChi(String ma) {
		ArrayList<DiaChi> listDC = dc_dao.getAlltbDiaChi();
		for(DiaChi dc : listDC) {
			if(dc.getMaDiaChi().equalsIgnoreCase(ma))
				return dc;
		}
		return null;
	}
	
	public NhaCungCap getThongTinNhaCungCap(String ma) {
		ArrayList<NhaCungCap> listNCC = ncc_dao.getAlltbNhaCungCap();
		for(NhaCungCap ncc : listNCC) {
			if(ncc.getMaNCC().equalsIgnoreCase(ma))
				return ncc;
		}
		return null;
	}
	
	public NhanVien getThongTinNhanVien(String ma) {
		ArrayList<NhanVien> listNV = nv_dao.getAllNhanViens();
		for(NhanVien nv : listNV) {
			if(nv.getMaNV().equalsIgnoreCase(ma))
				return nv;
		}
		return null;
	}
	
	public int timKiemTheoMaNV(String ma) {
		ArrayList<NhanVien> listNV = nv_dao.getAllNhanViens();
		for(NhanVien nv : listNV) {
			if(nv.getMaNV().equalsIgnoreCase(ma))
				return listNV.indexOf(nv);
		}
		return -1;
	}
	
	public int timKiemTheoTenNV(String ten) {
		ArrayList<NhanVien> listNV = nv_dao.getAllNhanViens();
		for(NhanVien nv : listNV) {
			if(nv.getTen().equalsIgnoreCase(ten))
				return listNV.indexOf(nv);
		}
		return -1;
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHoNV = new javax.swing.JTextField();
        dateNSNV = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSDTNV = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboCaLamNV = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCCCDNV = new javax.swing.JTextField();
        txtHSLuongNV = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTenDuongNV = new javax.swing.JTextField();
        txtSoNhaNV = new javax.swing.JTextField();
        txtQuanNV = new javax.swing.JTextField();
        txtMaDCNV = new javax.swing.JTextField();
        txtThanhPhoNV = new javax.swing.JTextField();
        txtPhuongNV = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnSuaNV = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        btnLamMoiNV = new javax.swing.JButton();
        btnThemNV = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableNV = new javax.swing.JTable();
        txtTenNV = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        rdoNVNu = new javax.swing.JRadioButton();
        rdoNVNam = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtTimNV = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        rdoTenNV = new javax.swing.JRadioButton();
        rdoMaNV = new javax.swing.JRadioButton();
        btnTimNV = new javax.swing.JButton();
        btnThoatNV = new javax.swing.JButton();
        btnLapHoaDon = new javax.swing.JButton();
        btnXemHoaDon = new javax.swing.JButton();
        btnXemThuoc = new javax.swing.JButton();
        btnXemNhanVien = new javax.swing.JButton();
        btnXemKhachHang = new javax.swing.JButton();
        btnXemThongKe = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("NHÂN VIÊN");

        jLabel3.setText("Mã nhân viên:");

        jLabel4.setText("Họ nhân viên:");

        jLabel5.setText("Tên nhân viên:");

        dateNSNV.setDateFormatString("dd-MM-yyyy");

        jLabel6.setText("Ngày sinh:");

        jLabel7.setText("Số điện thoại:");

        jLabel9.setText("CCCD:");

        cboCaLamNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"6H-12H","12H-17H","17H-22H"}));

        jLabel10.setText("Ca làm việc:");

        jLabel11.setText("Giới tính:");

        txtMaNV.setEditable(false);

        jLabel15.setText("Phường:");

        jLabel16.setText("Số nhà:");

        jLabel17.setText("Tên đường");

        jLabel18.setText("Mã địa chỉ:");

        jLabel19.setText("Quận:");

        jLabel20.setText("Thành phố:");

        txtMaDCNV.setEditable(false);

        btnSuaNV.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSuaNV.setText("Sửa nhân viên");
        btnSuaNV.setFocusable(false);
        btnSuaNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSuaNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNVActionPerformed(evt);
            }
        });

        btnXoaNV.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaNV.setText("Xóa nhân viên");
        btnXoaNV.setFocusable(false);
        btnXoaNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoaNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });

        btnLamMoiNV.setBackground(new java.awt.Color(0, 153, 255));
        btnLamMoiNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoiNV.setText("Làm mới");
        btnLamMoiNV.setFocusable(false);
        btnLamMoiNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoiNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLamMoiNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiNVActionPerformed(evt);
            }
        });

        btnThemNV.setBackground(new java.awt.Color(0, 153, 255));
        btnThemNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThemNV.setText("Thêm nhân viên");
        btnThemNV.setFocusable(false);
        btnThemNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThemNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoiNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(btnSuaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(btnXoaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addComponent(btnLamMoiNV, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setColumnHeaderView(null);
        jScrollPane2.setRowHeaderView(null);
        jScrollPane2.setViewportView(null);

        tableNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ nhân viên", "Tên nhân viên", "Ngày sinh", "Số điện thoại", "CCCD", "Ca làm việc", "Hệ số lương", "Giới tính", "Mã địa chỉ", "Số nhà", "Tên đường", "Phường", "Quận", "Thành phố"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableNV.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableNV.setShowHorizontalLines(true);
        tableNV.setShowVerticalLines(true);
        tableNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableNVMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableNV);
        if (tableNV.getColumnModel().getColumnCount() > 0) {
            tableNV.getColumnModel().getColumn(0).setMinWidth(100);
            tableNV.getColumnModel().getColumn(1).setMinWidth(100);
            tableNV.getColumnModel().getColumn(2).setMinWidth(100);
            tableNV.getColumnModel().getColumn(3).setMinWidth(100);
            tableNV.getColumnModel().getColumn(4).setMinWidth(100);
            tableNV.getColumnModel().getColumn(5).setMinWidth(100);
            tableNV.getColumnModel().getColumn(6).setMinWidth(100);
            tableNV.getColumnModel().getColumn(7).setMinWidth(100);
            tableNV.getColumnModel().getColumn(8).setMinWidth(100);
            tableNV.getColumnModel().getColumn(9).setMinWidth(100);
            tableNV.getColumnModel().getColumn(10).setMinWidth(100);
            tableNV.getColumnModel().getColumn(11).setMinWidth(100);
            tableNV.getColumnModel().getColumn(12).setMinWidth(100);
            tableNV.getColumnModel().getColumn(13).setMinWidth(100);
            tableNV.getColumnModel().getColumn(14).setMinWidth(100);
        }
        tableNV.getAccessibleContext().setAccessibleParent(jScrollPane2);

        jLabel23.setText("Hệ số lương:");

        buttonGroup2.add(rdoNVNu);
        rdoNVNu.setText("Nữ");

        buttonGroup2.add(rdoNVNam);
        rdoNVNam.setSelected(true);
        rdoNVNam.setText("Nam");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenNV)
                                    .addComponent(dateNSNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtHSLuongNV)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(rdoNVNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(rdoNVNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19)))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(30, 30, 30)
                                .addComponent(cboCaLamNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSDTNV)
                                    .addComponent(txtCCCDNV))))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                    .addComponent(txtHoNV)))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaDCNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel15)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtThanhPhoNV, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuanNV, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenDuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoNhaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaDCNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtHoNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(txtSoNhaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel17)
                                            .addComponent(txtTenDuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateNSNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPhuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSDTNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(txtQuanNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCCCDNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txtThanhPhoNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cboCaLamNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHSLuongNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(rdoNVNu)
                            .addComponent(rdoNVNam)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel21.setText("Nhập thông tin tìm kiếm:");

        jLabel22.setText("Tìm theo:");

        buttonGroup1.add(rdoTenNV);
        rdoTenNV.setText("Tên nhân viên");

        buttonGroup1.add(rdoMaNV);
        rdoMaNV.setText("Mã nhân viên");

        btnTimNV.setBackground(new java.awt.Color(0, 153, 255));
        btnTimNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimNV.setText("Tìm");
        btnTimNV.setFocusable(false);
        btnTimNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimNVActionPerformed(evt);
            }
        });

        btnThoatNV.setBackground(new java.awt.Color(0, 153, 255));
        btnThoatNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThoatNV.setText("Thoát");
        btnThoatNV.setFocusable(false);
        btnThoatNV.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThoatNV.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThoatNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatNVActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(rdoMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(rdoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtTimNV, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTimNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(btnThoatNV, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnTimNV, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnThoatNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(rdoTenNV)
                    .addComponent(rdoMaNV))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(btnXemThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnTimNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimNVActionPerformed
    	boolean luaChonMa = rdoMaNV.isSelected() ? true : false;
    	boolean luaChonTen = rdoTenNV.isSelected() ? true : false;
        if(luaChonMa == true) {
        	String ma = txtTimNV.getText().trim();
			int index = timKiemTheoMaNV(ma);
			if (index == -1) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy");
			} else {
				tableNV.setRowSelectionInterval(index, index);
				int row = tableNV.getSelectedRow();
				txtMaNV.setText(tableNV.getValueAt(row, 0).toString());
		        txtHoNV.setText(tableNV.getValueAt(row, 1).toString());
		        txtTenNV.setText(tableNV.getValueAt(row, 2).toString());
		        try {
		            Date ns = new SimpleDateFormat("dd-MM-yyyy").parse(tableNV.getValueAt(row, 3).toString());
		            dateNSNV.setDate(ns);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        txtSDTNV.setText(tableNV.getValueAt(row, 4).toString());
		        txtCCCDNV.setText(tableNV.getValueAt(row, 5).toString());
		        cboCaLamNV.setSelectedItem(tableNV.getValueAt(row, 6).toString());
		        txtHSLuongNV.setText(tableNV.getValueAt(row, 7).toString());
		        if(tableNV.getValueAt(row, 8).toString().equalsIgnoreCase("Nữ"))
		        	rdoNVNu.setSelected(true);
		        else
		        	rdoNVNam.setSelected(true);
		        txtMaDCNV.setText(tableNV.getValueAt(row, 9).toString());
		        txtSoNhaNV.setText(tableNV.getValueAt(row, 10).toString());
		        txtTenDuongNV.setText(tableNV.getValueAt(row, 11).toString());
		        txtPhuongNV.setText(tableNV.getValueAt(row, 12).toString());
		        txtQuanNV.setText(tableNV.getValueAt(row, 13).toString());
		        txtThanhPhoNV.setText(tableNV.getValueAt(row, 14).toString());
			}
        }
        else if(luaChonTen == true) {
        	String ten = txtTimNV.getText().trim();
			int index = timKiemTheoTenNV(ten);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableNV.getModel());
			tableNV.setRowSorter(sorter);
			if (index == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"));
				JOptionPane.showMessageDialog(null, "Không tìm thấy");
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + ten));
			}
        } 
        else {
        	JOptionPane.showMessageDialog(null, "Bạn cần lựa chọn cách tìm!");
        }
    }//GEN-LAST:event_btnTimNVActionPerformed

    private void btnThoatNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatNVActionPerformed
        // TODO add your handling code here:
        int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát không?","Chú ý",JOptionPane.YES_NO_OPTION);
		if(comfrim == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }//GEN-LAST:event_btnThoatNVActionPerformed

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

    private void tableNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNVMouseClicked
        int row = tableNV.getSelectedRow();
        txtMaNV.setText(tableNV.getValueAt(row, 0).toString());
        txtHoNV.setText(tableNV.getValueAt(row, 1).toString());
        txtTenNV.setText(tableNV.getValueAt(row, 2).toString());
        try {
            Date ns = new SimpleDateFormat("dd-MM-yyyy").parse(tableNV.getValueAt(row, 3).toString());
            dateNSNV.setDate(ns);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtSDTNV.setText(tableNV.getValueAt(row, 4).toString());
        txtCCCDNV.setText(tableNV.getValueAt(row, 5).toString());
        cboCaLamNV.setSelectedItem(tableNV.getValueAt(row, 6).toString());
        txtHSLuongNV.setText(tableNV.getValueAt(row, 7).toString());
        if(tableNV.getValueAt(row, 8).toString().equalsIgnoreCase("Nữ"))
        	rdoNVNu.setSelected(true);
        else
        	rdoNVNam.setSelected(true);
        txtMaDCNV.setText(tableNV.getValueAt(row, 9).toString());
        txtSoNhaNV.setText(tableNV.getValueAt(row, 10).toString());
        txtTenDuongNV.setText(tableNV.getValueAt(row, 11).toString());
        txtPhuongNV.setText(tableNV.getValueAt(row, 12).toString());
        txtQuanNV.setText(tableNV.getValueAt(row, 13).toString());
        txtThanhPhoNV.setText(tableNV.getValueAt(row, 14).toString());
    }//GEN-LAST:event_tableNVMouseClicked

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        // TODO add your handling code here:
        if(kiemTraThongTinNhapVao()) {
        	DiaChi dc = createDCForm();
        	NhanVien nv = createNhanVienForm();
    		if(getThongTinDiaChi(txtMaDCNV.getText().trim()) == null) {
    				if(getThongTinNhanVien(txtMaNV.getText().trim()) == null) {
    					dc_dao.createDiaChi(dc);
    		            nv_dao.createNhanVien(nv);
    		            DefaultTableModel tableModal = (DefaultTableModel) tableNV.getModel();
    		            tableModal.addRow(new Object[] {nv.getMaNV(),nv.getHo(),nv.getTen(),df.format(nv.getNgaySinh())
    							,nv.getSoDienThoai(),nv.getCccd(),nv.getCaLamViec(),nv.getHeSoLuong(),nv.getGioiTinh()
    							,dc.getMaDiaChi(),dc.getSoNha(),dc.getTenDuong(),dc.getPhuong(),dc.getQuan(),dc.getThanhPho()});
    					
    					txtMaDCNV.setText(createMaDC());
    					txtMaNV.setText(createMaNV());
    				} else {
    					txtMaNV.setText(createMaNV());		
    					JOptionPane.showMessageDialog(null, "Trùng mã nhân viên");
        				txtMaNV.requestFocus();
        				txtMaNV.selectAll();
    				}
    		} else {
    			txtMaDCNV.setText(createMaDC());
    			JOptionPane.showMessageDialog(null, "Trùng mã địa chỉ");
        		txtMaDCNV.requestFocus();
        		txtMaDCNV.selectAll();
    		}
    	}
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void btnLamMoiNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiNVActionPerformed
        txtMaNV.setText(createMaNV());
        txtHoNV.setText("");
        txtTenNV.setText("");
        txtSDTNV.setText("");
        txtCCCDNV.setText("");
        txtHSLuongNV.setText("");
        txtMaDCNV.setText(createMaDC());
        txtSoNhaNV.setText("");
        txtTenDuongNV.setText("");
        txtPhuongNV.setText("");
        txtQuanNV.setText("");
        txtThanhPhoNV.setText("");
    }//GEN-LAST:event_btnLamMoiNVActionPerformed

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        if(tableNV.getSelectedRowCount() > 0) {
            try {
                int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa không?","Chú ý",JOptionPane.YES_NO_OPTION);
                if(comfrim == JOptionPane.YES_OPTION) {
                    int r = tableNV.getSelectedRow();
                    DefaultTableModel modal = (DefaultTableModel) tableNV.getModel();
                    modal.removeRow(r); // xóa trong table model
                    String maNV = txtMaNV.getText().trim();
                    String maDC = txtMaDCNV.getText().trim();
                    nv_dao.DeleteNhanVien(maNV);
                    dc_dao.DeleteDiaChi(maDC);
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
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void btnSuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNVActionPerformed
        if(tableNV.getSelectedRow() != -1) {
            DiaChi dc = createDCForm();
            NhanVien nv = createNhanVienForm();
            if(dc_dao.updateDiaChi(dc) && nv_dao.updateNhanVien(nv)) {
                updateDataNV();
                btnLamMoiNVActionPerformed(evt);
                JOptionPane.showMessageDialog(null, "Sửa thành công");
            } else {
                btnLamMoiNVActionPerformed(evt);
                JOptionPane.showMessageDialog(null, "Sửa thất bại, bạn không thể sửa mã");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
        }
    }//GEN-LAST:event_btnSuaNVActionPerformed

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
            java.util.logging.Logger.getLogger(FrmNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoiNV;
    private javax.swing.JButton btnLapHoaDon;
    private javax.swing.JButton btnSuaNV;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnThoatNV;
    private javax.swing.JButton btnTimNV;
    private javax.swing.JButton btnXemHoaDon;
    private javax.swing.JButton btnXemKhachHang;
    private javax.swing.JButton btnXemNhanVien;
    private javax.swing.JButton btnXemThongKe;
    private javax.swing.JButton btnXemThuoc;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboCaLamNV;
    private com.toedter.calendar.JDateChooser dateNSNV;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
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
    private javax.swing.JMenu menuExit;
    private javax.swing.JRadioButton rdoMaNV;
    private javax.swing.JRadioButton rdoNVNam;
    private javax.swing.JRadioButton rdoNVNu;
    private javax.swing.JRadioButton rdoTenNV;
    private javax.swing.JTable tableNV;
    private javax.swing.JTextField txtCCCDNV;
    private javax.swing.JTextField txtHSLuongNV;
    private javax.swing.JTextField txtHoNV;
    private javax.swing.JTextField txtMaDCNV;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtPhuongNV;
    private javax.swing.JTextField txtQuanNV;
    private javax.swing.JTextField txtSDTNV;
    private javax.swing.JTextField txtSoNhaNV;
    private javax.swing.JTextField txtTenDuongNV;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThanhPhoNV;
    private javax.swing.JTextField txtTimNV;
    // End of variables declaration//GEN-END:variables
}
