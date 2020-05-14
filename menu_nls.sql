--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.14
-- Dumped by pg_dump version 10.12 (Ubuntu 10.12-0ubuntu0.18.04.1)

-- Started on 2020-05-15 00:27:01 WIB

/*
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;
*/
--
-- TOC entry 2338 (class 0 OID 475727)
-- Dependencies: 175
-- Data for Name: c_security_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.c_security_menu VALUES ('ea869dc2-7788-4dbe-b963-940c6b9dbc09', NULL, 1, 'Menu Master', NULL, 1, 'master', 'Master', NULL, true, true);
INSERT INTO public.c_security_menu VALUES ('edf90293-36fe-45e6-97ab-46438d748f00', NULL, 2, 'Menu Master Item', NULL, 1, 'master.item', 'Item', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('a397031a-57c4-4327-a0f7-839b1ebe0560', NULL, 2, 'Master Tambahan Biaya', NULL, 2, 'master.tambahan-biaya', 'Tambahan Biaya', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('b2b5a552-0f0a-40f6-b91b-6f233953bdba', NULL, 2, 'Master Jenis item', NULL, 3, 'master.jenis-item', 'Jenis Item', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('043f3c9f-ad9c-438f-8cac-a8d36a3f5d2b', NULL, 2, 'Master Kapal', NULL, 4, 'master.kapal', 'Kapal', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('35c4091c-3e2d-4d90-abb0-dee86f0676ba', NULL, 2, 'Master Kota', NULL, 5, 'master.kota', 'Kota', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('c3b751c6-2b83-4572-bbde-0100b68e1c13', NULL, 2, 'Master Satuan Kirim', NULL, 6, 'master.satuan-kirim', 'Satuan Kirim', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('934af43a-4e64-4052-8af7-14b63adacb26', NULL, 2, 'Master Kondisi', NULL, 7, 'master.kondisi', 'Kondisi', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('82051bdf-d287-4a91-b4ed-f4b7eb93e464', NULL, 2, 'Master Toko', NULL, 8, 'master.toko', 'Toko', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('9123cb65-d980-42b7-bbb6-154014341374', NULL, 2, 'Master EMKL', NULL, 9, 'master.emkl', 'EMKL', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('85fd2b62-f3ad-4592-b9e5-d1f0fb67c85e', NULL, 2, 'Master Kapal Berangkat', NULL, 10, 'master.kapal-berangkat', 'Kapal Berangkat', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('f4d51cac-3b6b-4c52-98f5-a6110b618abe', NULL, 2, 'Master Kategori Harga', NULL, 11, 'master.kategori-harga', 'Kategori Harga', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('279a9353-7fab-40b6-8463-f631726150a8', NULL, 2, 'Master Setting Harga', NULL, 12, 'master.setting-harga', 'Setting Harga', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('c55de31c-e32a-41d6-8c71-af2ce72903d5', NULL, 2, 'Master Setting Aplikasi', NULL, 13, 'master.setting-aplikasi', 'Setting Aplikasi', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('85e309d2-7b54-4f88-a500-e43f267368a2', NULL, 2, 'Master Jenis Pembayaran', NULL, 14, 'master.jenis-pembayaran', 'Jenis Pembayaran', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('edc73ff5-2b8e-425d-8ef7-78277253e7e6', NULL, 2, 'Master Rekening', NULL, 15, 'master.rekening', 'Rekening', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('b659aace-2630-490d-96cf-a076200fa3a5', NULL, 2, 'Master Ket. Jatuh Tempo', NULL, 16, 'master.ket-jatuh-tempo', 'Ket.Jatuh Tempo', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('7f34701f-6edb-416b-a599-132021137996', NULL, 2, 'Master Pelayaran', NULL, 17, 'master.pelayaran', 'Pelayaran', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', false, true);
INSERT INTO public.c_security_menu VALUES ('93e54c63-93c7-4267-a112-4f4e04beb44d', NULL, 1, 'Pricelist', NULL, 2, 'pricelist', 'Pricelist', NULL, true, true);
INSERT INTO public.c_security_menu VALUES ('490d218b-6e60-4bd5-887c-cb8fb564da15', NULL, 2, 'Pricelist Pelayaran', NULL, 1, 'pricelist.pricelist-pelayaran', 'Pricelist Pelayaran', '93e54c63-93c7-4267-a112-4f4e04beb44d', false, true);
INSERT INTO public.c_security_menu VALUES ('c4ce7273-8c3b-4adc-a39d-c66310837659', NULL, 1, 'Menu Transaksi', NULL, 3, 'transaksi', 'Transaksi', NULL, true, true);
INSERT INTO public.c_security_menu VALUES ('27d807ff-5a26-4919-a30f-14f3df596a9b', NULL, 2, 'Transaksi Stuffing', NULL, 1, 'transaksi.stuffing', 'Stuffing', 'c4ce7273-8c3b-4adc-a39d-c66310837659', false, true);
INSERT INTO public.c_security_menu VALUES ('6d4157d1-4dd7-49fc-b5d7-9358b34a6dde', NULL, 2, 'List Surat Jalan', NULL, 2, 'transaksi.listsuratjalan', 'List Surat Jalan', 'c4ce7273-8c3b-4adc-a39d-c66310837659', false, true);
INSERT INTO public.c_security_menu VALUES ('47162ed5-eb18-4c56-90c4-1cd39ce5e98f', NULL, 2, 'Transaksi Surat Jalan', NULL, 3, 'transaksi.suratjalan', 'Surat Jalan', 'c4ce7273-8c3b-4adc-a39d-c66310837659', false, true);
INSERT INTO public.c_security_menu VALUES ('7e99238a-0d15-4a65-ad3f-baa59fcdd859', NULL, 2, 'Transaksi Nota', NULL, 4, 'transaksi.nota', 'Nota', 'c4ce7273-8c3b-4adc-a39d-c66310837659', false, true);
INSERT INTO public.c_security_menu VALUES ('a05006a8-fb46-48ca-badc-fda5aabf54c6', NULL, 2, 'Transaksi Pembayaran', NULL, 5, 'transaksi.pembayaran', 'Pembayaran', 'c4ce7273-8c3b-4adc-a39d-c66310837659', false, true);
INSERT INTO public.c_security_menu VALUES ('9409e822-3111-4482-8e92-cd5e8f5ce95c', NULL, 1, 'Menu Laporan', NULL, 4, 'laporan', 'Laporan', NULL, true, true);
INSERT INTO public.c_security_menu VALUES ('6d281498-8346-4bd7-bdb6-b370d189fa92', NULL, 2, 'Laporan Dashboard', NULL, 1, 'laporan.dashboard', 'Dashboard', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('1b37370b-2539-409e-8723-6cd6a15470db', NULL, 2, 'Laporan Per Toko', NULL, 2, 'laporan.pertoko', 'Per Toko', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('f7c29d32-6efd-46cb-bdfd-3c6444744bb5', NULL, 2, 'Laporan Per Kapal', NULL, 3, 'laporan.perkapal', 'Stuffing / Kapal', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('b2fec2b0-2147-4856-8958-eaae8caca402', NULL, 2, 'Laporan Info Item', NULL, 4, 'laporan.infoitem', 'Info Item', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('1dfefe36-9d1f-4df2-8647-a46aeed59f7f', NULL, 2, 'Laporan Kontainer', NULL, 5, 'laporan.container', 'Jml Container Bulanan', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('255888d2-a964-4e66-8d15-05c70cc1a52b', NULL, 2, 'Laporan Rincian Nota', NULL, 6, 'laporan.rincian-nota', 'Rincian Nota', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('138e038a-82d2-48d5-91b6-0d89ddc749b9', NULL, 2, 'Laporan Rekap Nota', NULL, 7, 'laporan.rekap-nota', 'Rekap Nota', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('edc1c6fa-41ce-441a-a49c-187e62049be0', NULL, 2, 'Laporan Rekap Nota Belum Lunas', NULL, 8, 'laporan.rekap-nota-belum-lunas', 'Rekap Nota Belum Lunas', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('8fe940d9-ac4d-4967-99cf-954217bceffd', NULL, 2, 'Laporan Rekap Pembayaran', NULL, 9, 'laporan.rekap-pembayaran', 'Rekap Pembayaran', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('06806f0d-dec5-439d-8075-4227c8d2e84a', NULL, 2, 'Laporan Pricelist Pelayaran', NULL, 10, 'laporan.pricelist-pelayaran', 'Pricelist Pelayaran', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('51646807-3b2c-4dff-b218-5752408b1b6b', NULL, 2, 'Laporan Tracking Kapal', NULL, 11, 'laporan.tracking-kapal', 'Tracking Kapal', '9409e822-3111-4482-8e92-cd5e8f5ce95c', false, true);
INSERT INTO public.c_security_menu VALUES ('35053409-6d4c-4dc0-b083-1a2679eba57c', NULL, 1, 'Menu Setting', NULL, 5, 'setting', 'Setting', NULL, true, true);
INSERT INTO public.c_security_menu VALUES ('7c1d858e-9916-4264-8c0d-4200ffdb25ad', NULL, 2, 'Setting Menu', NULL, 1, 'setting.menu', 'Menu', '35053409-6d4c-4dc0-b083-1a2679eba57c', false, true);
INSERT INTO public.c_security_menu VALUES ('0519b6a8-5357-4ccd-99a5-ec2a4116d6a6', NULL, 2, 'Setting Role', NULL, 2, 'setting.role', 'Role', '35053409-6d4c-4dc0-b083-1a2679eba57c', false, true);
INSERT INTO public.c_security_menu VALUES ('43e35e80-b311-4e3b-a3a9-de345cda4a5f', NULL, 1, 'Menu Admin', NULL, 6, 'admin', 'Administrasi', NULL, true, true);
INSERT INTO public.c_security_menu VALUES ('7c5268cd-e913-4248-95b5-8dbe94ebdd77', NULL, 2, 'Menu User', NULL, 1, 'user-management', 'Pengguna', '43e35e80-b311-4e3b-a3a9-de345cda4a5f', false, true);
INSERT INTO public.c_security_menu VALUES ('d99b90cb-725b-4b29-8be3-3b388870c556', NULL, 1, 'Halaman Login', NULL, 1, 'login', 'Login', NULL, true, NULL);
INSERT INTO public.c_security_menu VALUES ('5fac7926-4e36-4408-945b-1a8d691d92f5', NULL, 1, 'Main Page', NULL, 2, 'home', 'Home', NULL, true, NULL);


--
-- TOC entry 2339 (class 0 OID 475753)
-- Dependencies: 179
-- Data for Name: c_security_role_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '255888d2-a964-4e66-8d15-05c70cc1a52b', '8595e6a6-2261-4b1f-ab57-e4a8e4f073ca');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '9123cb65-d980-42b7-bbb6-154014341374', 'fbea0884-b4b9-4615-bebb-90f11fc6f1aa');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '0519b6a8-5357-4ccd-99a5-ec2a4116d6a6', 'ed6a9e43-d767-4a0c-82b4-65c011939ef9');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '138e038a-82d2-48d5-91b6-0d89ddc749b9', '34583a70-a4da-49fc-8fb0-00f398c84f69');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'edc1c6fa-41ce-441a-a49c-187e62049be0', '402fddb9-b2b0-43fb-b1ce-ede74a136a66');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '85e309d2-7b54-4f88-a500-e43f267368a2', 'ed38d2e0-0c46-46e9-a3fc-93d63a0d46d8');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '35053409-6d4c-4dc0-b083-1a2679eba57c', '4a6af368-b333-4cd6-b1e0-56920f0e24bc');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'c4ce7273-8c3b-4adc-a39d-c66310837659', '6f9d97fb-226e-40c4-be5e-7447601a22ed');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'f7c29d32-6efd-46cb-bdfd-3c6444744bb5', '5ed6597b-df9b-4372-bef0-5448f993dd41');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '7f34701f-6edb-416b-a599-132021137996', '71fb3c68-adc6-488e-9e3e-1efeb52fd677');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'a05006a8-fb46-48ca-badc-fda5aabf54c6', '1441e17b-b466-473b-9139-01d7d832ffb0');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'c3b751c6-2b83-4572-bbde-0100b68e1c13', 'd742bdeb-864c-46ba-8c6f-ef208d2629b7');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '934af43a-4e64-4052-8af7-14b63adacb26', '61017d4c-e43e-4f1f-a43d-55b8b27ab7b1');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '06806f0d-dec5-439d-8075-4227c8d2e84a', '782a5a63-aedb-443d-8e30-3b3cd5b0a8a3');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '43e35e80-b311-4e3b-a3a9-de345cda4a5f', '598d0cdc-08a3-4459-b4aa-8348510f16b4');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'a397031a-57c4-4327-a0f7-839b1ebe0560', '47ee7200-df91-48f2-aebd-dd950540555f');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '35c4091c-3e2d-4d90-abb0-dee86f0676ba', '1c014c14-bd17-4ef1-8699-21f8ab12521e');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '6d281498-8346-4bd7-bdb6-b370d189fa92', '65f2f46a-9e88-4734-82f2-0a1ebd8e29fa');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'b2fec2b0-2147-4856-8958-eaae8caca402', '09cb2e62-4582-4059-b696-3055a9302f83');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '51646807-3b2c-4dff-b218-5752408b1b6b', 'fd4964af-54d2-4b11-b7b5-6c2ed352e5d0');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '7e99238a-0d15-4a65-ad3f-baa59fcdd859', '365d1c54-9e6c-42cf-8b19-4c54b1bd1f79');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'd99b90cb-725b-4b29-8be3-3b388870c556', '0a8f6661-8cb9-42a8-a71b-9b2b89c13138');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'edf90293-36fe-45e6-97ab-46438d748f00', '54703517-deac-4226-ac23-c8a400fd0205');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '85fd2b62-f3ad-4592-b9e5-d1f0fb67c85e', '0e6245c4-c949-427a-925c-e5ad9d7b2851');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '7c1d858e-9916-4264-8c0d-4200ffdb25ad', '6ee27c01-4261-49f6-be0d-b87ca3e190cf');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '1dfefe36-9d1f-4df2-8647-a46aeed59f7f', 'c9e11026-84dd-4e67-ac77-1cbe50a3197c');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'b659aace-2630-490d-96cf-a076200fa3a5', '12addb2f-e9e1-4f88-8a9a-8e75920e519a');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '9409e822-3111-4482-8e92-cd5e8f5ce95c', 'd7b2ba40-dd57-4ad6-a345-a0525b9fa422');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '47162ed5-eb18-4c56-90c4-1cd39ce5e98f', 'f04d2382-8193-4beb-95a4-e704a2a5aaae');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '490d218b-6e60-4bd5-887c-cb8fb564da15', '693a1031-938f-4fcb-9713-f1fccbdceb05');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', '0735d8d7-6e8d-40c6-9515-b8b566d161b1');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '279a9353-7fab-40b6-8463-f631726150a8', '06bafd9e-69a7-4de5-9ede-6dd50dd07719');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'b2b5a552-0f0a-40f6-b91b-6f233953bdba', 'e649b81d-599c-4666-abb0-8cb6d617d883');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '5fac7926-4e36-4408-945b-1a8d691d92f5', '56aadc09-c2b4-4a66-aa01-1f5c596100fe');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '82051bdf-d287-4a91-b4ed-f4b7eb93e464', '2bc4d141-36ba-4233-bd08-ba9581f11d8d');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '27d807ff-5a26-4919-a30f-14f3df596a9b', '969cc2b6-3262-4e0e-b56a-b97234b074ee');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '043f3c9f-ad9c-438f-8cac-a8d36a3f5d2b', '2f00987d-4bdc-4f3d-ac51-ce64968c7936');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '8fe940d9-ac4d-4967-99cf-954217bceffd', '56b7f1a1-5619-4407-8dbe-3c16aa5f4f6a');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '93e54c63-93c7-4267-a112-4f4e04beb44d', '5b5c53e8-4c47-4af6-b60b-ff9dec8b700f');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'edc73ff5-2b8e-425d-8ef7-78277253e7e6', 'd4296840-9d4a-41cd-9062-089b62c6d517');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '7c5268cd-e913-4248-95b5-8dbe94ebdd77', 'bc88183f-e170-4f40-8b04-c2c5c9d870dd');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '1b37370b-2539-409e-8723-6cd6a15470db', '4274689a-6ae6-424f-8073-bca64376144a');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'c55de31c-e32a-41d6-8c71-af2ce72903d5', 'e0042be0-dc5c-47be-b9e3-18a52879ec84');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', 'f4d51cac-3b6b-4c52-98f5-a6110b618abe', '8ef312ee-de8b-414f-867c-b366d7547439');
INSERT INTO public.c_security_role_menu VALUES ('5deadc81-27d0-417b-a216-efff0fb0f66d', '6d4157d1-4dd7-49fc-b5d7-9358b34a6dde', '09f9c3ef-60bf-4d70-af44-9d7c2c01dad6');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '9123cb65-d980-42b7-bbb6-154014341374', 'e405abb6-0544-4a82-b9ea-ac0d20d0f2d1');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'edc73ff5-2b8e-425d-8ef7-78277253e7e6', '93d79686-5b29-4552-ade1-1182f59a8e5a');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '043f3c9f-ad9c-438f-8cac-a8d36a3f5d2b', 'a97fad25-3011-47d2-a985-f22ae9effa07');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '85fd2b62-f3ad-4592-b9e5-d1f0fb67c85e', 'bea52a5c-3819-4f3d-b8a4-0fb1a918fe47');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '5fac7926-4e36-4408-945b-1a8d691d92f5', 'fa278fad-08cb-4742-8a89-d6beff106a08');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'd99b90cb-725b-4b29-8be3-3b388870c556', '590b7741-3ab8-4135-b9d5-c1a04d1e0b1a');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'ea869dc2-7788-4dbe-b963-940c6b9dbc09', 'b147f79a-61d7-4bd4-bdc0-015e916a04a7');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'f4d51cac-3b6b-4c52-98f5-a6110b618abe', 'f79ec6b3-fbae-406e-be82-4e3120713068');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '27d807ff-5a26-4919-a30f-14f3df596a9b', 'e7c7c92c-54d0-4e48-ba61-d827b1878db6');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '7f34701f-6edb-416b-a599-132021137996', '919fdbfb-1337-42bf-9c85-27a7614c74e3');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'c55de31c-e32a-41d6-8c71-af2ce72903d5', '2c8b0954-80ef-42af-9d58-a267f2f433b5');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '85e309d2-7b54-4f88-a500-e43f267368a2', 'd55178bf-3ad7-418f-b2dc-2db790e7dd9b');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'b2b5a552-0f0a-40f6-b91b-6f233953bdba', 'c77ed971-9420-43e2-809f-c76242e6bc58');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '934af43a-4e64-4052-8af7-14b63adacb26', 'b375ee06-80a6-4d90-a384-2d6dc686cf18');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '35c4091c-3e2d-4d90-abb0-dee86f0676ba', 'b548b597-a03b-4f26-aade-2118589e4926');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'c3b751c6-2b83-4572-bbde-0100b68e1c13', '054dc71b-a5aa-4526-8e8d-58dcbeb0c967');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '279a9353-7fab-40b6-8463-f631726150a8', '42e0141c-e23e-40f0-b1ab-468ab4bb1981');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'a397031a-57c4-4327-a0f7-839b1ebe0560', '7f4bdd83-14fd-4b0d-a366-1cc56639b79f');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'c4ce7273-8c3b-4adc-a39d-c66310837659', 'fb3ee20a-2db7-45dc-a07e-6ef3a4d2156d');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'edf90293-36fe-45e6-97ab-46438d748f00', '604aee76-e660-425b-9748-7382d84ea0a0');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', 'b659aace-2630-490d-96cf-a076200fa3a5', 'c9b9c4ca-aea1-4036-92a5-468cac0a39d1');
INSERT INTO public.c_security_role_menu VALUES ('b65eb616-3ad4-41da-94a3-e96667875408', '82051bdf-d287-4a91-b4ed-f4b7eb93e464', 'cac36f39-ea48-49a1-a75d-863470059630');


-- Completed on 2020-05-15 00:27:01 WIB

--
-- PostgreSQL database dump complete
--

