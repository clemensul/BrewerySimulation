/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var array_input=["mar_pla","mar_tvw","mar_rad","for_bie","for_che","for_wei"];
$(document).ready(function() {
    for(i=0; i<array_input.length-1;i++){
        $("#"+array_input[i]).keydown(function (e) {
            // Allow: backspace, delete, tab, escape, enter and .
            if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                 // Allow: Ctrl/cmd+A
                (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
                 // Allow: Ctrl/cmd+C
                (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
                 // Allow: Ctrl/cmd+X
                (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
                 // Allow: home, end, left, right
                (e.keyCode >= 35 && e.keyCode <= 39)) {
                     // let it happen, don't do anything
                     return;
            }
            // Ensure that it is a number and stop the keypress
            if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                e.preventDefault();
            }
        });
    }
});


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


var send_game_data= function(){
    var Input = {
            action: "submit",
            cookie: document.cookie,
            marketing1 : parseInt(document.getElementById("mar_pla").value),
            marketing2 : parseInt(document.getElementById("mar_tvw").value),
            marketing3 : parseInt(document.getElementById("mar_rad").value),
            development1 : parseInt(document.getElementById("for_bie").value),
            development2 : parseInt(document.getElementById("for_che").value),
            development3 : parseInt(document.getElementById("for_wei").value), 
        // Ausgaben
        //  
        };
    
    socket.send(JSON.stringify(Input));
    console.log(JSON.stringify(Input));
};

