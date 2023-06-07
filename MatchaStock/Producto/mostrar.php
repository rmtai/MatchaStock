<?php
require_once 'conexion.php';

if ($mysql->connect_error) {
    die("Conexion fallida: " . $mysql->connect_error);
}

$sql = "SELECT idItem, nombreProd, descripcionProd, cantidadProd, estado, idUser FROM producto";
$result = $mysql->query($sql);

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