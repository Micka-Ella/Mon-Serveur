<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="get.php" method="get">
        <input type="text" name="nom">
        <input type="submit" value="Valider (get)">
    </form>
    <form action="post.php" method="post">
        <input type="text" name="prenom">
        <input type="submit" value="Valider (post)">
    </form>
</body>
</html>
<!-- <!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire GET et POST</title>
</head>
<body>

<h1>Formulaire avec méthodes GET et POST</h1>

<form method="GET">
    <label for="nom">Nom :</label>
    <input type="text" id="nom" name="nom" required>
    <br><br>
    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required>
    <br><br>
    <input type="submit" value="Envoyer avec GET">
</form>

<hr>

<form method="POST">
    <label for="message">Message :</label>
    <textarea id="message" name="message" required></textarea>
    <br><br>
    <input type="submit" value="Envoyer avec POST">
</form>

<hr>

<h2>Données envoyées</h2>

<?php
// Si des données ont été envoyées par GET
if (isset($_GET['nom']) && isset($_GET['email'])) {
    echo "<h3>Informations envoyées par GET :</h3>";
    echo "Nom : " . $_GET['nom'] . "<br>";
    echo "Email : " . $_GET['email'] . "<br>";
}

// Si des données ont été envoyées par POST
if (isset($_POST['message'])) {
    echo "<h3>Message envoyé par POST :</h3>";
    echo "Message : " . $_POST['message'] . "<br>";
}
?> -->

</body>
</html>
