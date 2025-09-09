<?php

   
function decrypdata($text){
    $decode = base64_decode($text);
    $key = substr('mafi#12345678901', 0, 16);
    $decrypted = openssl_decrypt($decode, 'AES-128-ECB', $key, OPENSSL_RAW_DATA);
    return $decrypted;
}

$email = $_POST['email'] ?? '';
$password = $_POST['password'] ?? '';
$name = $_POST['name'] ?? '';
$image64 = $_POST['image'] ?? '';
$key = $_POST['key'] ?? '';


$security_key = decrypdata($key);
$dec_email = decrypdata($email);


if ($security_key == 'mafi223344' && strlen($dec_email) > 0 && strlen($password) > 0) {
    
    
    $con = mysqli_connect('mafiul.shop','wwehckqz_mafiul','17xHw8s^dRAK','wwehckqz_new_database');
    $sql22 = "SELECT * FROM my_table WHERE email LIKE '$dec_email' ";
    $result = mysqli_query($con,$sql22);
    $rows = mysqli_num_rows($result);
    
 if ($rows<=0){  
     
file_put_contents('debug.txt', $image64);
$decodedImage = base64_decode($image64);
$filename = time(). '_'.rand(1000,100000).'.jpg';
$filepath = 'images/'.$filename;

if (file_put_contents($filepath, $decodedImage)) {
   $sql ="INSERT INTO my_table (email,password,name,image) VALUES ('$dec_email','$password','$name','$filepath')" ;
   $result = mysqli_query($con,$sql);
   if($result){
       echo 'Succesful';
   }else{
       echo 'Error';
   }
   
}else{
    echo "Image save failed"; 
}


}else{
    
    echo 'Already available! Change email and try egain';
    
}

   
   //=====================================

   
} 

?>