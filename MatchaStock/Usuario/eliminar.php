<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    require_once 'conexion.php';
    $idUser = $_POST["idUser"];

    // Eliminar los registros relacionados en la tabla "producto"
    $deleteProductoQuery = "DELETE FROM producto WHERE idUser = $idUser";
    $deleteProductoResult = $mysql->query($deleteProductoQuery);

    if ($deleteProductoResult) {
        // Los registros de "producto" se eliminaron correctamente, ahora se puede eliminar el usuario
        $deleteUsuarioQuery = "DELETE FROM usuario WHERE idUser = $idUser";
        $deleteUsuarioResult = $mysql->query($deleteUsuarioQuery);

        if ($deleteUsuarioResult) {
            echo 'Usuario eliminado exitosamente';
        } else {
            echo 'Error al eliminar el usuario';
        }
    } else {
        echo 'Error al eliminar los registros de producto';
    }
} else {
    echo 'unknown error';
}


?>