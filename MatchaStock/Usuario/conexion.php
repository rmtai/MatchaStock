<?php

$host = "localhost";
$username = "root";
$password = "";
$database = "matchastockdb";

$mysql = new mysqli($host, $username, $password, $database);

// Verificar si ocurrió un error durante la conexión
if ($mysql->connect_error) {
    die("Error de conexión: " . $mysql->connect_error);
} else {
    echo "Conexión exitosa";
}

?>