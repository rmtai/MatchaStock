<?php
session_start();
include_once 'conexion.php';

if (isset($_POST['username']) && isset($_POST['passwordUser'])) {
    $username = $_POST['username'];
    $passwordUser = $_POST['passwordUser'];

    $sentencia = $mysql->prepare("SELECT * FROM usuario WHERE username = ? AND passwordUser = ?");
    $sentencia->bind_param('ss', $username, $passwordUser);
    $sentencia->execute();

    $resultado = $sentencia->get_result();
    if ($row = $resultado->fetch_assoc()) {
        // Verificar la existencia de los campos requeridos
        if (isset($row['nombre']) && isset($row['apellido']) && isset($row['username']) && isset($row['passwordUser']) && isset($row['email'])) {
            $response = array(
                'success' => true,
                'message' => 'Inicio de sesión exitoso',
                'data' => $row
            );

            $_SESSION['username'] = $username;
        } else {
            $response = array(
                'success' => false,
                'message' => 'Error: Faltan campos requeridos en la respuesta del servidor'
            );
        }
    } else {
        $response = array(
            'success' => false,
            'message' => 'Usuario no encontrado o contraseña incorrecta'
        );
    }

    $sentencia->close();
} else {
    $response = array(
        'success' => false,
        'message' => 'No se proporcionaron datos de inicio de sesión'
    );
}

$mysql->close();

header('Content-Type: application/json');
echo json_encode($response, JSON_UNESCAPED_UNICODE);
?>
