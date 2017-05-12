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
    <h2><?= "$user" ?>'s Miles</h2>

<?php

    // connect to DB
    $connect = dbConnect();

    // query miles for this user
    $sql = "SELECT A.AirlineName, A.AirlineCode, PA.MileageBalance, PA.MileageBalanceExpirationDate
            FROM Passenger P
                JOIN Passenger_Airline PA ON P.PassengerID = PA.PassengerID
                JOIN Airline A ON PA.AirlineID = A.AirlineID
                JOIN Credentials C ON P.PassengerID = C.PassengerID
            WHERE C.Username = '".$user."'";

    $result = $connect->query($sql);

    echo "<div id=\"milestable\"><table>
            <tr>
                <th>AirlineName</th>
                <th>AirlineCode</th>
                <th>MileageBalance</th>
                <th>MileageBalanceExpirationDate</th>
            </tr>";

    while($row = mysqli_fetch_assoc($result)) {
       echo "<tr>";
       echo "<td>".$row['AirlineName']."</td>";
       echo "<td>".$row['AirlineCode']."</td>";
       echo "<td>".$row['MileageBalance']."</td>";
       echo "<td>".$row['MileageBalanceExpirationDate']."</td>";
       echo "</tr>";
}

    echo "</table></div>";


?>

    <div>

    </div>
<!--
    <ul id="miles">
        <?php
            # if user's miles doesn't exist, make it
            ini_set("auto_detect_line_endings", true);
            if (!file_exists("miles$user.txt")) {
                file_put_contents("miles$user.txt", "");
            }
            $list = file("miles$user.txt");
            
            for ($i = 0; $i < count($list); $i++) {
        ?>
            <li>
                <form action="submit.php" method="post">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="index" value="<?= $i ?>" />
                    <input type="submit" value="Delete" />
                </form>
                <?= htmlspecialchars($list[$i]) ?>
            </li>
        <?php
            }
        ?>
        
        <li>
            <form action="submit.php" method="post">
                <input type="hidden" name="action" value="add" />
                <input name="item" type="text" size="25" autofocus="autofocus" />
                <input type="submit" value="Add" />
            </form>
        </li>
    </ul>
    -->

    <div>
        <a href="logout.php"><strong>Log Out</strong></a>
    </div>
</div>
<?= footer() ?>