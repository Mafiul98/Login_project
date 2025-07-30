<?php

$email = $_POST['email'];
$password = $_POST['password'];
$name = $_POST['name'];
$image64 = $_POST['image'];

file_put_contents('debug.txt', $image64);
$decodedImage = base64_decode($image64);

$filename = time(). '_'.rand(1000,100000).'.jpg';
$filepath = 'images/'.$filename;


if (file_put_contents($filepath, $decodedImage)) {
   $con = mysqli_connect('mafiul.shop','wwehckqz_mafiul','17xHw8s^dRAK','wwehckqz_new_database');
   $sql ="INSERT INTO my_table (email,password,name,image) VALUES ('$email','$password','$name','$filepath')" ;
   $result = mysqli_query($con,$sql);
   if($result){
       echo 'Succesful';
   }else{
       echo 'Error';
   }
   
} else {
    echo 'Image Upload Error';
}

?>