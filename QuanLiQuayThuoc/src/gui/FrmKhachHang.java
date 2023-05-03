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
import dao.DiaChi_DAO;
import dao.DonViThuoc_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhaCungCap_DAO;
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

public class FrmKhachHang extends javax.swing.JFrame {
	private KhachHang_DAO kh_dao = new KhachHang_DAO();
	private ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_DAO();
	private HoaDon_DAO hd_dao = new HoaDon_DAO();
	
	Pattern regexHo = Pattern.compile("^[a-zA-Z\\s]+$");
	Pattern regexTen = Pattern.compile("^([A-Za-z\\'\\.\\-\\d\\s]+)");
	Pattern regexSDT = Pattern.compile("^[\\d]{10}$");
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form FrmLapHoaDon
     */
    public FrmKhachHang() {
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
        updateDataKH();
    }
        
    
    public boolean kiemTraThongTinNhapVao() {
    	if(txtHoKH.getText().trim().length() <= 0) {
    		JOptionPane.showMessageDialog(null, "Họ khách hàng không được rỗng!");
    		txtHoKH.requestFocus();
    		txtHoKH.selectAll();
    		return false;
    		
    	}
    	if(!regexHo.matcher(txtHoKH.getText()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, Họ khách hàng chỉ chứa ký tự là chữ");
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
    	if(!regexSDT.matcher(txtSDTKH.getText().trim()).matches()) {
    		JOptionPane.showMessageDialog(null, "Sai cú pháp, số điện thoại khách hàng chỉ chứa ký tự là số từ 0-9 và đủ 10 chữ số");
    		txtSDTKH.requestFocus();
    		txtSDTKH.selectAll();
    		return false;
    	}
    	return true;
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
    	DefaultTableModel dm = (DefaultTableModel) tableKH.getModel();
    	dm.getDataVector().removeAllElements();
    }
    
    public void updateDataKH() {
    	deleteTable();
    	ArrayList<KhachHang> dskh = kh_dao.getAllKhachHang();
    	DefaultTableModel tableModal = (DefaultTableModel) tableKH.getModel();
    	for(KhachHang kh: dskh) {
    		tableModal.addRow(new Object[] {kh.getMaKH(),kh.getHoKH(),kh.getTenKH(),kh.getGioiTinh(),kh.getSoDienThoai()});
    	}
    }
    
    public int timKiemTheoMaKH(String ma) {
    	ArrayList<KhachHang> listKH = kh_dao.getAllKhachHang();
    	for(KhachHang kh : listKH) {
    		if(kh.getMaKH().equalsIgnoreCase(ma))
    			return listKH.indexOf(kh);
    	}
    	return -1;
    }
    
    public int timKiemTheoTenKH(String ma) {
    	ArrayList<KhachHang> listKH = kh_dao.getAllKhachHang();
    	for(KhachHang kh : listKH) {
    		if(kh.getTenKH().equalsIgnoreCase(ma))
    			return listKH.indexOf(kh);
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
        MaKH = new javax.swing.JLabel();
        HoKH = new javax.swing.JLabel();
        TenKH = new javax.swing.JLabel();
        txtHoKH = new javax.swing.JTextField();
        SDTKH = new javax.swing.JLabel();
        txtSDTKH = new javax.swing.JTextField();
        GioiTinhKH = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableKH = new javax.swing.JTable();
        txtTenKH = new javax.swing.JTextField();
        rdoKHNu = new javax.swing.JRadioButton();
        rdoKHNam = new javax.swing.JRadioButton();
        btnLamMoiKH = new javax.swing.JButton();
        btnSuaKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtTimKH = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        rdoTenKH = new javax.swing.JRadioButton();
        rdoMaKH = new javax.swing.JRadioButton();
        btnTimKH = new javax.swing.JButton();
        btnThoatKH = new javax.swing.JButton();
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
        jLabel2.setText("KHÁCH HÀNG");

        MaKH.setText("Mã khách hàng:");

        HoKH.setText("Họ khách hàng:");

        TenKH.setText("Tên khách hàng:");

        SDTKH.setText("Số điện thoại:");

        GioiTinhKH.setText("Giới tính:");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setColumnHeaderView(null);
        jScrollPane2.setRowHeaderView(null);
        jScrollPane2.setViewportView(null);

        tableKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Họ khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKH.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableKH.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableKH.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableKH.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableKH.setShowHorizontalLines(true);
        tableKH.setShowVerticalLines(true);
        tableKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableKHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableKH);
        if (tableKH.getColumnModel().getColumnCount() > 0) {
            tableKH.getColumnModel().getColumn(0).setMinWidth(100);
            tableKH.getColumnModel().getColumn(1).setMinWidth(100);
            tableKH.getColumnModel().getColumn(2).setMinWidth(100);
            tableKH.getColumnModel().getColumn(3).setMinWidth(100);
            tableKH.getColumnModel().getColumn(4).setMinWidth(100);
        }
        tableKH.getAccessibleContext().setAccessibleParent(jScrollPane2);

        buttonGroup2.add(rdoKHNu);
        rdoKHNu.setText("Nữ");

        buttonGroup2.add(rdoKHNam);
        rdoKHNam.setSelected(true);
        rdoKHNam.setText("Nam");

        btnLamMoiKH.setBackground(new java.awt.Color(0, 153, 255));
        btnLamMoiKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLamMoiKH.setText("Làm mới");
        btnLamMoiKH.setFocusable(false);
        btnLamMoiKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoiKH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLamMoiKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiKHActionPerformed(evt);
            }
        });

