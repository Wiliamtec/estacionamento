insert into USUARIOS(id, username, password, role)values(100, 'ana@gmail.com', '$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_ADMIN');
insert into USUARIOS(id, username, password, role)values(101, 'bia@gmail.com', '$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role)values(102, 'tosh@gmail.com','$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role)values(103, 'pedro@gmail.com','$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_ADMIN');

insert into CLIENTES(id, nome, cpf, id_usuario)values(10,'Bianca Silva', '28293518006',101);
insert into CLIENTES(id, nome, cpf, id_usuario)values(20,'Peter Tosh', '41644459043',102);

INSERT INTO VAGAS(id,codigo,status)values(10,'A-01','OCUPADA');
INSERT INTO VAGAS(id,codigo,status)values(20,'A-02','OCUPADA');
INSERT INTO VAGAS(id,codigo,status)values(30,'A-03','OCUPADA');
INSERT INTO VAGAS(id,codigo,status)values(40,'A-04','OCUPADA');
INSERT INTO VAGAS(id,codigo,status)values(50,'A-05','OCUPADA');

INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00',10,10);
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)values ('20230314-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2023-03-14 10:15:00',20,20);
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)values ('20230315-101500', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:15:00',10,30);
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)values ('20230316-101600', 'SIE-1040', 'FIAT', 'SIENA', 'VERDE', '2023-03-14 10:15:00',20,40);
INSERT INTO CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)values ('20230317-101700', 'SIE-1050', 'FIAT', 'SIENA', 'VERDE', '2023-03-14 10:15:00',10, 50);

