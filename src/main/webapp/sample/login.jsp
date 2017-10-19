<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <script>
        var settings = {
            "async": true,
            "crossDomain": true,
            "beforeSend" : function (xhr) {
                console.log(xhr);
                xhr.setRequestHeader ("Authorization", "Basic " + "YmlsbDphYmMxMjM=");
            },
            "url": "https://localhost:8443/oauth/token",
            "method": "POST",
            "headers": {
                "content-type": "application/x-www-form-urlencoded"
            },
            "processData": false,
            "data": "grant_type=password&username=bill&password=abc123&client_id=my-trusted-client&client_secret=secret"
        };

        // curl client:secret@localhost:8080/oauth/token -d grant_type=password -d username=bill -d password=abc123

        // "application/json"
        // "data": "{\"grant_type\":\"password\",\"username\": \"bill\",\"password\": \"abc123\",\"client_id\": \"my-trusted-client\",\"client_secret\": \"secret\"}"
        // Authorization: Basic YmlsbDphYmMxMjM=

        // "data": "{\"grant_type\":\"client_credentials\",\"client_id\": \"YOUR_CLIENT_ID\",\"client_secret\": \"YOUR_CLIENT_SECRET\",\"audience\": \"https://api.example.com/geocoding/v1/\"}"

        $.ajax(settings)
            .done(function (response) {
                console.log(response);
            })
            .fail(function(e) {
                console.log(e);
            });
    </script>
</head>
<body>
This is the login page.
</body>
</html>