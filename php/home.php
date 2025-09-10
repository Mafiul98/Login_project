<?php

$json = file_get_contents('php://input');
$data = json_decode($json,true);

$key = $data['key'];
$email = $data['email'];
$decryptedkey = decrypdata($key);

if($decryptedkey=='mafi223344' && strlen ($email)>0){

       $temp = array();
       $con = mysqli_connect('mafiul.shop','wwehckqz_mafiul','17xHw8s^dRAK','wwehckqz_new_database');
       $sql = "SELECT * FROM my_table WHERE email LIKE '$email'";
       $tvresult = mysqli_query($con,$sql);
       
       while ($row = mysqli_fetch_assoc($tvresult)){
           $name = $row ['name'];
           $image = $row ['image'];
           $email = $row ['email'];
           
           $temp ['result'] = $name . 'Email:' . $email;
           $temp ['image'] = 'https://www.mafiul.shop/'.$image;
           
       }
       
       echo json_encode($temp);
       
       
}
function decrypdata($text){
    $decode = base64_decode($text);
    $key = substr('mafi#12345678901', 0, 16);
    $decrypted = openssl_decrypt($decode, 'AES-128-ECB', $key, OPENSSL_RAW_DATA);
    return $decrypted;
}

?>
