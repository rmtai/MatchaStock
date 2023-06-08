<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idUser = $_GET["idUser"];
    $my_query = "DELETE from usuario where idUser = $idUser";
    $result = $mysql -> query($my_query);

    if($result == true){
        echo "Usuario eliminado satisfactoriamente...";
    }else{
        echo "Error al eliminar usuario...";
    }

}else{
    echo"Error desconocido";
}
?>