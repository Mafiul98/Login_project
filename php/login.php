<?php

$email = $_POST['email'];
$password = $_POST['password'];
$key = $_POST['key'];

$decryptedkey = decrypdata($key);
$dec_email = decrypdata($email);

if($decryptedkey == 'mafi223344' && strlen ($email)>0){
    
    $con = mysqli_connect('mafiul.shop','wwehckqz_mafiul','17xHw8s^dRAK','wwehckqz_new_database');
    $sql22 = "SELECT * FROM my_table WHERE email = '$dec_email' AND password = '$password'";
    $result = mysqli_query($con,$sql22);
    $rows = mysqli_num_rows($result);
}

if($rows>0){
    echo 'VALID LOGIN';
}else{
    
    echo 'Login Error';
    
}


function decrypdata($text){
    $decode = base64_decode($text);
    $key = substr('mafi#12345678901', 0, 16);
    $decrypted = openssl_decrypt($decode, 'AES-128-ECB', $key, OPENSSL_RAW_DATA);
    return $decrypted;
}


?>
