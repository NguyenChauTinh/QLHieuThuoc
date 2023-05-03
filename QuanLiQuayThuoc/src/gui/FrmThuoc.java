package gui;

import java.security.cert.X509CRLEntry;
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
import dao.DiaChi_DAO;
import dao.DonViThuoc_DAO;
import dao.NhaCungCap_DAO;
import dao.Thuoc_DAO;
import entity.ChiTietHoaDon;
import entity.DiaChi;
import entity.DonViThuoc;
import entity.HoaDon;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;

import java.time.LocalDateTime;

public class FrmThuoc extends javax.swing.JFrame {
	private DonViThuoc_DAO dv_dao = new DonViThuoc_DAO();
	private Thuoc_DAO t_dao = new Thuoc_DAO();
	private NhaCungCap_DAO ncc_dao = new NhaCungCap_DAO();
	private DiaChi_DAO dc_dao = new DiaChi_DAO();
	private ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();

	Pattern regexTenNCC = Pattern.compile("^[a-zA-Z\\s]+$");
	Pattern regexSdtNCC = Pattern.compile("^[\\d]{10}$");
	Pattern regexTenThuoc = Pattern.compile("^([A-Za-z\\'\\.\\-\\d\\s]+)");
	Pattern regexSL = Pattern.compile("[0-9]+");
	Pattern regexGia = Pattern.compile("[0-9\\.]+");
	Pattern regexDC = Pattern.compile("^[a-zA-Z0-9\\s]+$");
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form FrmLapHoaDon
     */
    public FrmThuoc() {
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
        txtMaDC.setText(createMaDC());
        txtMaNCC.setText(createMaNCC());
        txtMaThuoc.setText(createMaThuoc());
		
        updateData();

    }
    
