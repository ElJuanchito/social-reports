// mongosh "mongodb://localhost:27017/todoTest" "src/test/resources/dataset.js"

db = connect("mongodb://localhost:27017/todoTest")

//Todas las contraseñas encriptadas son "12345678"

//En caso del fallo de una prueba hacer un truncate (o borrar manualmente) los datos
//y volver a ejecutar el script para volver a poner los datos
db.users.deleteMany({})
// Colección users
db.users.insertMany([
    {
        _id: ObjectId("67fb3897397d860960faba1f"),
        email: "pepepistolas@gmail.com",
        role: "CLIENT",
        phone: "3225179118",
        password: "$2a$10$CCEOqZBHX3ALnZ1aHhhzqu5HlOlkyliGwgS7P630lAsWZQ6XYOq0u",
        name: "Pepe el pruebas",
        city: "BOGOTA",
        status: "INACTIVE",
        validationCode: {
            date: {
                date: new ISODate("2025-04-13T04:07:51.844Z")
            },
            code: "UKOT9N"
        },
        address: "Casa de pepe",
        _class: "co.edu.uniquindio.social_reports.model.entities.User"
    },
    {
        _id: ObjectId("67fb3897397d860960faba20"),
        email: "ana.torres@example.com",
        role: "CLIENT",
        phone: "3101234567",
        password: "$2a$10$CCEOqZBHX3ALnZ1aHhhzqu5HlOlkyliGwgS7P630lAsWZQ6XYOq0u",
        name: "Ana Torres",
        city: "MEDELLIN",
        status: "INACTIVE",
        validationCode: {
            date: new ISODate("2025-04-13T04:10:00.000Z"),
            code: "A1B2C3"
        },
        address: "Calle 123 #45-67",
        _class: "co.edu.uniquindio.social_reports.model.entities.User"
    },
    {
        _id: ObjectId("67fb3897397d860960faba21"),
        email: "jose.garcia@example.com",
        role: "CLIENT",
        phone: "3017654321",
        password: "$2a$10$CCEOqZBHX3ALnZ1aHhhzqu5HlOlkyliGwgS7P630lAsWZQ6XYOq0u",
        name: "José García",
        city: "CALI",
        status: "INACTIVE",
        validationCode: {
            date: new ISODate("2025-04-13T04:12:00.000Z"),
            code: "ZXCVB1"
        },
        address: "Cra 10 #20-30",
        _class: "co.edu.uniquindio.social_reports.model.entities.User"
    },
    {
        _id: ObjectId("67fb3897397d860960faba22"),
        email: "luisa.mendez@example.com",
        role: "CLIENT",
        phone: "3149876543",
        password: "$2a$10$CCEOqZBHX3ALnZ1aHhhzqu5HlOlkyliGwgS7P630lAsWZQ6XYOq0u",
        name: "Luisa Méndez",
        city: "MANIZALES",
        status: "INACTIVE",
        validationCode: {
            date: new ISODate("2025-04-13T04:13:30.000Z"),
            code: "LU1SA2"
        },
        address: "Av. Libertador 99",
        _class: "co.edu.uniquindio.social_reports.model.entities.User"
    },
    {
        _id: ObjectId("67fb3897397d860960faba23"),
        email: "carlos.ramos@example.com",
        role: "CLIENT",
        phone: "3152233445",
        password: "$2a$10$CCEOqZBHX3ALnZ1aHhhzqu5HlOlkyliGwgS7P630lAsWZQ6XYOq0u",
        name: "Carlos Ramos",
        city: "PEREIRA",
        status: "INACTIVE",
        validationCode: {
            date: new ISODate("2025-04-13T04:15:45.000Z"),
            code: "CRAM25"
        },
        address: "Diagonal 45 #78-56",
        _class: "co.edu.uniquindio.social_reports.model.entities.User"
    }




]);