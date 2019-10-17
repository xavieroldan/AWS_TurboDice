var urlServer="http://localhost:8084/AwsDiceGame/";
// var urlServer="http://turbodice-env.rwqv3piykm.eu-central-1.elasticbeanstalk.com/";
var urlRequest = "";
var myPlayer = { name : null, idPlayer : null};
var editedPlayer = { name : null, idPlayer : null };
var rateDTO= {"player":{"idPlayer":null,"name":null,"regDate":null,"listGame":[{"idGame":null,"isAnonim":false,"isWinner":false,"listDiceResult":[{"idDiceResult":null,"result":null}],"rate":null}]}};
var currentPenguin="";


//Create a new player
//✧*｡٩(ˊᗜˋ*)و✧*｡   
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function newplayer(){
     
    var myObj = { name : null }; 
    var name= document.getElementById("newname").value;
    myObj.name = name;
    var created=false;    
    $.ajax
            ({
        url: urlServer+'players',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(myObj),
        async: false,
        cache: false,
        processData: false,  
        success: function(data)
                    {                        
                        //save the idPlayer value 
                        document.getElementById("d1").innerHTML = "<br>"; 
                        myPlayer = data;
                        
                        //Change the return message
                        document.getElementById("d4").innerHTML="<p class='blinky'>Created player</p>";
                        created=true;                        
                    },
        error: function(xhr, ajaxOptions, thrownError)
                    {                        
                        document.getElementById("d1").innerHTML = "<br>";
                        created = false;
                        switch (xhr.status) 
                            {
                                case 409 : 
                                    document.getElementById("d4").innerHTML = "<p class='blinkr'>The player already exists:</p><p class='blinkr'>try again</p>";
                                    break;
                                default:   
                                    document.getElementById("d4").innerHTML= "<p class='blinkr'>Communications error:</p class='blink'><p class='blinkr'>try later</p>";
                            }
                    }        
            }); 

    if(created) 
        {
            playGame();            
        }                          
}

//Play new game menu
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function playGame()
{
   //Charge the game menu
    document.getElementById("h2").innerHTML="<div class='row justify-content-center mb-sm-3 mb-4 mt-sm-5' id='d1'><div class='col-12 col-sm-4 align-self-center'><a id='link' title='exit' href='./index.html'><img class='img-fluid' src='./exit.png' alt='exit'></a></div><div class='col-12 col-sm-4' id='penguin'><img class='img-fluid' src='./happy.gif' alt='penguin'></div><div class='col-12 col-sm-4 align-self-center'><a id='link' title='play' href='javascript:startGame();'><img class='img-fluid' src='./play.png' alt='play'></a></div></div><div class='row justify-content-center mb-sm-3 mb-4' id='d2'><p class='blinky'>Lets play!</p></div><div class='row justify-content-center mb-sm-3 mb-4' id='d3'></div><div class='row justify-content-center mb-sm-3 mb-4' id='d4'></div>";    
    }

