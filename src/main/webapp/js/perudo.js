/* Constante */
var server = "http://localhost:8080";
var perudoCookie = "fr_plaisance_perudo";
var refreshRate = 2000;

/* Timer */
var started = false;
var timer = null;

function start(){
	requestPerudo();
//	if(!started){
//		timer = setInterval("requestPerudo()", refreshRate);
//		started = true;
//	}
}

function stop(){
	clearInterval(timer);
	started = false;
}

/* General */

$(function() {
	if(getGameId() == null || getPlayerId() == null){
		drawAddPlayer();
	}
	else{
		start();
	}
});

function removeContent(){
	removeDivision("content");
}

function removeActionBar(){
	removeDivision("actionbar");
}

function removeTitle(){
	removeDivision("title");
}

function removeDivision(id){
	$("#" + id).empty();
}

function displayErrorMessage(message){
	removeDivision("errors");
	$("#errors").append("<div class='error'>" + message + "</div>");
}

function getGameId(){
	if($.cookie(perudoCookie) == null){
		return null;
	}
	return 	$.cookie(perudoCookie).split(":")[0];
}

function getPlayerId(){
	if($.cookie(perudoCookie) == null){
		return null;
	}
	return 	$.cookie(perudoCookie).split(":")[1];
}

function imageFace(face, color, small) {
	if(small){
		return "<img class='face-small' src='img/faces/" + face + color + ".png'/>";
	}
	return "<img class='face' src='img/faces/" + face + color + ".png'/>";
}

function imageNumber(number) {
	return "<span class='badge badge-inverse'>" + number + "</span> ";
}

function imageAction(action) {
	if(action == "bet"){
		return "";
	}
	return "<img class='action' src='img/action/" + action + ".png'/>";
}

/* Perudo */

function requestPerudo() {
	var gameId = getGameId();
	var playerId = getPlayerId();
	$.ajax({
		url : server + "/perudo/service/game/perudo",
		data : {
			"game" : gameId,
			"player" : playerId
		},
		dataType: "xml",
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseXML;
			handlePerudo(response);
        },
        error: function(jqXHR, textStatus) {
			handleFail(textStatus);
		}
	});
}

function deleteCookies() {
	$.cookie(perudoCookie, null);
}

function drawTitle(gameId, palifico, over, started) {
	removeTitle();
	$("#title").append("<h2 id='gameTitle'>Partie #" + gameId + "</h2>");
	if(over == "true"){
		$("#title").append("<h3 id='gameSubtitle'>Terminée</h3>");	
	}
	else if(palifico == "true"){
		$("#title").append("<h3 id='gameSubtitle'>Palifico</h3>");
	}
	else if(started == "true"){
		$("#title").append("<h3 id='gameSubtitle'>En cours</h3>");	
	}
	else{
		$("#title").append("<h3 id='gameSubtitle'>En attente</h3>");
	}
}

function drawMessages(response){
	var children = $("#messages").children();
	$.each(children, function(index, child) {
		$(child).remove();
	});
	$(response).find("message").each(function(){
		var message = $(this).text();
		var messageClass = $(this).attr("type"); 
		$("#messages").append("<div class='" + messageClass + "'>" + message + "</div>");
	});
}

function drawPlayers(response, playerId, started){
	
	$("#content").append("<table id='players' class='table table-striped table-bordered'>");
	var activePlayer = false;
	$(response).find("player").each(function(){
		
		var playerName = $(this).find("playerName").text();
		var playerRank = $(this).find("playerRank").text();
		var active = $(this).attr("active");
		var color = $(this).attr("color");
		
		$("#players").append("<tr id='player" + playerRank + "'><td class='player'>" + playerName + "</td></tr>");
		if(active == "true"){
			$("#player" + playerRank).removeClass("active").addClass("active");
		}
		
		if($(this).find("playerId").text().length > 0 && active == "true"){
			activePlayer = true;
		}
		
		if(started == "true"){
			$("#player" + playerRank).append("<td id='faces" + playerRank + "'></td>");
			$(this).find("face").each(function(){
				$("#faces" + playerRank).append(imageFace($(this).text(), color));
			});
		}
		
		var action = $(this).attr("action");
		if(action != null){
			$("#player" + playerRank).append("<td id='action" + playerRank + "'></td>");
			$("#action" + playerRank).append(imageAction(action));
		}
		else{
			$("#player" + playerRank).append("<td id='action" + playerRank + "'></td>");
		}
		
		if($(this).find("declaration").size() > 0){
			var number = $(this).find("number").text();
			var face = $(this).find("value").text();
			$("#player" + playerRank).append("<td id='declaration" + playerRank + "'>" + imageNumber(number) + imageFace(face, "s", true) + "</td>");
		}
	});
	return activePlayer;
}

function drawActionBar(ready, started, paused, isActive){
	if(ready == "true" && started == "false"){
		removeActionBar();
		$("#actionbar")
			.append("<input type='button' class='btn btn-primary' value='Commencer la partie' onclick='requestStartGame()' />");
	}
	else if(started == "true"){
		if(paused == "true"){
			if(isActive == true){
				removeActionBar();
				$("#actionbar")
					.append("<input type='button' class='btn btn-primary' value='Lancer les dés' onclick='requestRollDice()' />");
			}
			else{
				removeActionBar();
			}
		}
		else{
			if(isActive == true && actionBarIsDiplayed() == false){
				removeActionBar();
				$("#actionbar").append("<form id='actionbarform' class='form-inline'></form>");
				$("#actionbarform").append("<input type='button' class='btn btn-warning' value='Calza' onclick='requestCalza()' /> ");
				$("#actionbarform")
					.append( "<input type='button' class='btn btn-danger' value='Dudo' onclick='requestDudo()' /> ")
					.append( "<span class='input-prepend'><span class='add-on'>#</span><input type='text' class='input-mini' id='number' /></span> ")
					.append( "<span class='input-prepend'><span class='add-on'><i class='icon-th-large'></i></span><input type='text' class='input-mini' id='face' onkeypress='if(event.keyCode == 13){requestBet()}' /></span> ")
					.append( "<input type='button' class='btn btn-success' value='Enchérir' onclick='requestBet()' /> ");
			}
			if(isActive == false){
				removeActionBar();
				$("#actionbar").append("<form id='actionbarform' class='form-inline'></form>");
				$("#actionbarform").append("<input type='button' class='btn btn-warning' value='Calza' onclick='requestCalza()' /> ");
			}
		}
	}
}

