<?php
	/*
    	MileageTracker
    */
        
	include("common.php");
    // sessionNotSet();
	
	// get the action url, and return the post array
	// to what the airline would expect
	$actionurl = trim($_POST['MTForward']);
	unset($_POST['MTForward']);
	
	$content = request($actionurl);
	
	print_r($content);
	
	
	
function request($url) {
	$ch = curl_init($url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $_POST);
	
	curl_setopt($ch, CURLOPT_VERBOSE, 1);
	curl_setopt($ch, CURLOPT_USERAGENT, 'User-Agent: curl/7.39.0');

	$result = curl_exec($ch);
	$statuscode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
	$statustext = curl_getinfo($ch);
	curl_close($ch);
	if($statuscode!=200){
		echo "HTTP ERROR ".$statuscode."<br>";
		echo "<pre>";
		echo var_dump($statustext);
		echo "</pre>";
		return "false";
	}
	return $result;
}
	
?>