//Play new game request

    function startGame(){    
    
        var name =""; 
        var dices = new Array(); 
        var resultTxt= "";   
        var winner = false;
        var penguin = "";
        var outputText="";

        //Play the game
        $.ajax
        ({
            url: urlServer+'players/'+myPlayer.idPlayer+"/games/",
            type: 'POST',
            async: false,
            cache: false,
            processData: false, 
            contentType: "application/json; charset=utf-8",
            dataType: "json", 
            success: function(player)
                {   
                    //Get the dice game result
                    var listGame = player.listGame; //Get the list of games
                    var numGames = listGame.length; //Get the number of games
                    numGames --;   //Get the current position game  
                    var lastGame = new Array();        
                    lastGame = listGame[numGames]; //Get the last game
                    winner = lastGame.isWinner;
                    //Get the result of the dices  
                    var listDiceResult = lastGame.listDiceResult; 
                    listDiceResult.forEach(
                            function(results)
                                {
                                    dices.push(results.result);                        
                                }
                        );                
                    //Get the player name
                    name = player.name;
                    //Change the return message
                    if(winner)
                    {
                        //Wins

                        outputText= "<span class='blinky'>You win!</span>";
                        if(currentPenguin==="./happy.gif")
                        {
                            penguin="./great.gif";
                        }
                        else
                        {
                            penguin="./happy.gif";
                        }                        
                    }
                    else
                    {
                        //Lost
                        outputText= "<span class='blinkr'>You lost!</span>";
                        if(currentPenguin==="./cry.gif")
                        {
                            penguin="./roll.gif";
                        }
                        else
                        {
                            penguin="./cry.gif";
                        } 
                    }
                   //Add the dice results
                   resultTxt="<span class='blinky'> | ";
                   dices.forEach(function(result) 
                       {
                       resultTxt += result+" | ";
                       });
                   resultTxt+="</span>";           
                },
            error: function(xhr, ajaxOptions, thrownError)
                {    
                    switch (xhr.status) 
                        {
                            case 409 : 
                                output = "<p class='blinkr'>It was not possible to locate the player "+name+"</p>";
                                break;
                            case 404 :
                                output = "<p class='blinkr'>Unable to play</p class='blinkr'>Error 404<p></p>" ;
                                break;  
                            default:   
                                output = "<p class='blinkr'>Communications error:</p><p class='blinkr'>try later</p>";
                        }
                }            
        });   
            //Change the output dice results
            document.getElementById("d2").innerHTML = resultTxt; 
            //Change the output win/lost
            document.getElementById("d3").innerHTML = outputText;         
            //Change the penguin image
            document.getElementById("penguin").innerHTML="<img class='img-fluid' src='"+penguin+"' alt='penguin'>";
            currentPenguin= penguin;
    }

//Form select players
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function generateList(){ 

    var playersList = new Array();
    var nameList = new Array();
    var urlHeader = urlServer+"getall";
    var isPlayerSet= false;    
    //Getting the list player's names 
    $.ajax
        ({
        url: urlHeader,
        type: 'GET',
        contentType: "application/json; charset=utf-8",        
        async: false,
        cache: false,
        processData: false,  
        success: function(data)
                {                        
                    //Read all the player's names
                    playersList = data;                    
                    playersList.forEach(
                        function(player) 
                            {                                
                                nameList.push(player.name);
                            }
                        );  
                    //Creating the select list 
                    var selectList 
                    ="<select id='select_list'><option value=''selected disabled hidden></option>";
                    nameList.forEach(
                        function(name)
                            {
                            selectList+="<option value='"+name+"'>"+name+"</option>";               
                            }
                        );
                        selectList+="</select>";

                    //Output the list
                    document.getElementById("d2").innerHTML = selectList;

                    //Detect the selected player
                    var o = document.getElementById("select_list");
                    var nameTried = o.options[o.selectedIndex].value;

                    document.getElementById("select_list").addEventListener("change",
                        function(){                            
                            nameTried = o.options[o.selectedIndex].value;
                            //Set the player object
                            myPlayer.name=nameTried;
                            playersList.forEach(
                                function(player)
                                {                               
                                    if(player.name==nameTried)
                                    {
                                        //Go to the submenu
                                        myPlayer.idPlayer=player.idPlayer;
                                        isPlayerSet=true;
                                        if(isPlayerSet==true){submenu();}   
                                    }
                                }
                            )
                        });
                },
        error: function(xhr, ajaxOptions, thrownError)
                {                        
                    var errorMsg="";
                    switch (xhr.status) 
                        {
                            case 409 :
                                errorMsg="Error 409";
                                break;
                            default:   
                                errorMsg="Error comunications("+xhr.status+")";
                        }
                    
                    document.getElementById("d3").innerHTML="<p class='blinkr'>"+errorMsg+"</p>";
                }        
        });           
    }

