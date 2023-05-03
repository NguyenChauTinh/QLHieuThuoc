USE [QLHieuThuoc]
GO
/****** Object:  Table [dbo].[ChiTietHoaDon]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChiTietHoaDon](
	[maCTHD] [nvarchar](50) NOT NULL,
	[maHD] [nvarchar](50) NOT NULL,
	[maThuoc] [nvarchar](20) NOT NULL,
	[donGia] [float] NOT NULL,
	[soLuong] [int] NOT NULL,
	[thanhTien] [float] NOT NULL,
 CONSTRAINT [PK_ChiTietHoaDon] PRIMARY KEY CLUSTERED 
(
	[maCTHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DiaChi]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DiaChi](
	[maDiaChi] [nvarchar](20) NOT NULL,
	[soNha] [nvarchar](10) NOT NULL,
	[tenDuong] [nvarchar](30) NOT NULL,
	[phuong] [nvarchar](30) NOT NULL,
	[quan] [nvarchar](30) NOT NULL,
	[thanhPho] [nvarchar](30) NOT NULL,
 CONSTRAINT [PK_DiaChi] PRIMARY KEY CLUSTERED 
(
	[maDiaChi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DonViThuoc]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonViThuoc](
	[maDonVi] [nvarchar](20) NOT NULL,
	[tenDonVi] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_DonViThuoc] PRIMARY KEY CLUSTERED 
(
	[maDonVi] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[maHD] [nvarchar](50) NOT NULL,
	[maKH] [nvarchar](20) NOT NULL,
	[maNV] [nvarchar](20) NOT NULL,
	[ngayBan] [datetime] NOT NULL,
	[tongTien] [float] NOT NULL,
	[giamGia] [float] NOT NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[maHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[maKH] [nvarchar](20) NOT NULL,
	[hoKH] [nvarchar](20) NULL,
	[tenKH] [nvarchar](20) NULL,
	[gioiTinh] [nvarchar](20) NULL,
	[soDienThoai] [nvarchar](20) NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[maKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhaCungCap]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhaCungCap](
	[maNhaCungCap] [nvarchar](20) NOT NULL,
	[maDiaChi] [nvarchar](20) NOT NULL,
	[tenNhaCungCap] [nvarchar](50) NOT NULL,
	[soDienThoai] [nvarchar](20) NOT NULL,
 CONSTRAINT [PK_NhaCungCap] PRIMARY KEY CLUSTERED 
(
	[maNhaCungCap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNV] [nvarchar](20) NOT NULL,
	[maDiaChi] [nvarchar](20) NULL,
	[caLamViec] [nvarchar](20) NULL,
	[ten] [nvarchar](20) NULL,
	[ho] [nvarchar](20) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [nvarchar](10) NULL,
	[cccd] [nvarchar](20) NULL,
	[soDienThoai] [nvarchar](20) NULL,
	[heSoLuong] [float] NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[maNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[tenTK] [nvarchar](20) NOT NULL,
	[matKhau] [nvarchar](20) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Thuoc]    Script Date: 5/3/2023 11:20:01 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Thuoc](
	[maThuoc] [nvarchar](20) NOT NULL,
	[maDonVi] [nvarchar](20) NOT NULL,
	[maNhaCungCap] [nvarchar](20) NOT NULL,
	[tenThuoc] [nvarchar](30) NOT NULL,
	[donGiaThuoc] [float] NOT NULL,
	[hanSuDung] [datetime] NOT NULL,
	[soLuongTon] [int] NOT NULL,
	[ngaySX] [datetime] NOT NULL,
	[giaNhap] [float] NOT NULL,
 CONSTRAINT [PK_Thuoc] PRIMARY KEY CLUSTERED 
(
	[maThuoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00001', N'HD00001', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00002', N'HD00002', N'MT00001', 500, 123, 61500)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00003', N'HD00001', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00004', N'HD00004', N'MT00001', 500, 10, 5000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00005', N'HD00004', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00006', N'HD00001', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00007', N'HD00004', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00008', N'HD00003', N'MT00005', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00009', N'HD00003', N'MT00005', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00010', N'HD00003', N'MT00005', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00011', N'HD00003', N'MT00005', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00012', N'HD00004', N'MT00001', 500, 100, 50000)
GO
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00001', N'142 24', N'Le Loi', N'4', N'Go Vap', N'HCM')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00002', N'142 24', N'Le Loi', N'4', N'Go Vap', N'HCM')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00003', N'1', N'32', N'123', N'123', N'123')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00004', N'45645', N'4', N'45', N'5', N'64')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00005', N'45645786', N'486', N'45786', N'578', N'64786')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00007', N'142 24', N'Le Loi', N'4', N'Go Vap', N'HCM')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00008', N'142 24', N'Le Loi', N'4', N'Go Vap', N'HCM')
GO
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVBot', N'Bột')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVChai', N'Chai')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVLo', N'Lọ')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVNuoc', N'Nước')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVVien', N'Viên')
GO
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00001', N'KH00001', N'NV00001', CAST(N'2023-05-02T00:00:00.000' AS DateTime), 135000, 10)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00002', N'KH00004', N'NV00001', CAST(N'2023-05-01T00:00:00.000' AS DateTime), 49200, 20)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00003', N'KH00003', N'NV00002', CAST(N'2023-05-02T00:00:00.000' AS DateTime), 100000, 50)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00004', N'KH00005', N'NV00002', CAST(N'2023-05-02T00:00:00.000' AS DateTime), 139500, 10)
GO
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00001', N'ABC', N'ABC', N'Nữ', N'123')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00002', N'B', N'B', N'Nam', N'3324')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00003', N'123', N'123', N'Nam', N'123')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00004', N'123', N'123', N'Nữ', N'123')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00005', N'Nguyen Chau', N'Tinh', N'Nam', N'1234567911')
GO
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00001', N'DC00001', N'Vinphaco', N'1234567890')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00002', N'DC00002', N'Vinphaco', N'1234567890')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00005', N'DC00007', N'Vinphaco', N'1234567890')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00006', N'DC00008', N'Vinphaco', N'1234567890')
GO
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00001', N'DC00004', N'12H-17H', N'etre', N'ABC', CAST(N'2023-04-04' AS Date), N'Nam', N'456', N'4', 456)
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00002', N'DC00005', N'6H-12H', N'etrert', N'rtyr', CAST(N'2023-04-05' AS Date), N'Nam', N'45678', N'467', 456)
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00003', N'DC00003', N'6H-12H', N'a', N'a', CAST(N'2002-05-02' AS Date), N'', N'012345678912', N'1234567891', 12)
GO
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00001', N'DVChai', N'NCC00001', N'Atropin sulfat', 500, CAST(N'2023-04-20T00:00:00.000' AS DateTime), 10, CAST(N'2023-04-02T00:00:00.000' AS DateTime), 500)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00002', N'DVChai', N'NCC00002', N'Atropin sulfat', 500, CAST(N'2023-04-20T00:00:00.000' AS DateTime), 10, CAST(N'2023-04-02T00:00:00.000' AS DateTime), 500)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00005', N'DVChai', N'NCC00005', N'Atropin sulfat', 500, CAST(N'2023-04-20T00:00:00.000' AS DateTime), 10, CAST(N'2023-04-02T00:00:00.000' AS DateTime), 500)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00006', N'DVChai', N'NCC00006', N'Atropin sulfat', 500, CAST(N'2023-04-20T00:00:00.000' AS DateTime), 10, CAST(N'2023-04-02T00:00:00.000' AS DateTime), 500)
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_HoaDon] FOREIGN KEY([maHD])
REFERENCES [dbo].[HoaDon] ([maHD])
GO
ALTER TABLE [dbo].[ChiTietHoaDon] CHECK CONSTRAINT [FK_ChiTietHoaDon_HoaDon]
GO
ALTER TABLE [dbo].[ChiTietHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_ChiTietHoaDon_Thuoc] FOREIGN KEY([maThuoc])
REFERENCES [dbo].[Thuoc] ([maThuoc])
GO
ALTER TABLE [dbo].[ChiTietHoaDon] CHECK CONSTRAINT [FK_ChiTietHoaDon_Thuoc]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KhachHang] ([maKH])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien]
GO
ALTER TABLE [dbo].[NhaCungCap]  WITH CHECK ADD  CONSTRAINT [FK_NhaCungCap_DiaChi] FOREIGN KEY([maDiaChi])
REFERENCES [dbo].[DiaChi] ([maDiaChi])
GO
ALTER TABLE [dbo].[NhaCungCap] CHECK CONSTRAINT [FK_NhaCungCap_DiaChi]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FK_NhanVien_DiaChi] FOREIGN KEY([maDiaChi])
REFERENCES [dbo].[DiaChi] ([maDiaChi])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FK_NhanVien_DiaChi]
GO
ALTER TABLE [dbo].[Thuoc]  WITH CHECK ADD  CONSTRAINT [FK_Thuoc_DonViThuoc] FOREIGN KEY([maDonVi])
REFERENCES [dbo].[DonViThuoc] ([maDonVi])
GO
ALTER TABLE [dbo].[Thuoc] CHECK CONSTRAINT [FK_Thuoc_DonViThuoc]
GO
ALTER TABLE [dbo].[Thuoc]  WITH CHECK ADD  CONSTRAINT [FK_Thuoc_NhaCungCap] FOREIGN KEY([maNhaCungCap])
REFERENCES [dbo].[NhaCungCap] ([maNhaCungCap])
GO
ALTER TABLE [dbo].[Thuoc] CHECK CONSTRAINT [FK_Thuoc_NhaCungCap]
GO
