<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #001f3f;"> <!-- Navy blue background -->
    <div class="container-fluid">
        <!-- Logo with rounded corners -->
        <a class="navbar-brand" href="reservations?action=viewAdminPanel">
            <img src="allComponents/img/logo.jpg" alt="Logo" style="border-radius: 50%; width: 50px; height: 50px; background-color: white; padding: 5px;">
        </a>

        <!-- Navbar Toggler for mobile view -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Collapsible menu links -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link text-white" href="reservations?action=newReservation">Add Reservation</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="reservations?action=viewAdminPanel">Daily Reservations</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="reservations?action=viewAllReservations">All Reservations</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="reservations?action=viewCustomerDetails">Customer Details</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
