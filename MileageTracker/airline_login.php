<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();
	head();
	
	$airline = trim($_GET["airline"]);
	
	if (empty($airline)) {
		die("Invalid airline");
	}
	
	// connect to DB
	$connect = dbConnect();
	
	// query the airline urldecode
	$sql = "SELECT Airline_URL.AirlineURL AS Url, Airline_URL.AirlineURLFormIndex AS Ind FROM Airline_URL JOIN Airline ON Airline_URL.AirlineURLID = Airline.AirlineLoginURLID WHERE Airline.AirlineCode = '$airline'";
	
	$result = $connect->query($sql);
		
	if ($result->num_rows === 1) {
				
		// get the url from the result
		$resarray = $result->fetch_assoc();
		$url = $resarray['Url'];
		$index = $resarray['Ind'];
		
		$content = request($url);
		
		$forms = array();
		
		// parse the website content to get form elements
		// difficult to do this in other ways, due to memory limits
		$start = 0;
		$end = 0;
		while(true) {
			$start = strpos($content, "<form ", $end);
			if ($start === false) {
				break;
			}
			
			$end = strpos($content, "</form>", $start);
			if ($end === false) {
				break;
			}
			
			$forms[] = substr($content, $start, $end - $start) . "</form>";
		}
		
		$form = $forms[$index];
		
		// save the url to send their info to
		if (preg_match('/action="(.*?)"/', $form, $matches)) {
			$actionurl = $matches[1];
			
			// if the url is not absolute, fix that
			if (preg_match('/\.com/', $actionurl) == false) {
				preg_match('/.*?\.com/', $url, $matches);
				$actionurl = $matches[0] . $actionurl;
			}
			
			// replace the action url with something on our website
			$form = preg_replace('/action=".*?"/', 'action="/rperry12/MileageTracker/airline_complete.php"', $form);
			
			// add the original action url to the form
			$form = preg_replace('/>/', '><input hidden type="text" name="MTAirlineCode" value="' . $airline . '" >', $form, 1);
			

		} else {
			echo "could not find action";
		}
		
		echo $form;
		
	} else {
		echo "login page not found";
	}

function request($url){
	$ch = curl_init($url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
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