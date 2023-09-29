<?php
$claveOriginal = "123456";
$claveEncriptada = password_hash($claveOriginal, PASSWORD_BCRYPT);

var_dump($claveEncriptada);