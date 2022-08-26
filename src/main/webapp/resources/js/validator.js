document.getElementById("celular").disabled = true;
document.getElementById("txtCorreo").addEventListener("input", function () {
    campo = event.target;
    valido = document.getElementById("msgCorreo");

    emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
    //Se muestra un texto a modo de ejemplo, luego va a ser un icono
    if (emailRegex.test(campo.value)) {
        valido.innerText = "Correo válido";
        document.getElementById("celular").disabled = false;
    } else {
        valido.innerText = "Correo electronico inválido, al menos debe  !#$%&'*+-/=?^_`{}|~@example.org";
        document.getElementById("celular").disabled = true;

    }
});

function validarDocumento() {

    var documento = document.getElementById("usuario").value;

    if (documento.length === 11 || documento.length === 8) {
            PF('btnhola').enable();
        } else {
            PF('btnhola').disable();

        }

    }



function habilitar(){
	text_1 = document.getElementById("txt_1").value;
	text_2 = document.getElementById("txt_2").value;
	val = 0;

	if(text_1 ==""){
	val++;
	}
	
	if(text_2 ==""){
	val++;
	}
	
	if(val ==""){
		document.getElementById("btn").disabled = false;
	}else{
		document.getElementById("btn").disabled = true;
	}

}
document.getElementById("txt_1").addEventListener("keyup",habilitar);
document.getElementById("txt_2").addEventListener("keyup",habilitar);
document.getElementById("btn").addEventListener("click",() => {
	alert("Has llenado tood");
});