function actionBarIsDiplayed(){
	return $("#face").size() > 0;
}

function handlePerudo(response) {
	removeContent();
	if($(response).find("error").size() > 0){
		deleteCookies(response);
		drawAddPlayer();
	}
	else{
		var playerId = getPlayerId();
		var gameId = getGameId();
		
		var palifico = 	$(response).find("game").attr("palifico");
		var ready = 	$(response).find("game").attr("ready");
		var started = 	$(response).find("game").attr("started");
		var over = 		$(response).find("game").attr("over");
		var paused =	$(response).find("game").attr("paused");

		drawTitle(gameId, palifico, over, started);
		drawMessages(response);
		var isActive = drawPlayers(response, playerId, started);
		if(over == "true"){
			requestEndGame();
		}
		else{
			drawActionBar(ready, started, paused, isActive);
		}

	}
}

/* Add player */

function drawAddPlayer(){
	removeContent();
	$("#content")
		.append("<h2 id='gameTitle'>Ajouter un joueur</h2>")
		.append("<label for='playerName'>Nom du joueur :</label>")
		.append(" <input type='text' id='playerName' onkeypress='if(event.keyCode == 13){requestAddPlayer()}' />")
		.append(" <input type='button' class='btn btn-primary' value='Ajouter' onclick='requestAddPlayer()' />")
	;
}

function requestAddPlayer() {
	var playerName = $("#playerName").val();
	$.ajax({
		url :  server + "/perudo/service/game/player",
		data : {"name" : playerName},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleAddPlayer(response, playerName);
        }
	});
}

function handleAddPlayer(response, playerName){
	removeContent();
	tokens = response.split(":");
	type = tokens[0];
	
	if(type == "error"){
		errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	else{
		playerId = tokens[1];
		gameId = tokens[2];
		$.cookie(perudoCookie, gameId + ":" + playerId,	{expires : 1});
		start();
	}
	requestPerudo();
}

/* Start game */

function requestStartGame() {
	var gameId = getGameId();
	$.ajax({
		url :  server + "/perudo/service/game/start",
		data : {"game" : gameId},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleStartGame(response);
        }
	});
}

function handleStartGame(response){
	removeContent();
	tokens = response.split(":");
	type = tokens[0];
	
	if(type == "error"){
		errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	requestPerudo();
}

function requestBet() {
	var gameId = getGameId();
	var playerId = getPlayerId();
	var number = $("#number").val();
	var face = $("#face").val();
	$.ajax({
		url :  server + "/perudo/service/game/bet",
		data : {
			"game" : gameId,
			"player" : playerId,
			"number" : number,
			"face" : face
		},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleBet(response);
        }
	});
}

function handleBet(response) {
	removeContent();
	tokens = response.split(":");
	type = tokens[0];
	
	/* error:errorMessage */
	if(type == "error"){
		var errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	requestPerudo();
}

function requestDudo() {
	var gameId = getGameId();
	var playerId = getPlayerId();
	$.ajax({
		url :  server + "/perudo/service/game/dudo",
		data : {
			"game" : gameId,
			"player" : playerId
		},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleDudo(response);
        }
	});
}

function handleDudo(response) {
	removeContent();
	tokens = response.split(":");
	type = tokens[0];
	if(type == "error"){
		var errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	requestPerudo();
}

function requestCalza() {
	var gameId = getGameId();
	var playerId = getPlayerId();
	$.ajax({
		url :  server + "/perudo/service/game/calza",
		data : {
			"game" : gameId,
			"player" : playerId
		},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleCalza(response);
        }
	});
}

function handleCalza(response) {
	removeContent();
	tokens = response.split(":");
	type = tokens[0];
	if(type == "error"){
		var errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	requestPerudo();
}

function requestRollDice() {
	var gameId = getGameId();
	var playerId = getPlayerId();
	$.ajax({
		url :  server + "/perudo/service/game/roll",
		data : {
			"game" : gameId,
			"player" : playerId
		},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleRollDice(response);
        }
	});
}

function handleRollDice(response){
	tokens = response.split(":");
	if(tokens[0] == "error"){
		var errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	requestPerudo();	
}

function requestEndGame() {
	stop();
	var gameId = getGameId();
	$.ajax({
		url :  server + "/perudo/service/game/end",
		data : {
			"game" : gameId,
		},
		complete: function(jqXHR, textStatus){
			var response = jqXHR.responseText;
			handleEndGame(response);
        }
	});

}

function handleEndGame(response) {
	tokens = response.split(":");
	if(tokens[0] == "error"){
		var errorMessage = tokens[1];
		displayErrorMessage(errorMessage);
	}
	var winner = tokens[1];
	$("#gameSubtitle").text(winner + " a gagné !");
	deleteCookies();
}

function handleFail(textStatus) {
	stop();
	deleteCookies();
	removeActionBar();
	removeTitle();
	removeDivision("messages");
	removeContent();
	displayErrorMessage("<p>Une erreur est survenue.</p><p>La partie en cours est perdue.</p>");
}