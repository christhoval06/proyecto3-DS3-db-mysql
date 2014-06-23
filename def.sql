create table if not exists cliente(
  clienteid int not null auto_increment,
  nombre varchar(30) not null,
  apellido varchar(30) not null,
  cedula varchar(15) not null,
  direccion varchar(50),
  telefono varchar(7),
  provincia varchar(2),
  compra_anual decimal(9,2),
  activo int default 1,
  primary key(clienteid)
);

CREATE TABLE if not exists vendedor
(
  vendedorid INT PRIMARY KEY NOT NULL,
  codigo varchar(4) NOT NULL,
  nombre varchar(20) NOT NULL,
  apellido varchar(20) NOT NULL,
  departamento varchar (2),
  cargo varchar(20),
  venta_mensual decimal(9,2) DEFAULT 0,
  venta_anual decimal (9,2) DEFAULT 0,
  activo INT DEFAULT 1
);