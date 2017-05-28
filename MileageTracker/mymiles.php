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
  while($row = mysqli_fetch_assoc($result)) {

  echo "<li>".$row['MileageBalance']. " ".$row['AirlineName']." miles will expire on ".$row['MileageBalanceExpirationDate']."</li>";

}

echo "</ul></div>";


    // query miles for this user
    $sql = "SELECT A.AirlineName, A.AirlineCode, PA.MileageBalance, PA.MileageBalanceExpirationDate
            FROM Passenger P
                JOIN Passenger_Airline PA ON P.PassengerID = PA.PassengerID
                JOIN Airline A ON PA.AirlineID = A.AirlineID
                JOIN Credentials C ON P.PassengerID = C.PassengerID
            WHERE C.Username = '".$user."'";

    $result = $connect->query($sql);

    echo "<div class=\"margin\"><h3 class=\"clear\">My Miles</h3></div>
    <div class=\"clear\"><div id=\"milestable\"><table>
            <tr id=\"first\">
                <th>Airline</th>
                <th>Balance</th>
                <th>Expiration</th>
                <th>Actions</th>
            </tr>";

    while($row = mysqli_fetch_assoc($result)) {
       echo "<tr>";
       echo "<td>".$row['AirlineName']." (".$row['AirlineCode'].")</td>";
       echo "<td>".$row['MileageBalance']."</td>";
       echo "<td>".$row['MileageBalanceExpirationDate']."</td>";
       echo "<td><a href=\"delete_airline.php?airline=".$row['AirlineCode']."\">Delete</a></td>";
       echo "</tr>";
    }

    echo "</table></div></div>";

    $connect = dbConnect();

    $sql = "SELECT AirlineName, AirlineCode
            FROM Airline
            ORDER BY AirlineName ASC";

    $result = $connect->query($sql);

    
   echo "

        <div class=\"form\">
        <h2>Add a New Balance</h2>
            <form id=\"regform\" action=\"airline_login2.php\">
                <select name=\"airline\">";

    while($row = mysqli_fetch_assoc($result)) {
        echo "<option value=\"".$row['AirlineCode']."\">".$row['AirlineName']."</option>";

    }

    echo "</select><input type=\"submit\" value=\"Add\"></form></div>";
?>
    </div>
    
<?= footer() ?>