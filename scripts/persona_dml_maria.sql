INSERT INTO 
	`persona_db`.`persona`(`cc`,`nombre`,`apellido`,`genero`,`edad`) 
VALUES
	(123456789,'Pepe','Perez','M',30),
	(987654321,'Pepito','Perez','M',null),
	(321654987,'Pepa','Juarez','F',30),
	(147258369,'Pepita','Juarez','F',10),
	(963852741,'Fede','Perez','M',18);

INSERT INTO 
	`persona_db`.`profesion`(`id`,`nom`,`des`)
VALUES
	(1,'Arquitecto','Diseñador de edificaciones'),
	(2,'Enfermero','Especialista en atención médica'),
	(3,'Contador','Gestor de finanzas y tributos'),
	(4,'Docente','Profesor universitario'),
	(5,'Diseñador','Diseñador gráfico profesional');

INSERT INTO
	`persona_db`.`telefono`(`num`,`oper`,`duenio`)
VALUES
	(301112233,'Tigo',123456789),
	(305556677,'WOM',987654321),
	(310778899,'ETB',963852741);

