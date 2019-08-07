function setMatchUser(id) {
    jQuery.get("/app/matchUser", {key:id});
}

$(function () {

   $( "#matchUser" ).click(function ()
      {

          setMatchUser($("#UserQueueID").val());});

    console.log("duck");
});