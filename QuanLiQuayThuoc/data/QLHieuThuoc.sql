CREATE DATABASE QLHieuThuoc
GO
USE [QLHieuThuoc]
GO
/****** Object:  Table [dbo].[ChiTietHoaDon]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[DiaChi]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[DonViThuoc]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[HoaDon]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[KhachHang]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[NhaCungCap]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[NhanVien]    Script Date: 04/05/2023 4:24:41 CH ******/
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
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 04/05/2023 4:24:41 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[tenTK] [nvarchar](20) NOT NULL,
	[matKhau] [nvarchar](20) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Thuoc]    Script Date: 04/05/2023 4:24:41 CH ******/
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
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00001', N'HD00001', N'MT00004', 3500, 2, 7000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00002', N'HD00002', N'MT00003', 3000, 5, 15000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00003', N'HD00002', N'MT00003', 3000, 5, 15000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00004', N'HD00004', N'MT00001', 500, 10, 5000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00005', N'HD00004', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00006', N'HD00003', N'MT00003', 3000, 3, 9000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00007', N'HD00004', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00008', N'HD00003', N'MT00005', 2000, 3, 6000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00009', N'HD00003', N'MT00006', 1000, 3, 3000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00010', N'HD00005', N'MT00001', 500, 3, 1500)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00011', N'HD00005', N'MT00007', 5000, 3, 15000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00012', N'HD00004', N'MT00001', 500, 100, 50000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00013', N'HD00005', N'MT00005', 2000, 3, 6000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00014', N'HD00005', N'MT00009', 30000, 3, 90000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00015', N'HD00005', N'MT00010', 50000, 3, 150000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00016', N'HD00005', N'MT00008', 25000, 3, 75000)
INSERT [dbo].[ChiTietHoaDon] ([maCTHD], [maHD], [maThuoc], [donGia], [soLuong], [thanhTien]) VALUES (N'CTHD00017', N'HD00005', N'MT00002', 4000, 3, 12000)
GO
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00001', N'142 24', N'Le Loi', N'4', N'Go Vap', N'HCM')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00002', N'1450', N'Le Thi Hong', N'4', N'Go Vap', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00003', N'201', N'Nguyen Thai Son', N'7', N'Go Vap', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00005', N'12', N'Au Co', N'11', N'Tan Phu', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00006', N'1450', N'Le Thi Hong', N'4', N'Go Vap', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00007', N'1388', N'Pham Van Dong', N'11', N'Cai Be', N'Hau Giang')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00008', N'142 24', N'Le Hong Phong', N'10', N'1', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00009', N'150', N'Nguyen Hue', N'Vuon Lai', N'12', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00010', N'117', N'Cach Mang Thang Tam', N'10', N'3', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00011', N'1470', N'Le Duc Tho', N'7', N'Go Vap', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00012', N'139', N'Le Trong Tan', N'3', N'Tan Phu', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00013', N'1490', N'Cao Lo', N'12', N'8', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00014', N'145', N'Tay Thanh', N'3', N'Binh Chanh', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00015', N'30', N'Nguyen Van Khoi', N'11', N'Go Vap', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00016', N'124', N'Le Loi', N'7', N'Go Vap', N'Ho Chi Minh')
INSERT [dbo].[DiaChi] ([maDiaChi], [soNha], [tenDuong], [phuong], [quan], [thanhPho]) VALUES (N'DC00017', N'24', N'Le Thi Hong', N'4', N'Go Vap', N'Ho Chi Minh')
GO
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVBot', N'Bột')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVChai', N'Chai')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVLo', N'Lọ')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVNuoc', N'Nước')
INSERT [dbo].[DonViThuoc] ([maDonVi], [tenDonVi]) VALUES (N'DVVien', N'Viên')
GO
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00001', N'KH00001', N'NV00002', CAST(N'2023-05-04T00:00:00.000' AS DateTime), 7000, 0)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00002', N'KH00002', N'NV00002', CAST(N'2023-05-04T00:00:00.000' AS DateTime), 21000, 30)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00003', N'KH00003', N'NV00004', CAST(N'2023-05-04T00:00:00.000' AS DateTime), 18000, 0)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00004', N'KH00005', N'NV00002', CAST(N'2023-05-02T00:00:00.000' AS DateTime), 139500, 10)
INSERT [dbo].[HoaDon] ([maHD], [maKH], [maNV], [ngayBan], [tongTien], [giamGia]) VALUES (N'HD00005', N'KH00004', N'NV00004', CAST(N'2023-05-04T00:00:00.000' AS DateTime), 349500, 0)
GO
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00001', N'Bui', N'Trung', N'Nam', N'0367829617')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00002', N'Nguyen', N'Khai', N'Nam', N'0977381726')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00003', N'Phan', N'Quan', N'Nam', N'0365782728')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00004', N'Nguyen', N'Van An', N'Nam', N'0365702839')
INSERT [dbo].[KhachHang] ([maKH], [hoKH], [tenKH], [gioiTinh], [soDienThoai]) VALUES (N'KH00005', N'Nguyen Chau', N'Tinh', N'Nam', N'1234567911')
GO
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00001', N'DC00001', N'Vinphaco', N'1234567890')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00002', N'DC00002', N'DAVIPHARM', N'0365785758')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00003', N'DC00006', N'DAVIPHARM', N'0365784748')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00004', N'DC00007', N'CTY CP Duoc Hau Giang', N'0376893847')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00005', N'DC00009', N'CTY CP Duoc Dat Vi Phu', N'0977881414')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00006', N'DC00008', N'Pharmaceutical', N'1234567890')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00007', N'DC00010', N'CTY CP Duoc VTYT Ha Nam', N'0914771871')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00008', N'DC00011', N'CTY CP Duoc Pham Ha Tay', N'0913778998')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00009', N'DC00012', N'Phil Inter Phamar', N'0977881551')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00010', N'DC00013', N'Glaxo Operations UK', N'0365793728')
INSERT [dbo].[NhaCungCap] ([maNhaCungCap], [maDiaChi], [tenNhaCungCap], [soDienThoai]) VALUES (N'NCC00011', N'DC00014', N'OM Pharma', N'0913778445')
GO
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00002', N'DC00005', N'6H-12H', N'Ky Duyen', N'Nguyen', CAST(N'2002-04-16' AS Date), N'Nam', N'031238903654', N'0365782671', 3)
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00003', N'DC00003', N'6H-12H', N'Truong Tuan', N'Phan', CAST(N'2002-10-20' AS Date), N'Nam', N'054789903854', N'0365784748', 3)
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00004', N'DC00015', N'12H-17H', N'Hoang Anh', N'Truong', CAST(N'2002-09-14' AS Date), N'Nam', N'036723987312', N'0356873937', 2.5)
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00005', N'DC00016', N'17H-22H', N'Chau Tinh', N'Nguyen', CAST(N'2002-07-02' AS Date), N'Nam', N'048927329022', N'0367821933', 3)
INSERT [dbo].[NhanVien] ([maNV], [maDiaChi], [caLamViec], [ten], [ho], [ngaySinh], [gioiTinh], [cccd], [soDienThoai], [heSoLuong]) VALUES (N'NV00006', N'DC00017', N'6H-12H', N'Thanh Tuyen', N'Nguyen', CAST(N'2002-10-15' AS Date), N'Nữ', N'031287469022', N'0365782719', 3)
GO
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00001', N'DVChai', N'NCC00001', N'Atropin sulfat', 500, CAST(N'2023-04-20T00:00:00.000' AS DateTime), 10, CAST(N'2023-04-02T00:00:00.000' AS DateTime), 500)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00002', N'DVVien', N'NCC00002', N'Courtois', 4000, CAST(N'2024-06-05T00:00:00.000' AS DateTime), 200, CAST(N'2020-06-05T00:00:00.000' AS DateTime), 2000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00003', N'DVVien', N'NCC00003', N'Risenate', 3000, CAST(N'2024-06-05T00:00:00.000' AS DateTime), 150, CAST(N'2020-06-05T00:00:00.000' AS DateTime), 1500)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00004', N'DVVien', N'NCC00004', N'Lefvox', 3500, CAST(N'2023-06-01T00:00:00.000' AS DateTime), 100, CAST(N'2021-06-01T00:00:00.000' AS DateTime), 2000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00005', N'DVVien', N'NCC00005', N'Glumeform 750 XR', 2000, CAST(N'2024-05-11T00:00:00.000' AS DateTime), 300, CAST(N'2021-05-20T00:00:00.000' AS DateTime), 1000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00006', N'DVVien', N'NCC00006', N'Pamlonor', 1000, CAST(N'2024-04-12T00:00:00.000' AS DateTime), 100, CAST(N'2022-04-12T00:00:00.000' AS DateTime), 1000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00007', N'DVVien', N'NCC00007', N'Olexon S', 5000, CAST(N'2023-12-12T00:00:00.000' AS DateTime), 200, CAST(N'2020-12-12T00:00:00.000' AS DateTime), 3000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00008', N'DVChai', N'NCC00008', N'Babysolvan', 25000, CAST(N'2024-08-04T00:00:00.000' AS DateTime), 200, CAST(N'2020-08-04T00:00:00.000' AS DateTime), 15000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00009', N'DVNuoc', N'NCC00009', N'Ajuakinol', 30000, CAST(N'2024-08-04T00:00:00.000' AS DateTime), 150, CAST(N'2020-08-04T00:00:00.000' AS DateTime), 20000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00010', N'DVLo', N'NCC00010', N'Eumovate', 50000, CAST(N'2024-10-14T00:00:00.000' AS DateTime), 99, CAST(N'2020-10-14T00:00:00.000' AS DateTime), 30000)
INSERT [dbo].[Thuoc] ([maThuoc], [maDonVi], [maNhaCungCap], [tenThuoc], [donGiaThuoc], [hanSuDung], [soLuongTon], [ngaySX], [giaNhap]) VALUES (N'MT00011', N'DVVien', N'NCC00011', N'Klacid MR', 4000, CAST(N'2024-04-13T00:00:00.000' AS DateTime), 999, CAST(N'2021-04-13T00:00:00.000' AS DateTime), 2000)
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
