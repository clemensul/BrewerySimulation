//document.getElementById("start").style.display = "none";
//document.getElementById("lobby").style.display = "none";
//document.getElementById("marketing").style.display = "none";
//document.getElementById("forschung").style.display = "none";
//document.getElementById("produktion").style.display = "none";
//document.getElementById("bericht").style.display = "none";

function start () {
    console.log("START");    
}

function ChangeToLobby (game_id, player, admin) {
    console.log("Content: LOBBY");
    
    localStorage.setItem("game_id", game_id);
    localStorage.setItem("player", JSON.stringify(player));
    localStorage.setItem("admin", admin);

    window.location.href = "lobby.html";
}

function ChangeToReport(){
  window.location.href = "report.html";
}

function ChangeToGame () { 
    window.location.href = "game.html";
   
}

var validparameter= function(){
    // hier müssen nun alle input Felder überprüft werden und geschaut werden ob 
    // alle eingaben valide sind
    // man könnte auch alle Eingabefelder mit 0 vorinitalisieren
    
    var temp = document.getElementById("mar_pla");
    if (temp !== null && temp.value === ""){
      mar_pl=0;
    }else mar_pl = parseInt(temp.value);
    
    var temp = document.getElementById("mar_tvw");
    if (temp !== null && temp.value === ""){
      mar_tv=0;
    }else mar_tv = parseInt(temp.value);
    var temp = document.getElementById("mar_rad");
    if (temp !== null && temp.value === ""){
      mar_ra=0;
    }else mar_ra = parseInt(temp.value);
     
    var temp = document.getElementById("for_bie");
    if (temp !== null && temp.value === ""){
      for_bi=0;
    }else for_bi = parseInt(temp.value);
    var temp= document.getElementById("for_che");
    if (temp !== null && temp.value === ""){
      for_ch=0;
    }else for_ch = parseInt(temp.value);
    var temp= document.getElementById("for_wei");
    if (temp !== null && temp.value === ""){
      for_we=0;
    }else for_we = parseInt(temp.value);
    
    // Kapital abrufen und testen. Testen wird aber unnötig, weil es nachher kein eingabe Feld mehr ist

    var kap = document.getElementById("kapital");
    if (kap !== null && kap.value === ""){
      kapital=0;
    }else kapital = parseInt(kap.value);
    
    var ausgaben = mar_pl + mar_tv + mar_ra + for_bi + for_ch + for_we;


    // test ob ich zu viele Ausgaben hätte
    // später vllt ummodeln mit Kredtvergabe oder so
     
     if((kapital-ausgaben)<0){ 
         return false;
     }else return true;
};