//Submenu select player
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function submenu(){
    
    //Generate the submenu
    //Header
    document.getElementById("d1").innerHTML="<p class='blinky'>"+myPlayer.name+" selected</p>";
    //Options  
    document.getElementById("d2").innerHTML="<a href='javascript:playGame();'>Play</a>"; 
    document.getElementById("d3").innerHTML="<a href='javascript:renamePlayer();'>Rename</a>";
    document.getElementById("d4").innerHTML="<a href='javascript:deletePlayer();'>Delete</a>";
    document.getElementById("d5").innerHTML="<a href='javascript:resetGames();'>Reset games</a>";
    
}

//Delete player
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function deletePlayer(){
    urlRequest=urlServer+"players/"+myPlayer.idPlayer;
        
    document.getElementById("d2").innerHTML="<p>Confirm delete player, please</p>";
    document.getElementById("d3").innerHTML="<a href='javascript:deletePlayerRequest();' >Yes</a>";
    document.getElementById("d4").innerHTML="<br><a href='javascript:submenu();' >No</a>";    
    document.getElementById("d5").innerHTML="";    
}

//Delete request
function deletePlayerRequest(){
        $.ajax
        ({
                url: urlRequest,
                type: 'DELETE', 
                async: true,
                cache: false, 
                processData: false,               
                contentType: "application/json; charset=utf-8",
                dataType: "json", 
                  success: function(data)
                      {
                          //
                      alert("Data Deleted: " + data);
                      },
                  error: function(xhr, ajaxOptions, thrownError)
                     {
                         var errorMsg="";
                          switch (xhr.status) 
                          {                            
                            case 200 :
                                //TODO: Solve the generate error on deleted
                                document.getElementById("d1").innerHTML= "<p class='blinky'>Player deleted</p>";
                                document.getElementById("d2").innerHTML= "<br><a href='./index.html'>Exit</a>"; 
                                document.getElementById("d3").innerHTML= "";
                                document.getElementById("d4").innerHTML= "";
                                break;

                            default:   
                                 errorMsg="Error comunications("+xhr.status+")";
                                 document.getElementById("d1").innerHTML="";
                                 document.getElementById("d2").innerHTML="";
                                 document.getElementById("d3").innerHTML="<p class='blinkr'>"+errorMsg+"</p>";
                                 document.getElementById("d4").innerHTML="";                          
                          }                          
                      }

              });
}

//Rename player
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//Set the menu and get the PUT
function renamePlayer(){
    
    urlRequest=urlServer+"players/";//set the url call
    editedPlayer.idPlayer= myPlayer.idPlayer; //Set the id

    // Set a form to receive the new name
    document.getElementById("d1").innerHTML = "<p>Input new name</p>";
    document.getElementById("d2").innerHTML = "<br><form id='rename_form' onsubmit='return setNewName()' action='javascript:void(0);'><input id='newname'required='true'maxlength='15'size='15'type='text'value=''/></form>"
    document.getElementById("d3").innerHTML="";
    document.getElementById("d4").innerHTML=""; 
    document.getElementById("d5").innerHTML=""; 
    document.getElementById('newname').focus();
    }
    

 // Set the name (PUT)
 function setNewName(){        
    
    //Get the new name and full the object with the new name 
    var newName = document.getElementById("newname").value ;
    editedPlayer.name = newName;
    
    //Make the put request
    $.ajax
    ({            
    url: urlRequest,
    type:"PUT",
    async: true,
    cache: false, 
    processData: false,               
    contentType: "application/json; charset=utf-8",
    data: JSON.stringify(editedPlayer),
    dataType: "json", 
        success:function(data)
        {
            //Confirm the rename and exit menu
            document.getElementById("d1").innerHTML = "<p class='blinky'>Name changed</p>";
            document.getElementById("d2").innerHTML = "<br><a href='./index.html'>Exit</a>" 
        },
        error:function(xhr, ajaxOptions, thrownError)
        {
            //TODO: control the errors to exit the correct message and end  
            var output="XD"; 
            switch (xhr.status) 
            {                            
                case 409 :
                    //Duplicated name
                    output="The '"+editedPlayer.name+"' name already exists."
                    break;
                default:   
                    output = "<p class='blinkr'>Communications error:</p class='blink'><p class='blinkr'>try later</p>"
            }
            document.getElementById("d1").innerHTML = "<p class='blinkr'>"+output+"</p>";
            document.getElementById("d2").innerHTML = "<br><a href='javascript:renamePlayer();'>Try again</a>" 

        }
});    
}