        btnSuaKH.setBackground(new java.awt.Color(0, 153, 255));
        btnSuaKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSuaKH.setText("Sửa khách hàng");
        btnSuaKH.setFocusable(false);
        btnSuaKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSuaKH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKHActionPerformed(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(0, 153, 255));
        btnXoaKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoaKH.setText("Xóa khách hàng");
        btnXoaKH.setFocusable(false);
        btnXoaKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnXoaKH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(HoKH)
                                    .addComponent(MaKH))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaKH, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                    .addComponent(txtHoKH)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(TenKH)
                                .addGap(18, 18, 18)
                                .addComponent(txtTenKH)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(GioiTinhKH)
                                    .addComponent(SDTKH))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(rdoKHNu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                        .addComponent(rdoKHNam, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(btnSuaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLamMoiKH, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MaKH)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GioiTinhKH)
                            .addComponent(rdoKHNu)
                            .addComponent(rdoKHNam))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HoKH)
                            .addComponent(txtHoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SDTKH)
                            .addComponent(txtSDTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TenKH)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnXoaKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoiKH)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel21.setText("Nhập thông tin tìm kiếm:");

        jLabel22.setText("Tìm theo:");

        buttonGroup1.add(rdoTenKH);
        rdoTenKH.setText("Tên khách hàng");

        buttonGroup1.add(rdoMaKH);
        rdoMaKH.setText("Mã khách hàng");

        btnTimKH.setBackground(new java.awt.Color(0, 153, 255));
        btnTimKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKH.setText("Tìm");
        btnTimKH.setFocusable(false);
        btnTimKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimKH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKHActionPerformed(evt);
            }
        });

        btnThoatKH.setBackground(new java.awt.Color(0, 153, 255));
        btnThoatKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThoatKH.setText("Thoát");
        btnThoatKH.setFocusable(false);
        btnThoatKH.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThoatKH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThoatKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatKHActionPerformed(evt);
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
                        .addComponent(rdoMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(rdoTenKH)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(btnThoatKH, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnTimKH, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnThoatKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(rdoTenKH)
                    .addComponent(rdoMaKH))
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(402, 402, 402)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnTimKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKHActionPerformed
    	boolean luaChonMa = rdoMaKH.isSelected() ? true : false;
    	boolean luaChonTen = rdoTenKH.isSelected() ? true : false;
        if(luaChonMa == true) {
        	String ma = txtTimKH.getText().trim();
			int index = timKiemTheoMaKH(ma);
			if (index == -1) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy");
			} else {
				tableKH.setRowSelectionInterval(index, index);
				int row = tableKH.getSelectedRow();
		        txtMaKH.setText(tableKH.getValueAt(row, 0).toString());
		        txtHoKH.setText(tableKH.getValueAt(row, 1).toString());
		        txtTenKH.setText(tableKH.getValueAt(row, 2).toString());
		        if(tableKH.getValueAt(row, 3).toString().equalsIgnoreCase("Nữ"))
		        	rdoKHNu.setSelected(true);
		        else
		        	rdoKHNam.setSelected(true);
				txtSDTKH.setText(tableKH.getValueAt(row, 4).toString());
			}
        }
        else if(luaChonTen == true) {
        	String ten = txtTimKH.getText().trim();
        	TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableKH.getModel());
        	tableKH.setRowSorter(sorter);
			int index = timKiemTheoTenKH(ten);
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
    }//GEN-LAST:event_btnTimKHActionPerformed

    private void btnThoatKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatKHActionPerformed
        // TODO add your handling code here:
        int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn thoát không?","Chú ý",JOptionPane.YES_NO_OPTION);
		if(comfrim == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
    }//GEN-LAST:event_btnThoatKHActionPerformed

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

    private void tableKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableKHMouseClicked
    	int row = tableKH.getSelectedRow();
    	txtMaKH.setText(tableKH.getValueAt(row, 0).toString());
        txtHoKH.setText(tableKH.getValueAt(row, 1).toString());
        txtTenKH.setText(tableKH.getValueAt(row, 2).toString());
        if(tableKH.getValueAt(row, 3).toString().equalsIgnoreCase("Nữ"))
        	rdoKHNu.setSelected(true);
        else
        	rdoKHNam.setSelected(true);
		txtSDTKH.setText(tableKH.getValueAt(row, 4).toString());
    }//GEN-LAST:event_tableKHMouseClicked

    private void btnLamMoiKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiKHActionPerformed
        txtMaKH.setText("");
        txtHoKH.setText("");
        txtTenKH.setText("");
        txtSDTKH.setText("");
    }//GEN-LAST:event_btnLamMoiKHActionPerformed

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
    	if(tableKH.getSelectedRow() != -1) {
            KhachHang kh = createKhachHangForm();
            if(kh_dao.updateKhachHang(kh)) {
                updateDataKH();
                btnLamMoiKHActionPerformed(evt);
                JOptionPane.showMessageDialog(null, "Sửa thành công");
            } else {
                btnLamMoiKHActionPerformed(evt);
                JOptionPane.showMessageDialog(null, "Sửa thất bại, bạn không thể sửa mã");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn cần chọn dòng cần sửa");
        }
    }//GEN-LAST:event_btnSuaKHActionPerformed

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

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
    	if(tableKH.getSelectedRowCount() > 0) {
            try {
                int comfrim = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa không?","Chú ý",JOptionPane.YES_NO_OPTION);
                if(comfrim == JOptionPane.YES_OPTION) {
                    int r = tableKH.getSelectedRow();
                    DefaultTableModel modal = (DefaultTableModel) tableKH.getModel();
                    modal.removeRow(r); // xóa trong table model
                    String maKH = txtMaKH.getText().trim();
                    ArrayList<ChiTietHoaDon> listCTHD = cthd_dao.getAllChiTietHoaDons();
                    ArrayList<HoaDon> listHD = hd_dao.getAllHoaDons();
                    for(HoaDon hd : listHD) {
                    	if(hd.getKhachHang().getMaKH().equals(maKH)) {
                    		for(ChiTietHoaDon cthd : listCTHD) {
                    			if(cthd.getMaHD().getMaHD().equals(hd.getMaHD())) {
                    				cthd_dao.DeleteChiTietHoaDon(cthd.getCTHD());
                    			}
                    		}
                    		hd_dao.DeleteHoaDon(hd.getMaHD());
                    		break;
                    	}
                    }
                    kh_dao.DeleteKhachHang(maKH);
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
    }//GEN-LAST:event_btnXoaKHActionPerformed

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
            java.util.logging.Logger.getLogger(FrmKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmKhachHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GioiTinhKH;
    private javax.swing.JLabel HoKH;
    private javax.swing.JLabel MaKH;
    private javax.swing.JLabel SDTKH;
    private javax.swing.JLabel TenKH;
    private javax.swing.JButton btnLamMoiKH;
    private javax.swing.JButton btnLapHoaDon;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnThoatKH;
    private javax.swing.JButton btnTimKH;
    private javax.swing.JButton btnXemHoaDon;
    private javax.swing.JButton btnXemKhachHang;
    private javax.swing.JButton btnXemNhanVien;
    private javax.swing.JButton btnXemThongKe;
    private javax.swing.JButton btnXemThuoc;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu menuExit;
    private javax.swing.JRadioButton rdoKHNam;
    private javax.swing.JRadioButton rdoKHNu;
    private javax.swing.JRadioButton rdoMaKH;
    private javax.swing.JRadioButton rdoTenKH;
    private javax.swing.JTable tableKH;
    private javax.swing.JTextField txtHoKH;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDTKH;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKH;
    // End of variables declaration//GEN-END:variables
}
