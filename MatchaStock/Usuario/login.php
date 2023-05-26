<?php
// Incluir el archivo de conexión a la base de datos
include 'conexion.php';

// Verificar si se enviaron los datos de inicio de sesión
if (isset($_POST['username'], $_POST['passwordUser'])) {
    // Obtener los datos de inicio de sesión enviados desde la aplicación
    $usernameInput = $_POST['username'];
    $passwordInput = $_POST['passwordUser'];

    // Consultar el usuario en la base de datos de manera segura (evitar inyección de SQL)
    $query = "SELECT username, passwordUser FROM usuario WHERE username = ? AND passwordUser = ?";
    $statement = $mysql->prepare($query);
    $statement->bind_param('ss', $usernameInput, $passwordInput);
    $statement->execute();
    $result = $statement->get_result();

    // Verificar si se encontró el usuario

    if ($row = $result->fetch_assoc()) {
        // Verificar la contraseña utilizando password_verify
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
} else {
    // Datos de inicio de sesión no proporcionados
    $response = array('success' => false, 'message' => 'Datos de inicio de sesión no proporcionados');
    echo json_encode($response);
}

// Cerrar la conexión a la base de datos
$mysql->close();
?>
