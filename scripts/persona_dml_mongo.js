db = db.getSiblingDB('persona_db');

db.persona.insertMany([
	{
		"_id": NumberInt(123456789),
		"nombre": "Pepe",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(987654321),
		"nombre": "Pepito",
		"apellido": "Perez",
		"genero": "M",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(321654987),
		"nombre": "Pepa",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(30),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(147258369),
		"nombre": "Pepita",
		"apellido": "Juarez",
		"genero": "F",
		"edad": NumberInt(10),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": NumberInt(963852741),
		"nombre": "Fede",
		"apellido": "Perez",
		"genero": "M",
		"edad": NumberInt(18),
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	}
], { ordered: false });

db.telefono.insertMany([
    {
        "_id": "3183195301",
        "num": "4567435",
        "oper": "Wom",
        "primaryDuenio": NumberInt(147258369),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    },
    {
        "_id": "3153559499",
        "num": "7096778",
        "oper": "Tigo",
        "primaryDuenio": NumberInt(321654987),
        "_class": "co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument"
    }
], { ordered: false });
