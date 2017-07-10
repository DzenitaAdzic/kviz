<html> 

<head>
    <title>Kvizovi</title>
    <link rel="stylesheet" href="/RWA_projekat/kviz.css"/>   
</head>

<body> 


    <div id="kviz" style="margin-right: 60px; width: auto;">
		
	
	</div>

</body>

</html>


<script>

var kviz = -1;
var trenutnoPitanje = -1;
var pitanja = [];
var preostaloVremena = 0;
var rezultati = [];
document.addEventListener("DOMContentLoaded", function(event) {
	kviz = ${kvizId};
	ucitajPitanja();
});

	
    function ucitajPitanja() {
    	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState != 4 || xhr.responseText == null || xhr.responseText == "") return;
		if(xhr.status === 200) {
			var response = JSON.parse(xhr.responseText);
			pitanja = response;
			ucitajSljedecePitanje();
			return;
		} 
	};

	xhr.open("GET", "http://localhost:8080/RWA_projekat/api/pitanja?kvizId=" + kviz);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send();	
    
	}
    
    function ucitajSljedecePitanje() {
    	trenutnoPitanje++;    	
    	if(trenutnoPitanje >= pitanja.length) {
    		var container = document.getElementById("kviz");
        	var h1 = document.createElement("h1");
        	h1.innerHTML = "Zavrsili ste!";
        	var odgovori = document.createElement("table");
        	container.innerHTML = "";
        	container.appendChild(h1);
        	var odgovoriHtml = "";
        	odgovoriHtml += "<tr><th>Unesite vas email:</th></tr><br>"
        	odgovoriHtml += '<input id="emailText" type="text" value="" size="50" /><br><br>';
        odgovoriHtml += '<input  onclick="posaljiRezultate()" type="button" value="Posalji rezultate" />';
        odgovori.innerHTML = odgovoriHtml;
        	container.appendChild(odgovori);
    		return;
    	}
    	var pitanje = pitanja[trenutnoPitanje];
    	var container = document.getElementById("kviz");
    	var h1 = document.createElement("h1");
    	h1.innerHTML = pitanje.pitanje;
    	var odgovori = document.createElement("table");
    	container.innerHTML = "";
    	container.appendChild(h1);
    	var odgovoriHtml = "";
    	preostaloVremena = parseInt(pitanje.vrijeme);
    	for(var i in pitanje.odgovori) {
    		var odgovor = pitanje.odgovori[i];
    		var rb = parseInt(i) + 1;
    		odgovoriHtml += "<tr><th>" + rb + ". " + odgovor.tekst + 
    		"<input style='margin-left: 20px;' type='checkbox' id='odgovor" + odgovor.id + "'/>" +
    		"</th></tr><br>"
    }
    	odgovoriHtml += '<input  onclick="sacuvajOdgovor()" type="button" value="Potvrditi" />';
    odgovori.innerHTML = odgovoriHtml;
    	container.appendChild(odgovori);
	}
    
    function posaljiRezultate() {
    		var email = document.getElementById("emailText").value;
    		if (email==null || email=="") {
    			alert("Polje email je obavezno.");
    			return;
    		}
    		
    		var xhr = new XMLHttpRequest();

    		xhr.onreadystatechange = function() {
    			if (xhr.readyState != 4 || xhr.responseText == null || xhr.responseText == "") return;
    			if(xhr.status === 200) {
    				console.log(xhr.responseText);
    	           	var response = JSON.parse(xhr.responseText);
    	           	if (response!=null && response["status"]==1) {
    	           		window.location = "rezultati.html";
    	           	} else {
    	           		alert("Dogodila se greska");
    	           	}		
    	           	return;
    			} else {
    				var error = "Dogodila se greska";
    				if(xhr!=null && xhr.responseText!=null){
    					var response = JSON.parse(xhr.responseText);
    					if (response!=null && response["message"]!=null) {
    						error = response["message"];
    					}
    				}
    				alert(error);
    				return;
    			}
    		};

    		xhr.open("POST", "http://localhost:8080/RWA_projekat/api/pitanja");
    		xhr.setRequestHeader("Content-Type", "application/json");
    		xhr.send(JSON.stringify({kvizId: kviz, email: email, odgovori: rezultati}));
    }
    
    function sacuvajOdgovor() {
    		var pitanje = pitanja[trenutnoPitanje];
    		var rez = {pitanjeId: pitanje.id};
    		rez.odgovori = [];
    		for(var i in pitanje.odgovori) {
    			var odgovor = pitanje.odgovori[i];
    			var id = odgovor.id;
    			if(document.getElementById("odgovor" + id).checked) {
    				rez.odgovori.push(odgovor.id);
    				rezultati.push(rez);    				
    			}    			
    		}    
    		ucitajSljedecePitanje();
    }
</script>