CREATE DATABASE veterinaria;
USE veterinaria;

CREATE TABLE clientes
(
	idcliente	INT AUTO_INCREMENT PRIMARY KEY,
	apellidos	VARCHAR(50)		NOT NULL,
	nombres		VARCHAR(50)		NOT NULL,
	dni			CHAR(8)			NOT NULL,
	claveAcceso	VARCHAR(200)	NOT NULL,
	create_at	DATETIME 		NOT NULL DEFAULT NOW(),
	update_at	DATETIME			NULL,
	CONSTRAINT uk_dni_cli UNIQUE(dni)
)ENGINE=INNODB;

INSERT INTO clientes(apellidos,nombres,dni,claveAcceso) VALUES 
('Sanchez Mesias','Daniel','45693247','123456'),
('Lopez Cardenas','Josue','77693211','123456'),
('Cabrera Napa','Anny','71788436','123456');

SELECT * FROM clientes;

CREATE TABLE animales
(
	idanimal			INT AUTO_INCREMENT PRIMARY KEY,
	nombreanimal	VARCHAR(50)		NOT NULL,
	create_at		DATETIME 		NOT NULL DEFAULT NOW(),
	update_at		DATETIME 		NULL,
	CONSTRAINT uk_nombreanimal UNIQUE(nombreanimal)
)ENGINE=INNODB;

INSERT INTO animales(nombreanimal) VALUES
('Perro'),
('Gato'),
('Peces');

SELECT * FROM animales;

CREATE TABLE razas
(
	idraza		INT AUTO_INCREMENT PRIMARY KEY,
	idanimal	INT 				NOT NULL,
	nombreraza	VARCHAR(50) 	NOT NULL,
	create_at	DATETIME			NOT NULL DEFAULT NOW(),
	update_at	DATETIME 		NULL,
	CONSTRAINT fk_idanimal_razas FOREIGN KEY (idanimal) REFERENCES animales(idanimal),
	CONSTRAINT uk_nombreraza UNIQUE(nombreraza)
)ENGINE=INNODB;

INSERT INTO razas(idanimal,nombreraza) VALUES
(1,'Pastor alemán'),
(1,'Bulldog'),
(1,'Golden retriever'),
(1,'Rottweiler'),
(2,'Siberiano'),
(2,'Bombay'),
(2,'Persa'),
(2,'Siamés'),
(3,'Payaso'),
(3,'Guppy'),
(3,'Zebra pleco'),
(3,'Goldfish');

SELECT * FROM razas;

CREATE TABLE mascotas
(
	idmascota	INT AUTO_INCREMENT PRIMARY KEY,
	idcliente	INT 				NOT NULL,
	idraza		INT 				NOT NULL,
	nombre		VARCHAR(50) 	NOT NULL,
	fotografia	VARCHAR(200)	NULL,
	color			VARCHAR(50)		NOT NULL,
	genero		VARCHAR(50)		NOT NULL,
	create_at	DATETIME			NOT NULL DEFAULT NOW(),
	update_at	DATETIME 		NULL,
	CONSTRAINT fk_idcliente_mas FOREIGN KEY (idcliente) REFERENCES clientes(idcliente),
	CONSTRAINT fk_idraza_mas	FOREIGN KEY (idraza) REFERENCES razas(idraza)
)ENGINE=INNODB;

INSERT INTO mascotas (idcliente,idraza,nombre,fotografia,color,genero) VALUES
(1,12,'Goldi','pDorado.jpg','Dorado','Hembra'),
(1,2,'Rocky','bulldog.jpg','Marron con blanco','Macho'),
(2,7,'Felix','persa.jpg','Naranja','Macho'),
(3,4,'Luna','rottweiler.jpg','Negro','Hembra');

SELECT * FROM mascotas;


-- Buscar mascotas segun DNI
DELIMITER $$
CREATE PROCEDURE spu_buscarClientes
(
  IN _dni CHAR(8)
)
BEGIN
	SELECT 
		mascotas.idmascota,clientes.apellidos,clientes.nombres,mascotas.nombre, animales.nombreanimal,
		razas.nombreraza
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE clientes.dni = _dni;
END$$

CALL spu_buscarClientes('71788436');

-- Mostrar detalle de mascotas
DELIMITER $$
CREATE PROCEDURE spu_detalleMascota
(
	IN _idmascota	INT
)
BEGIN
	SELECT 
		mascotas.idmascota,mascotas.nombre, animales.nombreanimal,
		razas.nombreraza, mascotas.fotografia, mascotas.color, mascotas.genero
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE mascotas.idmascota = _idmascota;
END$$

CALL spu_detalleMascota(3)

-- Registrar cliente
DELIMITER $$
CREATE PROCEDURE spu_registrarCliente
(
	IN _apellidos 		VARCHAR(50),
	IN _nombres	  		VARCHAR(50),
	IN _dni		  		CHAR(8),
	IN _claveAcceso	VARCHAR(200)
)
BEGIN
	INSERT INTO clientes(apellidos,nombres,dni,claveAcceso) VALUES
	(_apellidos,_nombres,_dni,_claveAcceso);
END$$

CALL spu_registrarCliente('Jimenez Yataco','Camila','33669911','123456')

-- Registrar mascota
DELIMITER $$
CREATE PROCEDURE spu_registrarMascota
(
	_idcliente	INT,
	_idraza		INT,
	_nombre		VARCHAR(50),
	_fotografia	VARCHAR(200),
	_color		VARCHAR(50),
	_genero		VARCHAR(50)
)
BEGIN
	IF _fotografia = "" THEN SET _fotografia = NULL; END IF;
	INSERT INTO mascotas (idcliente,idraza,nombre,fotografia,color,genero) VALUES
	(_idcliente,_idraza,_nombre,_fotografia,_color,_genero);
END$$

CALL spu_registrarMascota(4,9,'Marlin','pez.jpg','Naranja','Macho');

-- Listar animales
DELIMITER $$
CREATE PROCEDURE spu_listar_animales()
BEGIN
	SELECT *
	FROM animales;
END $$

-- Filtro para las razas
DELIMITER $$
CREATE PROCEDURE spu_filtroRaza
(
  IN _idanimal INT
)
BEGIN
	SELECT *
	FROM razas
	WHERE idanimal = _idanimal;
END $$
-- Login

DELIMITER $$
CREATE PROCEDURE spu_loginCliente
(
 IN _dni CHAR(8)
)
BEGIN
	SELECT * FROM clientes
	WHERE dni = _dni;
END$$

CALL spu_loginCliente("71788436");

-- Actualizar contraseña de clientes
UPDATE clientes SET claveAcceso = "$2y$10$Lh.FEBuJKjM/fQYxT2j3vO7g2mZZF7HRvgplu.zchvnOvviGJI2Ki";