    public String createMaThuoc() {

//        String res1 = firstName.substring(0, 3);
//        String res2 = middleName.isEmpty() ? "0" : middleName.substring(0, 1);
//        String res3 = lastName.substring(0, 3);
//        String res4 = res1 + res2 + res3;
    	ArrayList<Thuoc> listThuoc = t_dao.getAlltbThuoc();
    	Integer count = 1;
    	Integer countNew = 0;
    
    	for(Thuoc t : listThuoc) {
    		countNew = Integer.parseInt(t.getMaThuoc().substring(2));
    		if(countNew > count) {
    			break;
    		}
    		if(countNew >= count) {
    			count = (++count);
    		}
    	}
//    	count = count + 1;
        String res5 = count.toString().length() == 1 ? ("MT0000" + count)
                : count.toString().length() == 2 ? ("MT000" + count) 
                : count.toString().length() == 3 ? ("MT00" + count)
                : count.toString().length() == 4 ? ("MT0" + count)
                : ("NV" + count);
        
        
//        String finalResult = res4 + res5;
        return res5;

    }
    public String createMaNCC() {
    	ArrayList<NhaCungCap> listNCC = ncc_dao.getAlltbNhaCungCap();
    	Integer count = 1;
    	Integer countNew = 0;
    	for(NhaCungCap ncc : listNCC) {
    		countNew = Integer.parseInt(ncc.getMaNCC().substring(3));
    		if(countNew > count) {
    			break;
    		}
    		if(countNew >= count) {
    			count = (++count);
    		}
    	}
//    	count = count + 1;
    	String res5 = count.toString().length() == 1 ? ("NCC0000" + count)
    			: count.toString().length() == 2 ? ("NCC000" + count) 
    					: count.toString().length() == 3 ? ("NCC00" + count)
    							: count.toString().length() == 4 ? ("NCC0" + count)
    									: ("NCC" + count);
    	return res5;
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
    public boolean kiemTraThongTinNhapVao() {
    	
    	if(txtTenThuoc.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên thuốc không được rỗng!");
    		txtTenThuoc.requestFocus();
    		txtTenThuoc.selectAll();
    		return false;
    		
    	}
    	if(!regexTenThuoc.matcher(txtTenThuoc.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên thuốc chỉ chứa ký tự là chữ");
    		txtTenThuoc.requestFocus();
    		txtTenThuoc.selectAll();
    		return false;
    	}
    	Date dateSX = dateNSX.getDate();
    	Date dateSD = dateHSD.getDate();
    	
    	if(dateSX == null) {
    		JOptionPane.showMessageDialog(null, "Ngày sản xuất không được rỗng!");
    		return false;
    		
    	}
    	if(dateSD == null) {
    		JOptionPane.showMessageDialog(null, "Hạn sử dụng không được rỗng!");
    		return false;
    		
    	}
    	if(dateSX.after(new Date())) {
    		JOptionPane.showMessageDialog(null, "Ngày sản xuất phải trước ngày hiện tại");
    		dateNSX.requestFocus();
    		return false;
    	}
    	if(dateSD.before(dateSX)) {
    		JOptionPane.showMessageDialog(null, "Hạn sử dụng phải sau ngày sản xuất");
    		dateHSD.requestFocus();
    		return false;
    	}
    	if(txtSoLuong.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(null, "Số lượng nhập không được rỗng");
    		txtSoLuong.requestFocus();
    		txtSoLuong.selectAll();
    		return false;
    	}
    	if(!regexSL.matcher(txtSoLuong.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, Số lượng nhập chỉ bao gồm chữ số và lớn hơn 0");
    		txtSoLuong.requestFocus();
    		txtSoLuong.selectAll();
    		return false;
    	}
    	if(Integer.parseInt(txtSoLuong.getText().trim()) <= 0) {
    		JOptionPane.showMessageDialog(null, "Số lượng nhập không được bé hơn hoặc bằng 0");
    		txtSoLuong.requestFocus();
    		txtSoLuong.selectAll();
    		return false;
    	}
    	if(txtGiaNhap.getText().trim().length()<=0) {
    		JOptionPane.showMessageDialog(null, "Đơn giá nhập không được rỗng");
    		txtGiaNhap.requestFocus();
    		txtGiaNhap.selectAll();
    		return false;
    	}
    	if(!regexGia.matcher(txtGiaNhap.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, đơn giá nhập chỉ bao gồm chữ số và lớn hơn 0");
    		txtGiaNhap.requestFocus();
    		txtGiaNhap.selectAll();
    		return false;
    	}
    	if(Double.parseDouble(txtGiaNhap.getText().trim()) <= 0) {
    		JOptionPane.showMessageDialog(null, "Đơn giá nhập không được bé hơn hoặc bằng 0");
    		txtGiaNhap.requestFocus();
    		txtGiaNhap.selectAll();
    		return false;
    	}
    	if(txtGiaBan.getText().trim().length()<=0) {
    		JOptionPane.showMessageDialog(null, "Đơn giá bán không được rỗng");
    		txtGiaBan.requestFocus();
    		txtGiaBan.selectAll();
    		return false;
    	}
    	if(!regexGia.matcher(txtGiaBan.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, đơn giá bán chỉ bao gồm chữ số và lớn hơn 0");
    		txtGiaBan.requestFocus();
    		txtGiaBan.selectAll();
    		return false;
    	}
    	if(Double.parseDouble(txtGiaNhap.getText().trim()) <= 0) {
    		JOptionPane.showMessageDialog(null, "Đơn giá bán không được bé hơn hoặc bằng 0");
    		txtGiaBan.requestFocus();
    		txtGiaBan.selectAll();
    		return false;
    	}
    	if(txtTenNCC.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên nhà cung cấp không được rỗng!");
    		return false;
    		
    	}
    	if(!regexTenNCC.matcher(txtTenNCC.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên nhà cung cấp chỉ chứa ký tự là chữ");
    		txtTenNCC.requestFocus();
    		txtTenNCC.selectAll();
    		return false;
    	}
    	if(txtSDT.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Số điện thoại nhà cung cấp không được rỗng!");
    		return false;
    		
    	}
    	if(!regexSdtNCC.matcher(txtSDT.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, số điện thoại nhà cung cấp chỉ chứa ký tự là số từ 0-9 và đủ 10 chữ số");
    		txtSDT.requestFocus();
    		txtSDT.selectAll();
    		return false;
    	}
    	if(txtSoNha.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Số nhà không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtSoNha.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, số nhà bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtSoNha.requestFocus();
    		txtSoNha.selectAll();
    		return false;
    	}
    	if(txtTenDuong.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên đường không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtTenDuong.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên đường bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtTenDuong.requestFocus();
    		txtTenDuong.selectAll();
    		return false;
    	}
    	if(txtPhuong.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên phường không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtPhuong.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên phường bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtPhuong.requestFocus();
    		txtPhuong.selectAll();
    		return false;
    	}
    	if(txtQuan.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên quận không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtQuan.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên quận bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtQuan.requestFocus();
    		txtQuan.selectAll();
    		return false;
    	}
    	if(txtThanhPho.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Tên thành phố không được rỗng!");
    		return false;
    		
    	}
    	if(!regexDC.matcher(txtThanhPho.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, tên thành phố bao gồm chữ hoặc số và cách nhau một hoặc nhiều khoảng trắng");
    		txtThanhPho.requestFocus();
    		txtThanhPho.selectAll();
    		return false;
    	}
    	return true;
    }
    
    public DiaChi createDCForm() {
    	String maDC = txtMaDC.getText().trim();
		String soNha = txtSoNha.getText().trim();
		String tenDuong = txtTenDuong.getText().trim();
		String phuong = txtPhuong.getText().trim();
		String quan = txtQuan.getText().trim();
		String thanhPho = txtThanhPho.getText().trim();
		
		DiaChi dc = new DiaChi(maDC, soNha, tenDuong, phuong, quan, thanhPho);
		return dc;
	}
    public NhaCungCap createNCCForm() {
    	
		String maNCC = txtMaNCC.getText().trim();
		String tenNCC = txtTenNCC.getText().trim();
		String sdt = txtSDT.getText().trim();
		String maDC = txtMaDC.getText().trim();
		DiaChi dc = new DiaChi(maDC);
		
		NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC, dc, sdt);
		return ncc;
	}
    public Thuoc createThuocForm() {
		String maThuoc = txtMaThuoc.getText().trim();
		String tenThuoc = txtTenThuoc.getText().trim();
		String maDv = cboDV.getSelectedItem().toString();
		DonViThuoc dvThuoc = new DonViThuoc(maDv);
		double donGiaNhap = Double.parseDouble(txtGiaNhap.getText().trim());
		Date hsd = dateHSD.getDate();
		String maNCC = txtMaNCC.getText().trim();
		NhaCungCap ncc = new NhaCungCap(maNCC);
		int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
		Date nsx = dateNSX.getDate();
		double donGiaBan = Double.parseDouble(txtGiaBan.getText().trim());
		
		Thuoc t = new Thuoc(maThuoc, tenThuoc, dvThuoc, donGiaNhap, hsd, ncc, soLuong, nsx, donGiaBan);
		return t;
	}
    
	public void deleteTable() {
		DefaultTableModel dm = (DefaultTableModel) tableThuoc.getModel();
		dm.getDataVector().removeAllElements();
	}
	public void updateData() {
		deleteTable();
		ArrayList<Thuoc> dst = t_dao.getAlltbThuoc();
		DefaultTableModel tableModal = (DefaultTableModel) tableThuoc.getModel();
		for(Thuoc t: dst) {
//			String tenDonViThuoc = getDVThuoc(t.getDvThuoc().getMaDonVi());
			NhaCungCap ncc = getThongTinNhaCungCap(t.getNhaCC().getMaNCC());
			DiaChi dc = getThongTinDiaChi(ncc.getDiaChi().getMaDiaChi());
			tableModal.addRow(new Object[] {t.getMaThuoc(),t.getTenThuoc(),df.format(t.getNgaySX()) ,df.format(t.getHanSuDung()) 
					,t.getSoLuongTon(),t.getDvThuoc().getMaDonVi(),t.getDonGiaNhap(),t.getDonGiaBan(),t.getNhaCC().getMaNCC()
					,ncc.getTenNCC(),ncc.getSoDienThoai()
					,dc.getMaDiaChi(),dc.getSoNha(),dc.getTenDuong(),dc.getPhuong(),dc.getQuan(),dc.getThanhPho()});
		
		}
		
	}
	
	public String getDVThuoc(String ma) {
    	ArrayList<DonViThuoc> listDV = dv_dao.getAlltbDonViThuoc();
    	for(DonViThuoc dvt : listDV) {
    		if(dvt.getMaDonVi().equalsIgnoreCase(ma)) {
    			String dvtSelect = dvt.getTenDonVi();
    			return dvtSelect;
    		}
    	}
    	return null;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNCC = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenThuoc = new javax.swing.JTextField();
        dateHSD = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dateNSX = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboDV = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtMaThuoc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTenNCC = new javax.swing.JTextField();
        txtTenDuong = new javax.swing.JTextField();
        txtSoNha = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtQuan = new javax.swing.JTextField();
        txtMaDC = new javax.swing.JTextField();
        txtThanhPho = new javax.swing.JTextField();
        txtPhuong = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableThuoc = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        rdoTen = new javax.swing.JRadioButton();
        rdoMa = new javax.swing.JRadioButton();
        btnTim = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
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
        jLabel2.setText("THUỐC");

        jLabel3.setText("Mã thuốc:");

        jLabel4.setText("Tên thuốc:");

        txtMaNCC.setEditable(false);

        jLabel5.setText("Ngày sản xuất:");

        dateHSD.setDateFormatString("dd-MM-yyyy");

        jLabel6.setText("Hạn sử dụng:");

        dateNSX.setDateFormatString("dd-MM-yyyy");

        jLabel7.setText("Số lượng nhập:");

        jLabel9.setText("Đơn vị thuốc:");

        cboDV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        jLabel10.setText("Đơn giá nhập:");

        jLabel11.setText("Đơn giá bán:");

        jLabel12.setText("Mã nhà cung cấp:");

        txtMaThuoc.setEditable(false);

        jLabel13.setText("Tên nhà cung cấp:");

        jLabel14.setText("Số điện thoại:");

        jLabel15.setText("Mã địa chỉ:");

        jLabel16.setText("Số nhà:");

        jLabel17.setText("Tên đường");

        jLabel18.setText("Phường:");

        jLabel19.setText("Quận:");

        jLabel20.setText("Thành phố:");

        txtMaDC.setEditable(false);

        btnSua.setBackground(new java.awt.Color(0, 153, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa thuốc");
        btnSua.setFocusable(false);
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 153, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa thuốc");
        btnXoa.setFocusable(false);
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(0, 153, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFocusable(false);
        btnLamMoi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 153, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm thuốc");
        btnThem.setFocusable(false);
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(53, 53, 53))
        );

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setColumnHeaderView(null);
        jScrollPane2.setRowHeaderView(null);
        jScrollPane2.setViewportView(null);

        tableThuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Ngày sản xuất", "Hạn sử dụng", "Số lượng nhập", "Đơn vị thuốc", "Đơn giá nhập", "Đơn giá bán", "Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Mã địa chỉ", "Số nhà", "Tên đường", "Phường", "Quận", "Thành phố"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableThuoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableThuoc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableThuoc.setShowHorizontalLines(true);
        tableThuoc.setShowVerticalLines(true);
        tableThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableThuocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableThuoc);
        if (tableThuoc.getColumnModel().getColumnCount() > 0) {
            tableThuoc.getColumnModel().getColumn(0).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(1).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(2).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(3).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(4).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(5).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(6).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(7).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(8).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(9).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(10).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(11).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(12).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(13).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(14).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(15).setMinWidth(100);
            tableThuoc.getColumnModel().getColumn(16).setMinWidth(100);
        }
        tableThuoc.getAccessibleContext().setAccessibleParent(jScrollPane2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dateNSX, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                    .addComponent(dateHSD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboDV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaNCC))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtThanhPho, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenDuong, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoNha, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaDC, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhuong, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(txtMaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)
                                            .addComponent(txtMaThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(txtTenThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13)
                                            .addComponent(txtTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel5))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel14)
                                                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(dateNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateHSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtMaDC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(txtSoNha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cboDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(txtTenDuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtPhuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtThanhPho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        ArrayList<DonViThuoc> dsDV = dv_dao.getAlltbDonViThuoc();
        for(DonViThuoc dv : dsDV) {
            cboDV.addItem(dv.getMaDonVi());
        }

        jLabel21.setText("Nhập thông tin tìm kiếm:");

        jLabel22.setText("Tìm theo:");

        buttonGroup1.add(rdoTen);
        rdoTen.setText("Tên thuốc");

        buttonGroup1.add(rdoMa);
        rdoMa.setText("Mã thuốc");

        btnTim.setBackground(new java.awt.Color(0, 153, 255));
        btnTim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTim.setText("Tìm");
        btnTim.setFocusable(false);
        btnTim.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTim.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        btnThoat.setBackground(new java.awt.Color(0, 153, 255));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.setFocusable(false);
        btnThoat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThoat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
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
                        .addComponent(rdoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(rdoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnTim, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(rdoTen)
                    .addComponent(rdoMa))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 919, Short.MAX_VALUE)))
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    	
    	
    	if(kiemTraThongTinNhapVao()) {
    		DiaChi dc = createDCForm();
        	NhaCungCap ncc = createNCCForm();
        	Thuoc t = createThuocForm();
    		if(getThongTinDiaChi(txtMaDC.getText()) == null) {
    			if(getThongTinNhaCungCap(txtMaNCC.getText()) == null) {
    				if(getThongTinThuoc(txtMaThuoc.getText()) == null) {
    					dc_dao.createDiaChi(dc);
    					ncc_dao.createNhaCungCap(ncc);
    					t_dao.createThuoc(t);
    					DefaultTableModel tableModal = (DefaultTableModel) tableThuoc.getModel();
    					tableModal.addRow(new Object[] {t.getMaThuoc(),t.getTenThuoc(),df.format(t.getNgaySX()),df.format(t.getHanSuDung()) 
    							,t.getSoLuongTon(),t.getDvThuoc().getMaDonVi(),t.getDonGiaNhap(),t.getDonGiaBan(),t.getNhaCC().getMaNCC()
    							,ncc.getTenNCC(),ncc.getSoDienThoai(),dc.getMaDiaChi(),dc.getSoNha(),dc.getTenDuong()
    							,dc.getPhuong(),dc.getQuan(),dc.getThanhPho()});
    					
    					txtMaDC.setText(createMaDC());
    					txtMaNCC.setText(createMaNCC());
    					txtMaThuoc.setText(createMaThuoc());
    				} else {
    					txtMaThuoc.setText(createMaThuoc());		
    					JOptionPane.showMessageDialog(null, "Trùng mã thuốc");
        				txtMaThuoc.requestFocus();
        				txtMaThuoc.selectAll();
    				}
    			} else {
    				txtMaNCC.setText(createMaNCC());
    				JOptionPane.showMessageDialog(null, "Trùng mã nhà cung cấp");
        			txtMaNCC.requestFocus();
        			txtMaNCC.selectAll();
    			}
    		} else {
    			txtMaDC.setText(createMaDC());
    			JOptionPane.showMessageDialog(null, "Trùng mã địa chỉ");
        		txtMaDC.requestFocus();
        		txtMaDC.selectAll();
    		}
    	}
		
			
    }//GEN-LAST:event_btnThemActionPerformed

    private void tableThuocMouseClicked(java.awt.event.MouseEvent evt)  {//GEN-FIRST:event_tableThuocMouseClicked
        int row = tableThuoc.getSelectedRow();
        txtMaThuoc.setText(tableThuoc.getValueAt(row, 0).toString());
        txtTenThuoc.setText(tableThuoc.getValueAt(row, 1).toString());
		try {
			Date nsx = new SimpleDateFormat("dd-MM-yyyy").parse(tableThuoc.getValueAt(row, 2).toString());
			Date hsd = new SimpleDateFormat("dd-MM-yyyy").parse(tableThuoc.getValueAt(row, 3).toString());
			dateNSX.setDate(nsx);
			dateHSD.setDate(hsd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txtSoLuong.setText(tableThuoc.getValueAt(row, 4).toString());
		cboDV.setSelectedItem(tableThuoc.getValueAt(row, 5).toString());
		txtGiaNhap.setText(tableThuoc.getValueAt(row, 6).toString());
		txtGiaBan.setText(tableThuoc.getValueAt(row, 7).toString());
		txtMaNCC.setText(tableThuoc.getValueAt(row, 8).toString());
		txtTenNCC.setText(tableThuoc.getValueAt(row, 9).toString());
		txtSDT.setText(tableThuoc.getValueAt(row, 10).toString());
		txtMaDC.setText(tableThuoc.getValueAt(row, 11).toString());
		txtSoNha.setText(tableThuoc.getValueAt(row, 12).toString());
		txtTenDuong.setText(tableThuoc.getValueAt(row, 13).toString());
		txtPhuong.setText(tableThuoc.getValueAt(row, 14).toString());
		txtQuan.setText(tableThuoc.getValueAt(row, 15).toString());
		txtThanhPho.setText(tableThuoc.getValueAt(row, 16).toString());
        
        
        
    }//GEN-LAST:event_tableThuocMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
    	if(tableThuoc.getSelectedRowCount() > 0) {
			try {
				int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa không?","Chú ý",JOptionPane.YES_NO_OPTION);
				if(comfrim == JOptionPane.YES_OPTION) {
					int r = tableThuoc.getSelectedRow();
					String maThuoc = txtMaThuoc.getText().trim();
					String maNCC = txtMaNCC.getText().trim();
					String maDC = txtMaDC.getText().trim();
					ArrayList<ChiTietHoaDon> listCTHD = cthd_dao.getAllChiTietHoaDons();
					for(ChiTietHoaDon cthd : listCTHD) {
						if(cthd.getMaThuoc().getMaThuoc().equals(maThuoc)) {
							cthd_dao.DeleteChiTietHoaDon(cthd.getCTHD());
						}
					}
					t_dao.DeleteThuoc(maThuoc);
					ncc_dao.DeleteNCC(maNCC);
					dc_dao.DeleteDiaChi(maDC);
					DefaultTableModel modal = (DefaultTableModel) tableThuoc.getModel();
					modal.removeRow(r); // xóa trong table model
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
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
    	if(tableThuoc.getSelectedRow() != -1) {
    		if(kiemTraThongTinNhapVao()) {
    			Thuoc t = createThuocForm(); 
    			DiaChi dc = createDCForm();
    			NhaCungCap ncc = createNCCForm();
    			if(dc_dao.updateDiaChi(dc) && ncc_dao.updateNCC(ncc) && t_dao.updateThuoc(t)) {
    				updateData();
    				btnLamMoiActionPerformed(evt);
    				JOptionPane.showMessageDialog(null, "Sửa thành công");
    			} else {
    				btnLamMoiActionPerformed(evt);
    				JOptionPane.showMessageDialog(null, "Sửa thất bại");
    			}	
    		}
    	} else {
    		JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
    	}
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
    	txtMaDC.setText(createMaDC());
        txtMaNCC.setText(createMaNCC());
        txtMaThuoc.setText(createMaThuoc());
        txtTenThuoc.setText("");
		txtSoLuong.setText("");
		txtGiaNhap.setText("");
		txtGiaBan.setText("");
		txtTenNCC.setText("");
		txtSDT.setText("");
		txtSoNha.setText("");
		txtTenDuong.setText("");
		txtPhuong.setText("");
		txtQuan.setText("");
		txtThanhPho.setText("");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
    	boolean luaChonMa = rdoMa.isSelected() ? true : false;
    	boolean luaChonTen = rdoTen.isSelected() ? true : false;
        if(luaChonMa == true) {
        	String ma = txtTim.getText().trim();
			int index = timKiemTheoMaThuoc(ma);
			if (index == -1) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy mã");
			} else {
				tableThuoc.setRowSelectionInterval(index, index);
				int row = tableThuoc.getSelectedRow();
		        txtMaThuoc.setText(tableThuoc.getValueAt(row, 0).toString());
		        txtTenThuoc.setText(tableThuoc.getValueAt(row, 1).toString());
				try {
					Date nsx = new SimpleDateFormat("dd-MM-yyyy").parse(tableThuoc.getValueAt(row, 2).toString());
					Date hsd = new SimpleDateFormat("dd-MM-yyyy").parse(tableThuoc.getValueAt(row, 3).toString());
					dateNSX.setDate(nsx);
					dateHSD.setDate(hsd);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				txtSoLuong.setText(tableThuoc.getValueAt(row, 4).toString());
				cboDV.setSelectedItem(tableThuoc.getValueAt(row, 5).toString());
				txtGiaNhap.setText(tableThuoc.getValueAt(row, 6).toString());
				txtGiaBan.setText(tableThuoc.getValueAt(row, 7).toString());
				txtMaNCC.setText(tableThuoc.getValueAt(row, 8).toString());
				txtTenNCC.setText(tableThuoc.getValueAt(row, 9).toString());
				txtSDT.setText(tableThuoc.getValueAt(row, 10).toString());
				txtMaDC.setText(tableThuoc.getValueAt(row, 11).toString());
				txtSoNha.setText(tableThuoc.getValueAt(row, 12).toString());
				txtTenDuong.setText(tableThuoc.getValueAt(row, 13).toString());
				txtPhuong.setText(tableThuoc.getValueAt(row, 14).toString());
				txtQuan.setText(tableThuoc.getValueAt(row, 15).toString());
				txtThanhPho.setText(tableThuoc.getValueAt(row, 16).toString());
			}
        }
        else if(luaChonTen == true) {
        	String ten = txtTim.getText().trim();
			int index = timKiemTheoTenThuoc(ten);
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableThuoc.getModel());
			tableThuoc.setRowSorter(sorter);
			if (index == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)"));
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + ten));
			}
        } 
        else {
        	JOptionPane.showMessageDialog(null, "Bạn cần lựa chọn cách tìm!");
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát không?","Chú ý",JOptionPane.YES_NO_OPTION);
		if(comfrim == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }//GEN-LAST:event_btnThoatActionPerformed

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
            java.util.logging.Logger.getLogger(FrmThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmThuoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmThuoc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLapHoaDon;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXemHoaDon;
    private javax.swing.JButton btnXemKhachHang;
    private javax.swing.JButton btnXemNhanVien;
    private javax.swing.JButton btnXemThongKe;
    private javax.swing.JButton btnXemThuoc;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboDV;
    private com.toedter.calendar.JDateChooser dateHSD;
    private com.toedter.calendar.JDateChooser dateNSX;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JMenu menuExit;
    private javax.swing.JRadioButton rdoMa;
    private javax.swing.JRadioButton rdoTen;
    private javax.swing.JTable tableThuoc;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaDC;
    private javax.swing.JTextField txtMaNCC;
    private javax.swing.JTextField txtMaThuoc;
    private javax.swing.JTextField txtPhuong;
    private javax.swing.JTextField txtQuan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoNha;
    private javax.swing.JTextField txtTenDuong;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTenThuoc;
    private javax.swing.JTextField txtThanhPho;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
