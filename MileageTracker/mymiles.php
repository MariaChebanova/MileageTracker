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

    <div>
        <a href="logout.php"><strong>Log Out</strong></a>
    </div>
</div>
<?= footer() ?>