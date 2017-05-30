<?php
	/*
    	MileageTracker
    */
        
	include("common.php");
    sessionNotSet();
    head();

	$user = $_SESSION["name"];
?>

    <div id="main">
    <div class="margin">
     <h3 class="clear">Alerts</h3></div>

     

<?php

    // connect to DB
    $connect = dbConnect();

    $sql = "SELECT A.AirlineName, A.AirlineCode, PA.MileageBalance, PA.MileageBalanceExpirationDate
            FROM Passenger_Airline PA
            JOIN Airline A ON PA.AirlineID = A.AirlineID
            JOIN Credentials C ON PA.PassengerID = C.PassengerID
            WHERE C.Username = '".$user."'
            ORDER BY PA.MileageBalanceExpirationDate ASC
            LIMIT 2";

  $result = $connect->query($sql);

   
  echo "<div class=\"clear\"><ul id=\"exp-list\">";

  if ($result->num_rows > 0) {
  while($row = mysqli_fetch_assoc($result)) {

  echo "<li class=\"margin\">".$row['MileageBalance']. " ".$row['AirlineName']." miles will expire on ".$row['MileageBalanceExpirationDate']."</li>";

} 
} else {
    echo "<li class=\"margin\">No alerts because you aren't tracking any miles.</li>";
}

echo "</ul></div>";


    // query miles for this user
    $sql = "SELECT A.AirlineName, A.AirlineCode, PA.MileageBalance, PA.MileageBalanceExpirationDate
            FROM Passenger P
                JOIN Passenger_Airline PA ON P.PassengerID = PA.PassengerID
                JOIN Airline A ON PA.AirlineID = A.AirlineID
                JOIN Credentials C ON P.PassengerID = C.PassengerID
            WHERE C.Username = '".$user."'
            ORDER BY PA.MileageBalanceExpirationDate ASC";

    $result = $connect->query($sql);

    echo "<div class=\"margin\"><h3 class=\"clear\">My Miles</h3></div>";

    if ($result->num_rows > 0) {
    
        echo "<div class=\"clear\"><div id=\"milestable\"><table>
                <tr id=\"first\">
                    <th class=\"mobile\">Airline</th>
                    <th>AL</th>
                    <th>Miles</th>
                    <th>Exp. Date</th>
                    <th>Actions</th>
                </tr>";

        while($row = mysqli_fetch_assoc($result)) {
           echo "<tr>";
           echo "<td class=\"mobile\">".$row['AirlineName']."</td>";
           echo "<td>".$row['AirlineCode']."</td>";
           echo "<td>".$row['MileageBalance']."</td>";
           echo "<td>".$row['MileageBalanceExpirationDate']."</td>";
           echo "<td><a href=\"delete_airline.php?airline=".$row['AirlineCode']."\">Remove</a></td>";
           echo "</tr>";
        }

        echo "</table></div></div>";

    } else {
        echo "<h3 id=\"not-tracking\" class=\"center-points\"><span class=\"margin\">You are currently not tracking<br> any miles, please select an airline<br> below to add a new balance.</span></h3>";
    }

    $connect = dbConnect();

    $sql = "SELECT AirlineName, AirlineCode
            FROM Airline
			WHERE ValidURL=1
            ORDER BY AirlineName ASC";

    $result = $connect->query($sql);

    
   echo "
 <div class=\"form-new-balance\">
        <div class=\"form\">
       
        <h2>Add a New Balance</h2>
            <form id=\"regform\" action=\"airline_login2.php\">
                <select name=\"airline\">";

    while($row = mysqli_fetch_assoc($result)) {
        echo "<option value=\"".$row['AirlineCode']."\">".$row['AirlineName']."</option>";

    }

    echo "</select><input type=\"submit\" value=\"Add\"></form></div></div>";
?>
    </div>
    
<?= footer() ?>