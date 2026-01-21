insert into USUARIOS(id, username, password, role)values(100, 'ana@gmail.com', '$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_ADMIN');
insert into USUARIOS(id, username, password, role)values(101, 'bia@gmail.com', '$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role)values(102, 'tosh@gmail.com', '$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_CLIENTE');
insert into USUARIOS(id, username, password, role)values(103, 'pedro@gmail.com', '$2y$10$BmOytATYZangNScORy75W.wK/X0dF6/7FiWy5SXRMNRp5EUicrvIi', 'ROLE_CLIENTE');

insert into CLIENTES(id, nome, cpf, id_usuario)values(10,'Bianca Silva', '28293518006',101);
insert into CLIENTES(id, nome, cpf, id_usuario)values(20,'Peter Tosh', '41644459043',102);
