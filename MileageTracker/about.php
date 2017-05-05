<?php
	/* MileageTracker */

	include("common.php");
	sessionSet();
	head();
?>

    <div class="headline container-fluid">
        <h2>About</h2>
        <p class="about-text">
            MileageTracker helps people manage their airline frequent flyer miles. Our solution securely stores the user’s credentials for each individual airline and pulls the amount of their frequent flyer miles in to MileageTracker, which conveniently displays all of the user’s mileage balances across all airlines in one location. In addition, the expiration date for each set of miles is displayed next to the balance and therefore the user is able to easily view their balances and each of the expiration dates.
        </p>
        <p class="about-text">
            Our solution automatically sends users emails notifying them when the miles are due to expire - users can set a certain timeframe for notification of when the miles will expire. In summary, MileageTracker ensures that users never lose their hard-earned frequent flyer miles by providing users a convenient layout which features their mileage balances and their expiration dates, with alerts to notify them when they are set to expire.
        </p>
    </div>

<?= footer() ?>