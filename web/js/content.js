//document.getElementById("start").style.display = "none";
//document.getElementById("lobby").style.display = "none";
//document.getElementById("marketing").style.display = "none";
//document.getElementById("forschung").style.display = "none";
//document.getElementById("produktion").style.display = "none";
//document.getElementById("bericht").style.display = "none";

function start () {
    console.log("START");    
}

function lobby (game_id, player, socket) {
    console.log("LOBBY");
    
    window.location.href = "lobby.html";
    document.getElementById("lobby_game_id").value = game_id;
    
    if (player !== "")
    document.getElementById("lobby_player").textContent = "Spieler: " + JSON.stringify(player);
}

function game () { 
    window.location.href = "game.html";
   
}

var validparameter= function(){
     // hier müssen nun alle input Felder überprüft werden und geschaut werden ob 
    // alle eingaben valide sind
    
     var mar_pl = 0;
     if(parseInt($('#mar_pla').get(0).value)!==null){
         mar_pl=parseInt($('#mar_pla').get(0).value);
     };
     var mar_tv = 0;
     if(parseInt($('#mar_tvw').get(0).value)!==null){
         mar_tv=parseInt($('#mar_tvw').get(0).value);
     };
     var mar_ra = parseInt($('#mar_rad').get(0).value);
     
     var for_bi = parseInt($('#for_bie').get(0).value);
     var for_ch = parseInt($('#for_che').get(0).value);
     var for_we = parseInt($('#for_wei').get(0).value);
     
     var kapital = parseInt($('#kapital').get(0).value);
     var ausgaben = mar_pl + mar_tv + mar_ra + for_bi + for_ch + for_we;
     
     
     
     console.log(mar_pl);
     console.log(mar_tv);
     console.log(ausgaben);
     console.log(kapital);
     
     if((kapital-ausgaben)<0){
         alert("Zu viele Ausgaben");
     }
};



function finish(){
   
    validparameter();
    
    
   // window.location.href = "report.html";
}