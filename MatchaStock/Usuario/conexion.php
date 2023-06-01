<?php
$hostname = 'localhost';
$database = 'matchastockdb';
$username = 'root';
$password = '';

$mysql = new mysqli($hostname, $username, $password, $database);
if ($mysql->connect_error) {
    die('Error de conexión a la base de datos: ' . $mysql->connect_error);
}
?>