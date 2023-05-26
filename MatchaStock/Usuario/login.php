<?php
// Incluir el archivo de conexión a la base de datos
include 'conexion.php';

// Obtener los datos de inicio de sesión enviados desde la aplicación
$usernameInput = $_POST['username'];
$passwordInput = $_POST['passwordUser'];

// Consultar el usuario en la base de datos
$query = "SELECT * FROM usuario WHERE username = :username";
$statement = $mysql->prepare($query);
$statement->bind_param(':username', $usernameInput);
$statement->execute();
$result = $statement->get_result();

// Verificar si se encontró el usuario
if ($row = $result->fetch_assoc()) {
    // Verificar la contraseña
    if (password_verify($passwordInput, $row['passwordUser'])) {
        // Inicio de sesión exitoso
        $response = array('success' => true, 'message' => 'Inicio de sesión exitoso');
        echo json_encode($response);
    } else {
        // Contraseña incorrecta
        $response = array('success' => false, 'message' => 'Contraseña incorrecta');
        echo json_encode($response);
    }
} else {
    // Usuario no encontrado
    $response = array('success' => false, 'message' => 'Usuario no encontrado');
    echo json_encode($response);
}

// Cerrar la conexión a la base de datos
$mysql->close();
?>