//Reset games
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function resetGames(){
    urlRequest=urlServer+"players/"+myPlayer.idPlayer+"/games";
        
    document.getElementById("d2").innerHTML="<p>Confirm delete games, please</p>";
    document.getElementById("d3").innerHTML="<a href='javascript:resetGamesRequest();' >Yes</a>";
    document.getElementById("d4").innerHTML="<br><a href='javascript:submenu();' >No</a>";    
    document.getElementById("d5").innerHTML="";    
}

//Reset games request
function resetGamesRequest(){
        $.ajax
        ({
                url: urlRequest,
                type: 'DELETE', 
                async: true,
                cache: false, 
                processData: false,               
                contentType: "application/json; charset=utf-8",
                dataType: "json", 
                  success: function(data)
                      {
                        //Reset games done
                        document.getElementById("d1").innerHTML= "<p class='blinky'>Games reset done</p>";
                        document.getElementById("d2").innerHTML= "<br><a href='./index.html'>Exit</a>"; 
                        document.getElementById("d3").innerHTML= "";
                        document.getElementById("d4").innerHTML= "";
                      },
                  error: function(xhr, ajaxOptions, thrownError)
                     {
                         var errorMsg="";
                          switch (xhr.status) 
                          {                            
                            case 409 :
                                //Server catch error unable to delete
                                document.getElementById("d1").innerHTML="";
                                document.getElementById("d2").innerHTML="";
                                document.getElementById("d3").innerHTML="<p class='blinkr'>Unable to reset</p><p class='blinkr>games for "+myPlayer.name+"</p>";
                                document.getElementById("d4").innerHTML="";                                
                                break;

                            default:   
                                 errorMsg="Error comunications("+xhr.status+")";
                                 document.getElementById("d1").innerHTML="";
                                 document.getElementById("d2").innerHTML="";
                                 document.getElementById("d3").innerHTML="<p class='blinkr'>"+errorMsg+"</p>";
                                 document.getElementById("d4").innerHTML="";                          
                          }                          
                      }
              });
}

//Ranking
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function ranking(){  

    //Get the list of players and rates

    function getRanking(){
        var ranking = new Array ();

        $.ajax({
            url: urlServer +'players/',
            async: false,
            success: function(data)
            {
                ranking = data;
            },       
            error: function(xhr, ajaxOptions, thrownError)
            {
                document.getElementById("d4").innerHTML= "<p class='blinkr'>Communications error:</p class='blink'><p class='blinkr'>try later</p>";
            }
        });
    return ranking;
    } 
    var ranking = getRanking();

//TODO: Create the view
    var output="Pos Name Exp Rate";
    var i=1;

    ranking.forEach(function(rateDTO)
    {
        //To calculate the experience
        var today = Date.parse(new Date()); 
        const oneDay = 24 * 60 * 60 * 1000;
        var firstDate = Date.parse(rateDTO.player.regDate);
        var diffDays = Math.abs((firstDate - today) / oneDay).toFixed(1);
        var experience = ((diffDays * rateDTO.player.listGame.length)/10).toFixed(1);
        
        output+="<p>"+i
        +" "+rateDTO.player.name
        // +" "+rateDTO.player.listGame.length
        // +" "+diffDays
        +" "+experience
        +" "+rateDTO.rate 
        
        +"</p>";
        i++;
    })

    document.getElementById("d1").innerHTML=output;
//TODO: Set the view
// alert("END-"+urlServer +'players/');
}



    








//Get all players
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
function getall(){
    var options = {
        method: 'get'
        }
    alert("Enviado el GET");

    fetch(urlHost+"getall", options).then(response => {
        document.getElementById("incoming").innerHTML = response
        }).catch(error => console.error(error))
}