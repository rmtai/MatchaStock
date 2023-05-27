<?php
include_once 'conexion.php';

if (isset($_POST['username'])) {
    $username = $_POST['username'];
}
if (isset($_POST['passwordUser'])) {
    $passwordUser = $_POST['passwordUser'];
}
$sentencia = $mysql->prepare("SELECT * FROM usuario WHERE username =? AND passwordUser=?");
$sentencia->bind_param('ss', $username, $passwordUser);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($row = $resultado->fetch_assoc()) {

    $response = array(
        'success' => true, // Indica que el inicio de sesión fue exitoso
        'message' => 'Inicio de sesión exitoso', // Mensaje adicional, puede personalizarse
        'data' => $row // Puedes incluir cualquier otro dato que necesites en la respuesta
    );
} else {
    $response = array(
        'success' => false, // Indica que el inicio de sesión falló
        'message' => 'Usuario no encontrado o contraseña incorrecta' // Mensaje de error
    );
}

    header('Content-Type: application/json');
    echo json_encode($response, JSON_UNESCAPED_UNICODE);

$sentencia->close();
$mysql->close();
?>