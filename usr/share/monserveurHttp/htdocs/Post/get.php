<?php 
// echo $_POST['prenom'] ;

echo $_GET['nom'] ;
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <p><?php echo "GET: Bonjour, ".$_GET['nom'] . " !</br>"; ?></p>   
    <a href="index.php">retour</a>
</body>
</html>
