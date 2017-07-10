<html> 

<head>
    <title>Kvizovi</title>
    <link rel="stylesheet" href="/RWA_projekat/kviz.css"/>   
</head>

<body> 


    <div id="kviz">
	<h1 align="center">Dodavanje pitanja</h1>
		<table id="listaKvizova"> 
		<tr>
			<th> Pitanje: </th>
			<th> <input type="text" id="pitanjeText" size="40" required> </th>
		</tr>
		<tr>
			<th> Vrijeme (sekunde): </th>
			<th> <input type="text" id="vrijemeText" size="2" required> </th>
		</tr>
		<tr>
			<th> Bodovi: </th>
			<th> <input type="text" id="bodoviText" size="2" required> </th>
		</tr>
		<tr>
			<th> Odgovori: </th>
			<th> 
				<div id="odgovoriContainer">
					
				</div>
			</th>
		</tr>		
		
		<tr> <th colspan="2"> 
		<input  onclick="kreiratiNovoPitanje()" type="button" value="Dodaj pitanje" /> 
		<input  onclick="dodajOdgovor()" type="button" value="Dodaj odgovor" />
		</th> </tr>
		</table>
	
	</div>
	
	<div id="pitanjaZaKviz">
		<h1 align="center">Trenutna pitanja</h1>
		<div id="pitanjaContainer" style="height: 200px; overflow: scroll;">
		</div>
    </div>


</body>

</html>


<script>
var kviz = -1;
var odgovori = 0;
document.addEventListener("DOMContentLoaded", function(event) {
	kviz = ${kvizId};
	ucitajPitanja();
});

function dodajOdgovor() {
	odgovori++;
	var tr = document.createElement("tr"); 
	var th1 = document.createElement("th");
	th1.innerHTML = odgovori + ": ";
	var th2 = document.createElement("th");
	th2.innerHTML = "<input type='text' id='odgovorText" + (odgovori - 1) + "' size='40'> " +
	"<input type='checkbox' id='odgovorCheck" + (odgovori - 1) + "' size='10'>";
	tr.appendChild(th1);
	tr.appendChild(th2);
	document.getElementById("odgovoriContainer").appendChild(tr);
}

	function kreiratiNovoPitanje() {
		var pitanje = document.getElementById("pitanjeText").value;
		var vrijeme = document.getElementById("vrijemeText").value;
		var bodovi = document.getElementById("bodoviText").value;
		if(pitanje == null || pitanje === "") {
			alert("Pitanje ne moze biti prazno.");
			return;
		}
		
		if(vrijeme == null || vrijeme === "") {
			alert("Vrijeme ne moze biti prazno.");
			return;
		}
		
		if(bodovi == null || bodovi === "") {
			alert("Bodovi ne mogu biti prazni.");
			return;
		}
		
		var odgovoriArray = [];
		for (i = 0; i < odgovori; i++) { 
			var textId = "odgovorText" + i;
			var checkId = "odgovorCheck" + i;
		    var odgovor = document.getElementById(textId).value;
		    var checked = document.getElementById(checkId).checked;
		    if(odgovor!=null && odgovor != "") {
		    		odgovoriArray.push({tekst: odgovor, tacan: checked ? 1 : 0});
		    }
		}
		
		if (odgovoriArray.length < 2) {
			alert("Morate unijeti minimalno dva odgovora.");
			return;
		}
		
		if (kviz==-1) {
			alert("Pogresan kviz.");
			return;
		}
		
		var xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if (xhr.readyState != 4 || xhr.responseText == null || xhr.responseText == "") return;
			if(xhr.status === 200) {
				console.log(xhr.responseText);
	           	var response = JSON.parse(xhr.responseText);
	           	if (response!=null && response["status"]==1) {
	           		document.getElementById("pitanjeText").value = "";
	           		document.getElementById("vrijemeText").value = "";
	           		document.getElementById("bodoviText").value = "";
	           		document.getElementById("odgovoriContainer").innerHTML = "";
	           		ucitajPitanja();
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

		xhr.open("POST", "http://localhost:8080/RWA_projekat/admin/api/pitanja?action=CREATE&kvizId=" + kviz);
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(JSON.stringify({pitanje: pitanje, vrijeme: vrijeme, bodovi: bodovi, odgovori: odgovoriArray}));	
	}

	function obrisatiPitanje(pitanjeId) {
		var url = "http://localhost:8080/RWA_projekat/admin/api/pitanja?action=DELETE";
		var xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if (xhr.readyState != 4 || xhr.responseText == null || xhr.responseText == "") return;
			if(xhr.status === 200) {
				ucitajPitanja();
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

		xhr.open("POST", url);
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.send(JSON.stringify({id: pitanjeId}));	
	}

    function ucitajPitanja() {
    	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState != 4 || xhr.responseText == null || xhr.responseText == "") return;
		if(xhr.status === 200) {
			var response = JSON.parse(xhr.responseText);
			var pitanjaContainer = document.getElementById("pitanjaContainer");
			var pitanja = "";
			for(var i in response)
			{
				var pitanje = response[i];				
				pitanja += "<span>" + pitanje.pitanje + 
				" (vrijeme: " + pitanje.vrijeme + "s, " +
				"bodovi: " + pitanje.bodovi + ")" +
			    "<span onclick='obrisatiPitanje(" + pitanje.id + ")' style='float: right;'>Obrisati </span>"			    
			    + "</span><br>"; 
			    
			    for(var i in pitanje.odgovori) {
			    		var odgovor = pitanje.odgovori[i];
			    		var color = "red";
			    		if(odgovor.tacan == 1) {
			    			color = "green";
			    		}
			    		var rb = parseInt(i) + 1;
			    		pitanja += "<span style='margin-left: 20px; color: " + color + ";'>" + rb + ". " + odgovor.tekst + "</span><br>";
			    }
			    
			}
			pitanjaContainer.innerHTML = pitanja;			
			return;
		} 
	};

	xhr.open("GET", "http://localhost:8080/RWA_projekat/admin/api/pitanja?kvizId=" + kviz);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send();	

}
</script>