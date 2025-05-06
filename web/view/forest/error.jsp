<head>
    <!-- Import the Silkscreen font -->
    <link href="https://fonts.googleapis.com/css2?family=Silkscreen&display=swap" rel="stylesheet">

    <style>
        @keyframes flash {
            0% {
                top: -75px;
                opacity: 0;
            }
            5% {
                top: 25px;
                opacity: 1;
            }
            90% {
                top: 25px;
                opacity: 1;
            }
            100% {
                top: -75px;
                opacity: 0;
            }
        }

        .flash-message {
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
            height: 50px;
            width: 500px;
            box-sizing: border-box;
            position: absolute;
            top: -50px;
            left: calc(50vw - 250px);
            font: 600 1rem/1rem 'Silkscreen', sans-serif;
            color: #155724;
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: .25rem;
            animation-name: flash;
            animation-duration: 3.5s;
            animation-delay: 0.3s;
            animation-iteration-count: 1;
            z-index: 10000;
        }

        .flash-message__text {
            margin-left: 10px;
        }
    </style>
</head>

<body>
    <div class="flash-message">
        <i class="fa fa-check" aria-hidden="true"></i>
        <span class="flash-message__text">User ID not found! Cannot visit.</span>
    </div>

    
</body>
