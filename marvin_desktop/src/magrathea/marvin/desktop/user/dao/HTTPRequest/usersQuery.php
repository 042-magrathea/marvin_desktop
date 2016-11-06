<?php

// Ejemplo Retorno Users

$result = <<<EOT
[{
    "idUser": "1",
    "publicName": "ARNAU",
    "password": "1234",
    "e-mail": "arnau@magrathea.com",
    "administrator": "true"
}, {
    "idUser": "2",
    "publicName": "IVÁN",
    "password": "2345",
    "e-mail": "ivan@magrathea.com",
    "administrator": "true"
}, {
    "idUser": "3",
    "publicName": "VILLA",
    "password": "3456",
    "e-mail": "villa@magrathea.com",
    "administrator": "true"
}]
EOT;
echo $result;