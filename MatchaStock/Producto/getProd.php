<?php

require_once 'conexion.php';

// Realizar la consulta para obtener los datos con estado 3
$query = "SELECT idItem, nombreProd, descripcionProd, cantidadProd, estado, idUser  FROM producto WHERE estado = 3";
$result = $mysql->query($query);

if ($result->num_rows >= 0) {
    $productos = array();
    while ($row = $result->fetch_assoc()) {
        $producto = array();
        $producto['idItem'] = $row['idItem'];
        $producto['nombreProd'] = $row['nombreProd'];
        $producto['descripcionProd'] = $row['descripcionProd'];
        $producto['cantidadProd'] = $row['cantidadProd'];
        $producto['estado'] = $row['estado'];
        $producto['idUser'] = $row['idUser'];
        array_push($productos, $producto);
    }
    header('Content-Type: application/json');
    echo json_encode($productos);
} else {
    echo "No se encontraron productos";
}
$mysql->close();
?>
