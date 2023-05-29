<?php
if ($_SERVER["REQUEST_METHOD"] == "GET") {
    require_once 'conexion.php';
    $my_query = "SELECT idItem, nombreProd, cantidadProd, estado, descripcionProd, idUser FROM producto WHERE estado = 1 OR estado = 2";
    $result = $mysql->query($my_query);

    if ($result->num_rows > 0) {
        $productos = array();
        while ($row = $result->fetch_assoc()) {
            $producto = array();
            $producto['idItem'] = $row['idItem'];
            $producto['nombreProd'] = $row['nombreProd'];
            $producto['cantidadProd'] = $row['cantidadProd'];
            $producto['estado'] = $row['estado'];
            $producto['descripcionProd'] = $row['descripcionProd'];
            $producto['idUser'] = $row['idUser'];
            array_push($productos, $producto);
        }
        header('Content-Type: application/json');
        echo json_encode($productos);
    } else {
        echo "No se encontraron productos";
    }
    $result->close();
    $mysql->close();
}
?>
