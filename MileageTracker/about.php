<?php
	/* MileageTracker */

	include("common.php");
	sessionSet();
	head();
?>

    <div class="headline container-fluid">
        <div class="main-icons">
        <div class="col-sm-4">
          <img class="main-icon" src="images/time.png" /><br>
          Miles Expire
        </div>
         <div class="col-sm-4">
          <img class="main-icon" src="images/travel.png" /><br>
          Rules Differ
        </div>
        <div class="col-sm-4">
          <img class="main-icon" src="images/card.png" /><br>
          Difficult Redemptions
        </div>
      </div>
        <p class="about-text">
            MileageTracker helps manage airline frequent flyer miles. Our solution securely stores the user’s credentials for each individual airline and pulls the amount of their frequent flyer miles in to MileageTracker, which conveniently displays all of the user’s mileage balances across all airlines in one location. In addition, the expiration date for each set of miles are displayed next to the balance and therefore the user is able to easily view their balances and each of the expiration dates.
        <br>
        <br>
            Our solution automatically sends users email and text message alerts notifying them when the miles are set to expire. In summary, MileageTracker ensures that users never forefit their hard-earned frequent flyer miles by providing a convenient layout which features their mileage balances and expiration dates, with alerts to notify users when their miles are set to expire.
        </p>
        <br>
    </div>

<?= footer() ?>