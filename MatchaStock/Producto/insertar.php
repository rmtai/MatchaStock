<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    require_once 'conexion.php';
    $nombreProd = $_POST["nombreProd"];
    $descripcionProd = $_POST["descripcionProd"];
    $cantidadProd = $_POST["cantidadProd"];
    $idUser = $_POST["idUser"];

    //guardar imagen
    // Guardar imagen en una carpeta temporal
    $imagen_temporal = $_FILES["img"]["tmp_name"];
    $nombre_imagen = $_FILES["img"]["name"];
    $ruta_imagen_temporal = "../imagenTemporal" . $nombre_imagen;
    move_uploaded_file($imagen_temporal, $ruta_imagen_temporal);

    // Leer el contenido de la imagen como un string binario
    $imagen_binaria = file_get_contents($ruta_imagen_temporal);

    // Escapar caracteres especiales y convertir el string binario a un string hexadecimal
    $imagen_hexadecimal = $mysql->real_escape_string(bin2hex($imagen_binaria));
 

   // Insertar datos en la base de datos
   $my_query = "INSERT INTO producto (nombreProd, descripcionProd, cantidadProd, imagen, estado, idUser) VALUES ('$nombreProd', '$descripcionProd', '$cantidadProd', UNHEX('$imagen_hexadecimal'), 1, '$idUser')";
   $result = $mysql->query($my_query);

   // Eliminar la imagen de la carpeta temporal
   unlink($ruta_imagen_temporal);

   if ($result == true) {
       echo "Producto guardado";
   } else {
       echo "Error, el producto no ha podido ser guardado";
   }
}
