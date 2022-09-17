
 function revealDeposit(){
	if (document.getElementById("deposit").style.visibility == "hidden"){
	document.getElementById("deposit").style.visibility = "visible";
	} else if (document.getElementById("deposit").style.visibility == "visible"){
		document.getElementById("deposit").style.visibility = "hidden";
	}
	
}

function revealWithdraw(){
	if (document.getElementById("withdraw").style.visibility == "hidden"){
	document.getElementById("withdraw").style.visibility = "visible";
	} else if (document.getElementById("withdraw").style.visibility == "visible"){
		document.getElementById("withdraw").style.visibility = "hidden";
	}
	
}

async function revealDetails(){
	document.getElementById("selectInfo").style.visibility = "visible";
	
	var infoList = [];
	await getInfo()
    	.then((result) => {
        	infoList = result;
    	})
    	.catch((error) => {
        	console.log(error);
    	});
	
	
	let symbol = document.getElementById("select").value
	
	for (let i = 0; i < infoList.length; i++){
		var crypto = await infoList[i];
		
		if (crypto.symbol == symbol){
			var thisCrypto = infoList[i];
		}
	}
	
	document.getElementById("symbol").value = thisCrypto.symbol;
	document.getElementById("price").value = thisCrypto.price;
}

async function getInfo(){
    var apiURL = `/api`;

    var settings = {
    method : "GET"
    };

    var response = await fetch ( apiURL, settings );
    var hereIn = await response.json();
    
    var data = [];
    
    for (let i = 0; i < hereIn.length; i++){
		data.push(hereIn[i]);
}
    
    
    return data;
}


function revealEstimate(){
	document.getElementById("calcEstimate").style.visibility = "visible";
	document.getElementById("submit").style.visibility = "visible";
	
	
	let amount = document.getElementById("purchaseAmount").value;
	
	let price = document.getElementById("price").value;
	let total;
	price = price.replace(",","");
	if (price < 1){
		total = parseInt(amount)/price;
	} else {
		total = parseInt(amount)/parseInt(price);
	}
	console.log(total);
	
	if (document.getElementById("symbol").value == "BTC" || document.getElementById("symbol").value == "ETH"){
		console.log("1");
		document.getElementById("estimate").value = "Currency not Supported";
		document.getElementById("submit").style.visibility = "hidden";
	} else{
		console.log("2");
		document.getElementById("estimate").value = Math.ceil(total);
	}
	document.getElementById("hideAfter").style.visibility = "hidden";
}

/*$('#myForm').submit(function() {
	
	document.getElementById("symbol").disabled = false;
	document.getElementById("price").disabled = false;
	document.getElementById("estimate").disabled = false;	

    return false;
});*/