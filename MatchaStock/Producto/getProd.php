<?php

require_once 'conexion.php';

// Realizar la consulta para obtener los datos con estado 3
$query = "SELECT * FROM producto WHERE estado = 3";
$result = $mysql->query($query);

// Verificar si hay resultados
if ($result && $result->num_rows > 0) {
    // Crear un array para almacenar los datos
    $data = array();

    // Iterar sobre los resultados y agregarlos al array
    while ($row = $result->fetch_assoc()) {
        $data[] = $row;
    }

    // Devolver los datos en formato JSON
    echo json_encode($data);
} else {
    echo 'No hay datos disponibles';
}

?>
