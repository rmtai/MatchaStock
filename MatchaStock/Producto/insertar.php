<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    require_once 'conexion.php';

    $nombreProd = $_POST["nombreProd"];
    $descripcionProd = $_POST["descripcionProd"];
    $cantidadProd = $_POST["cantidadProd"];
    $idUser = $_POST["idUser"];
    
    
    $my_query = "INSERT INTO producto (nombreProd, descripcionProd, cantidadProd, estado, idUser) VALUES ('".$nombreProd."','".$descripcionProd."','".$cantidadProd."', 1, ".$idUser.")";

    $result = $mysql -> query($my_query);
    if($result == true){
        echo "Producto guardado satisfactoriamente...";
    }
    else{
        echo "Error al guardar producto...";
    }
}
else{
    echo"Error desconocido";
}

?>


