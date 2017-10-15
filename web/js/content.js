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
    
    

    var kap = document.getElementById("kapital");
    if (kap !== null && kap.value === ""){
      kapital=0;
    }else kapital = parseInt(kap.value);
    
    var ausgaben = mar_pl + mar_tv + mar_ra + for_bi + for_ch + for_we;
     
     
    console.log(mar_pl);
    console.log(mar_tv);
    console.log(mar_ra);
    
    console.log(for_bi);
    console.log(for_ch);
    console.log(for_we);
    
    
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