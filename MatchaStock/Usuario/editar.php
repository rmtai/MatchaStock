<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    require_once 'conexion.php';
    $nombre = $_POST["nombre"];
    $apellido = $_POST["apellido"];
    $username = $_POST["username"];
    $passwordUser = $_POST["passwordUser"];
    $email = $_POST["email"];
	
	$hostname = "localhost";
    $dbusername = "root";
    $dbpassword = "";
    $database = "matchastockdb";
	
	$conn = new mysqli($hostname, $dbusername, $dbpassword, $database);

    // Verificar si se estableció la conexión correctamente
if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

// Verificar si se envió el ID del usuario
if (isset($_POST["idUser"])) {
    $idUser = $_POST["idUser"];
    // Realizar la edición del usuario con el ID proporcionado
    $sql = "UPDATE usuario SET nombre='$nombre', apellido='$apellido', username='$username', password='$passwordUser', email='$email' WHERE id=$idUser";

    if ($conn->query($sql) === TRUE) {
        echo "Usuario editado correctamente";
    } else {
        echo "Error al editar el usuario: " . $conn->error;
    }
} else {
    // El ID del usuario no se proporcionó, realizar una inserción en su lugar
    $sql = "INSERT INTO usuario (nombre, apellido, username, password, email) VALUES ('$nombre', '$apellido', '$username', '$passwordUser', '$email')";

    if ($conn->query($sql) === TRUE) {
        echo "Usuario creado correctamente";
    } else {
        echo "Error al crear el usuario: " . $conn->error;
    }
}

// Cerrar la conexión a la base de datos
$conn->close();
?>