<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    require_once 'conexion.php';
    $idUser = $_POST["idUser"];
    $nombre = $_POST["nombre"];
    $apellido = $_POST["apellido"];
    $username = $_POST["username"];
    $email = $_POST["email"];
    $passwordUser = $_POST["passwordUser"];

    $query =
    "UPDATE usuario SET 
    nombre = '$nombre', 
    apellido = '$apellido', 
    username = '$username',
    email = '$email',
    passwordUser = '$passwordUser'
    WHERE idUser = $idUser";

    $result = $mysql->query($query);

    if ($result == true) {
        echo "Usuario editado satisfactoriamente...";
    } else {
        echo "Error al editar usuario...";
    }
} else {
    echo "Error desconocido";
} 
?>