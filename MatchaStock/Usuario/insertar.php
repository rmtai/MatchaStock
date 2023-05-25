<?php
    if($_SERVER["REQUEST_METHOD"]== "POST"){
        require_once 'conexion.php';
        $nombre= $_POST["nombre"];
        $apellido=$_POST["apellido"];
        $username= $_POST["username"];
        $passwordUser=$_POST["passwordUser"];
        $email=$_POST["email"];
        $my_query = "insert into usuario(nombre, apellido, username, passwordUser, email) values ('".$nombre."', '".$apellido."', '".$username."', '".$passwordUser."', '".$email."')";
        $result = $mysql->query($my_query);
        if ($result == true) {
            echo "Realizado con exito";
        }else{
            echo "error";
        }
    }
?>