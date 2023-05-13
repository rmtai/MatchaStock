<?php

$mysql = new mysqli("localhost", "root", "", "matchastockdb");
if($mysql->connect_error){
    echo"Error: ";
    die("error de conexion");
}
else{
    echo"Conexion exitosa";
}
?>