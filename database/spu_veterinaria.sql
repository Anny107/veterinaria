USE veterinaria;
-- Buscar mascotas segun DNI
DELIMITER $$
CREATE PROCEDURE spu_listar_mascotas_clientes
(
  IN _idcliente INT
)
BEGIN
	SELECT 
		mascotas.idmascota,clientes.apellidos,clientes.nombres,mascotas.nombre, animales.nombreanimal,
		razas.nombreraza
	FROM mascotas
	INNER JOIN clientes ON clientes.idcliente = mascotas.idcliente
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE mascotas.idcliente = _idcliente;
END$$

CALL spu_listar_mascotas_clientes(1);

-- Mostrar detalle de mascotas
DELIMITER $$
CREATE PROCEDURE spu_detalle_Mascota
(
	IN _idmascota	INT
)
BEGIN
	SELECT 
		mascotas.idmascota,mascotas.nombre, animales.nombreanimal,
		razas.nombreraza, mascotas.fotografia, mascotas.color, mascotas.genero
	FROM mascotas
	INNER JOIN razas ON razas.idraza = mascotas.idraza
	INNER JOIN animales ON animales.idanimal = razas.idanimal
	WHERE mascotas.idmascota = _idmascota;
END$$

CALL spu_detalle_Mascota(3)

-- Registrar cliente
DELIMITER $$
CREATE PROCEDURE spu_registrar_Cliente
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

CALL spu_registrar_Cliente('Jimenez Yataco','Camila','33669911','123456');

-- Registrar mascota
DELIMITER $$
CREATE PROCEDURE spu_registrarMascota
(
	IN _idcliente	INT,
	IN _idraza	INT,
	IN _nombre	VARCHAR(50),
	IN _fotografia	VARCHAR(200),
	IN _color	VARCHAR(50),
	IN _genero	VARCHAR(50)
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
CREATE PROCEDURE spu_filtrar_Raza
(
  IN _idanimal INT
)
BEGIN
	SELECT *
	FROM razas
	WHERE idanimal = _idanimal;
END $$

CALL spu_filtrar_Raza(1);

-- Login
DELIMITER $$
CREATE PROCEDURE spu_login_cliente
(
 IN _dni CHAR(8)
)
BEGIN
	SELECT * FROM clientes
	WHERE dni = _dni;
END$$

CALL spu_login_cliente("71788436");

-- Actualizar contraseña de clientes
UPDATE clientes SET claveAcceso = "$2y$10$TyA02/gEDXalQJDCuy/leOX2.hXF3MIx6cHy6IlObleU/6G09suIC";