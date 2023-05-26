<?php

include 'conexion.php';
$username = $_POST['username'];
$passwordUser = $_POST['passwordUser'];

$sentencia = $conexion->prepare("SELECT username, passwordUser FROM usuario WHERE username =? AND passwordUser=?");
$sentencia -> bind_param('ss', $username, $passwordUser);
$sentencia -> execute();

$resultado = $sentencia -> get_result();
if($fila = $resultado -> fetch_assoc()){
    echo json_encode($fila, JSON_UNESCAPED_UNICODE);
}

$sentencia -> close();
$conexion -> close();


?>
