<?php
	/*
		MileageTracker
	*/

	include ("common.php");
	sessionNotSet();
	head();
	
	$user = $_SESSION["name"];

	// connect to DB
	$connect = dbConnect();
	
	$sql = "SELECT P.PassengerFName, P.PassengerLName, P.PassengerEmail, P.PassengerPhoneNumber, P.AlertDays, P.AlertEmail, P.AlertPhone, C.Username, C.Password
			FROM Passenger P
			JOIN Credentials C ON P.PassengerID = C.PassengerID
			WHERE C.Username = '".$user."'";
	
	$result = $connect->query($sql);

	if (!$result->num_rows === 1) {
		die();
	}
?>
	<div id="main">
        <h2><?= "$user" ?>'s Profile</h2>

<?php

	echo "<div class=\"headline container-fluid\"><div class=\"row\"><div class=\"col-sm-6\">";
	  echo "<div id=\"register\">
        <div class=\"form\">
          <h2>Contact Form </h2>
          <form id=\"regform\"  method=\"post\">
          <input type=\"text\" name=\"Your full Name\" placeholder='Your full Name' />
          <input type=\"email\" name=\"Your Email\" placeholder='Your contact Email'/>
		   <span></span><select name='selection'>
			<option value='Job Inquiry'>Job Inquiry</option>
			<option value='General Question'>General Question</option>
			</select>
			<input type =\"text\"  placeholder='Your Message to Us'> </input>
      	   <input type=\"submit\" value=\"submit\" /></form></div></div>";

    echo "</div>";

	// echo "<form action='' method='post' class='STYLE-NAME'>
    // 	<h3>Contact Form</h3>
    // 	<label>
    //     <span>Your Name :</span>
	// 		<input id='name' type='text' name='name' placeholder='Your Full Name' />
	// 	</label>
		
	// 	<label>
	// 		<span>Your Email :</span>
	// 		<input id='email' type='email' name='email' placeholder='Valid Email Address' />
	// 	</label>
		
	// 	<label>
	// 		<span>Message :</span>
	// 		<textarea id='message' name='message' placeholder='Your Message to Us'></textarea>
	// 	</label> 
	// 	<label>
	// 		<span>Subject :</span><select name='selection'>
	// 		<option value='Job Inquiry'>Job Inquiry</option>
	// 		<option value='General Question'>General Question</option>
	// 		</select>
	// 	</label>    
	// 	<label>
	// 		<span>&nbsp;</span> 
	// 		<input type='button' class='button' value='Send' /> 
	// 	</label>    
	// 	</form>";

	// echo "<input type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "<div class=\"headline container-fluid\"><div class=\"row\"><div class=\"col-sm-6\">";
     $row = mysqli_fetch_assoc($result);
    echo "<div id=\"register\">
        <div class=\"form\">
          <h2>My Info</h2>
          <form id=\"regform\" action=\"update_profile.php\" method=\"post\">
          <input type=\"text\" name=\"PassengerFName\" placeholder=\"".$row['PassengerFName']."\" />
          <input type=\"text\" name=\"PassengerLName\" placeholder=\"".$row['PassengerLName']."\" />
          <input type=\"email\" name=\"PassengerEmail\" placeholder=\"".$row['PassengerEmail']."\" />
          <input type=\"number\" name=\"PassengerPhoneNumber\" placeholder=\"".$row['PassengerPhoneNumber']."\" />
           <input type=\"password\" name=\"PassengerPassword\" placeholder=\"".$row['Password']."\" />
      		<input type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "</div>";


    echo "<div class=\"col-sm-6\">";
    echo "<div id=\"register\">
        <div class=\"form\">
          <h2>My Preferences</h2>
          <form id=\"regform\" action=\"update_preferences.php\" method=\"post\">
<input type=\"text\" name=\"AlertDays\" placeholder=\"Start Alert This Many Days Before Expiration: ".$row['AlertDays']."\" />";

	$emailAlert = $row['AlertEmail'];
	$phoneAlert = $row['AlertPhone'];

	if ($emailAlert == 1) {
		echo"<input type=\"checkbox\" name=\"AlertEmail\" value=\"Email\" checked=\"checked\">Email Alerts<br>";
	} else {
		echo"<input type=\"checkbox\" name=\"AlertEmail\" value=\"Email\">Email Alerts<br>";
	}

	if ($phoneAlert == 1) {
		echo"<input type=\"checkbox\" name=\"AlertPhone\" value=\"Phone\" checked=\"checked\">Phone Alerts<br>";
	} else {
		echo"<input type=\"checkbox\" name=\"AlertPhone\" value=\"Phone\">Phone Alerts<br>";
	}

    echo "<input type=\"submit\" value=\"Update\" /></form></div></div>";

    echo "</div></div></div></div>";


    
footer();
//<input type=\"text\" name=\"PassengerUserName\" placeholder=\"".$row['Username']."\" />
?>
            
            
            
            
       
