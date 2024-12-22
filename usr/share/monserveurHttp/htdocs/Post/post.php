<?php
echo "Bonjour, ceci est une page PHP servie via un serveur Java !";
echo $_POST['prenom'];
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <p><?php echo "POST: Bonjour," . $_POST['prenom'] . " !"; ?></p>
    <a href="index.php">retour</a>

</body>
</html>